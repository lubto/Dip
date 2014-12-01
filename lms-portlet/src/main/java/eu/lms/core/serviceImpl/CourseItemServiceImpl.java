package eu.lms.core.serviceImpl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import eu.lms.core.daoImpl.CourseItemDaoImpl;
import eu.lms.core.entity.CourseItem;
import eu.lms.core.mapper.Mapper;
import eu.lms.iface.dto.CourseItemDto;
import eu.lms.iface.service.CourseItemService;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service providing access to the data-access-layer.
 *
 * @author Lubomir Lacika
 *
 */
@Service
@Transactional
public class CourseItemServiceImpl implements CourseItemService {

    @Autowired
    private CourseItemDaoImpl courseItemDao;

    /**
     * This method is used to get all course item.
     *
     * @return List This returns the list of course item.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CourseItemDto> getAllCourseItem() {
        List<CourseItem> entities = courseItemDao.getAll();

        List<CourseItemDto> dtos = new ArrayList<CourseItemDto>(entities.size());

        for (CourseItem entity : entities) {
            dtos.add(Mapper.toDto(entity));
        }
        return dtos;
    }

    /**
     * This method is used to save course item.
     *
     * @param courseItemDto This parameter is entity which we want to save/update.
     */
    @Override
    public void saveCourseItem(CourseItemDto courseItemDto) {
        Validate.notNull(courseItemDto);
        Validate.notNull(courseItemDto.getId());

        CourseItem entity = courseItemDao.getById(courseItemDto.getId());
        courseItemDao.update(Mapper.toEntity(entity, courseItemDto));
    }

    /**
     * This method is used to create course item.
     *
     * @param courseDto This parameter is entity which we want to create.
     */
    @Override
    public void createCourseItem(CourseItemDto courseItemDto) {
        Validate.notNull(courseItemDto);
        Validate.isTrue(courseItemDto.getId() == null);

        courseItemDao.create(Mapper.toEntity(null, courseItemDto));
    }

    /**
     * This method is used to get course item by ID.
     *
     * @param id This parameter is ID of course item.
     * @return CategoryDto This returns the course item.
     */
    @Override
    public CourseItemDto getCourseItemById(Long id) {
        Validate.notNull(id);

        CourseItem entity = courseItemDao.getById(id);

        return Mapper.toDto(entity);
    }

    /**
     * This method is used to delete course item by ID.
     *
     * @param id This parameter is ID of course item.
     * @return CategoryDto This returns the course item.
     */
    @Override
    public CourseItemDto deleteCourseItemById(Long id) {
        Validate.notNull(id);

        CourseItem entity = courseItemDao.getById(id);
        courseItemDao.delete(entity);

        return Mapper.toDto(entity);
    }

    /**
     * This method is used to get course item by user´s ID.
     *
     * @param userId This parameter is ID of user.
     * @return AccountUsserDto This returns the CourseItemDto.
     */
    @Override
    public CourseItemDto getCourseItemByUserId(String userid) {
        return Mapper.toDto(courseItemDao.getCourseItemByUserId(userid));
    }

    /**
     * This method is used to find all users ID from CourseItem by course´s ID
     * and if is course item visible.
     *
     * @param courseId This parameter is course´s ID.
     * @param visibleCourseItem This parameter is true if is courseItem visible
     * on the portal.
     * @return List This returns the list of users ID´s.
     */
    @Override
    public List<String> getAllCourseItemByCourse(String courseId, Boolean visibleCourseItem) {
        return courseItemDao.getAllCourseItemByUserId(courseId, visibleCourseItem);
    }

    /**
     * This method is used to change boolean value if student visited course.
     *
     * @param id This parameter is course´s item ID.
     * @param visit This parameter is true if is courseItem has been visited.
     */
    @Override
    public void changeVisitCourseItem(Long id, Boolean visit) {
        Validate.notNull(id);

        CourseItem entity = courseItemDao.getById(id);
        entity.setVisitedCourse(true);
        courseItemDao.update(entity);
    }

    /**
     * This method is used to get all course items by course´s ID.
     *
     * @param courseId This parameter is ID´s course.
     * @return List This returns the list of CourseItemDto.
     */
    @Override
    public List<CourseItemDto> getAllCourseItemByCourse(Long courseId) {

        List<CourseItem> entities = courseItemDao.getAllCourseItemByUserId(courseId);

        List<CourseItemDto> dtos = new ArrayList<CourseItemDto>(entities.size());

        for (CourseItem entity : entities) {
            dtos.add(Mapper.toDto(entity));
        }
        return dtos;
    }

    /**
     * This method is used to change visible and invisible course item in the
     * portal.
     *
     * @param item This parameter is boolean value, if it is true, course item
     * is visible in the portal. If the value is false, course item is invisible
     * in the portal.
     * @param id This parameter is ID of course item.
     */
    @Override
    public void changeActivate(Boolean item, Long id) {
        Validate.notNull(id);
        Validate.notNull(item);

        CourseItem entity = courseItemDao.getById(id);
        entity.setVisible(item);
    }

    /**
     * This method is used to check value "timeIsUp". If course item is pass due
     * to time then it will be changed.
     *
     * @param itemsCourse This parameter is list of item course.
     * @return List This return list of course items
     */
    @Override
    public List<CourseItemDto> checkTimeIsUpList(List<CourseItemDto> itemsCourse) {
        for (CourseItemDto courseItemDto : itemsCourse) {
            try {
                User user = UserLocalServiceUtil.getUser(Long.valueOf(courseItemDto.getUserId()));
                courseItemDto.setFullName(user.getFullName());
                long timeNow = System.currentTimeMillis();
                Date date = new Date(timeNow);
                if (courseItemDto.getEndCourse().before(date)) {
                    courseItemDto.setTimesUp(true);
                } else {
                    courseItemDto.setTimesUp(false);
                }
            } catch (PortalException ex) {
                Logger.getLogger(CourseItemServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SystemException ex) {
                Logger.getLogger(CourseItemServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return itemsCourse;
    }

    /**
     * This method is used to search in table of course item by full name.
     *
     * @param searchWord This parameter is word which we want to search.
     * @param list This parameter is list of course item.
     * @return List This returns the list of CourseItemDto where the word is
     * being searched.
     */
    @Override
    public List<CourseItemDto> searchInListByFullName(String searchWord, List<CourseItemDto> list) {
        String search = searchWord.toLowerCase();
        List<CourseItemDto> listNew = new ArrayList<CourseItemDto>();
        for (Iterator<CourseItemDto> it = list.iterator(); it.hasNext();) {
            CourseItemDto course = it.next();
            String name = course.getFullName().toLowerCase();
            if (name.contains(search)) {
                listNew.add(course);
            }
        }
        return listNew;
    }

    /**
     * This method is used to search in table of course item by course.
     *
     * @param searchWord This parameter is word which we want to search.
     * @param list This parameter is list of course item.
     * @return List This returns the list of CourseItemDto where the word is
     * being searched.
     */
    @Override
    public List<CourseItemDto> searchInListByCourse(String searchWord, List<CourseItemDto> list) {
        String search = searchWord.toLowerCase();
        List<CourseItemDto> listNew = new ArrayList<CourseItemDto>();
        for (Iterator<CourseItemDto> it = list.iterator(); it.hasNext();) {
            CourseItemDto course = it.next();
            String name = course.getNameCourse().toLowerCase();
            if (name.contains(search)) {
                listNew.add(course);
            }
        }
        return listNew;
    }

}
