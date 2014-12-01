package eu.lms.iface.service;

import eu.lms.iface.dto.CourseItemDto;
import java.util.List;

/**
 * Service providing access to the data-access-layer.
 *
 * @author Lubomir Lacika
 *
 */
public interface CourseItemService {

    /**
     * This method is used to get all course item.
     *
     * @return List This returns the list of course item.
     */
    List<CourseItemDto> getAllCourseItem();

    /**
     * This method is used to save course item.
     *
     * @param courseItemDto This parameter is entity which we want to save/update.
     */
    void saveCourseItem(CourseItemDto courseItemDto);

    /**
     * This method is used to create course item.
     *
     * @param categoryDto This parameter is entity which we want to create.
     */
    void createCourseItem(CourseItemDto courseItemDto);

    /**
     * This method is used to get course item by ID.
     *
     * @param id This parameter is ID of course item.
     * @return CategoryDto This returns the course item.
     */
    CourseItemDto getCourseItemById(Long id);

    /**
     * This method is used to delete course item by ID.
     *
     * @param id This parameter is ID of course item.
     * @return CourseItemDto This returns the course item.
     */
    CourseItemDto deleteCourseItemById(Long id);

    /**
     * This method is used to get course item by user´s ID.
     *
     * @param userId This parameter is ID of user.
     * @return AccountUsserDto This returns the CourseItemDto.
     */
    CourseItemDto getCourseItemByUserId(String userid);

    /**
     * This method is used to find all users ID from CourseItem by course´s ID
     * and if is course item visible.
     *
     * @param courseId This parameter is course´s ID.
     * @param visibleCourseItem This parameter is true if is courseItem visible
     * on the portal.
     * @return List This returns the list of users ID´s.
     */
    List<CourseItemDto> getAllCourseItemByCourse(Long courseId);

    /**
     * This method is used to get all course items by course´s ID.
     *
     * @param courseId This parameter is ID´s course.
     * @return List This returns the list of CourseItemDto.
     */
    List<String> getAllCourseItemByCourse(String courseId, Boolean visibleCourseItem);

    /**
     * This method is used to change boolean value if student visited course.
     *
     * @param id This parameter is course´s item ID.
     * @param visit This parameter is true if is courseItem has been visited.
     */
    void changeVisitCourseItem(Long id, Boolean visit);

    /**
     * This method is used to change visible and invisible course item in the
     * portal.
     *
     * @param item This parameter is boolean value, if it is true, course item
     * is visible in the portal. If the value is false, course item is invisible
     * in the portal.
     * @param id This parameter is ID of course item.
     */
    void changeActivate(Boolean item, Long id);

    /**
     * This method is used to check value "timeIsUp". If course item is pass due
     * to time then it will be changed.
     *
     * @param itemsCourse This parameter is list of item course.
     * @return List This return list of course items
     */
    List<CourseItemDto> checkTimeIsUpList(List<CourseItemDto> list);

    /**
     * This method is used to search in table of course item by full name.
     *
     * @param searchWord This parameter is word which we want to search.
     * @param list This parameter is list of course item.
     * @return List This returns the list of CourseItemDto where the word is
     * being searched.
     */
    List<CourseItemDto> searchInListByFullName(String searchWord, List<CourseItemDto> list);

    /**
     * This method is used to search in table of course item by course.
     *
     * @param searchWord This parameter is word which we want to search.
     * @param list This parameter is list of course item.
     * @return List This returns the list of CourseItemDto where the word is
     * being searched.
     */
    List<CourseItemDto> searchInListByCourse(String searchWord, List<CourseItemDto> list);

}
