package eu.lms.core.serviceImpl;

import eu.lms.core.daoImpl.BankQuestionsDaoImpl;
import eu.lms.core.daoImpl.CategoryDaoImpl;
import eu.lms.core.daoImpl.CourseDaoImpl;
import eu.lms.core.daoImpl.CourseItemDaoImpl;
import eu.lms.core.daoImpl.GroupDocumentsDaoImpl;
import eu.lms.core.daoImpl.ItemAttemptsDaoImpl;
import eu.lms.core.daoImpl.LessonDaoImpl;
import eu.lms.core.daoImpl.UserBankQDaoImpl;
import eu.lms.core.entity.BankQuestions;
import eu.lms.core.entity.Category;
import eu.lms.core.entity.Course;
import eu.lms.core.entity.CourseItem;
import eu.lms.core.entity.GroupDocuments;
import eu.lms.core.entity.ItemAttempts;
import eu.lms.core.entity.Lesson;
import eu.lms.core.entity.UserBankQ;
import eu.lms.core.mapper.Mapper;
import eu.lms.iface.dto.BankQuestionsDto;
import eu.lms.iface.dto.CategoryDto;
import eu.lms.iface.dto.CourseDto;
import eu.lms.iface.dto.GroupDocumentsDto;
import eu.lms.iface.dto.LessonDto;
import eu.lms.iface.service.CourseService;
import eu.lms.portlet.mainright.pto.CoursePto;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service providing access to the data-access-layer.
 *
 * @author Lubomir Lacika
 */
@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private static final Logger LOG = Logger.getLogger(CourseServiceImpl.class);
    @Autowired
    private CourseDaoImpl courseDao;
    @Autowired
    private CategoryDaoImpl categoryDao;
    @Autowired
    private LessonDaoImpl lessonDao;
    @Autowired
    private BankQuestionsDaoImpl bankDao;
    @Autowired
    private GroupDocumentsDaoImpl groupDao;
    @Autowired
    private UserBankQDaoImpl userBankDao;
    @Autowired
    private ItemAttemptsDaoImpl itemAttemptsDao;
    @Autowired
    private CourseItemDaoImpl courseItemDao;

    /**
     * This method is used to get all course.
     *
     * @return List This returns the list of course.
     */
    @Override
    @Transactional
    public List<CourseDto> getAllCourse() {
        List<Course> entities = courseDao.getAll();

        List<CourseDto> dtos = new ArrayList<CourseDto>(entities.size());

        for (Course course : entities) {
            dtos.add(Mapper.toDto(course));
        }
        return dtos;
    }

    /**
     * This method is used to save course.
     *
     * @param courseDto This parameter is entity which we want to save/update.
     */
    @Override
    public void saveCourse(CourseDto courseDto) {
        Validate.notNull(courseDto);
        Validate.notNull(courseDto.getId());
        Course course = courseDao.getById(courseDto.getId());

        Category category = categoryDao.getById(courseDto.getCategory().getId());
        //Course updatedCourse = courseDao.save(Mapper.toEntity(course, courseDto, category));
        courseDao.update(Mapper.toEntitys(course, courseDto, category));
        //return Mapper.toDto(updatedCourse); 
    }

    /**
     * This method is used to create course.
     *
     * @param courseDto This parameter is entity which we want to create.
     */
    @Override
    public void createCourse(CourseDto courseDto) {
        Validate.notNull(courseDto);
        Validate.isTrue(courseDto.getId() == null);
        Category category = categoryDao.getById(courseDto.getCategory().getId());

        //Course savedCourse = courseDao.create(Mapper.toEntity(null, courseDto, category));
        courseDao.create(Mapper.toEntity(null, courseDto, category));
        category.setNumberCourses(category.getNumberCourses() + 1);
        //return Mapper.toDto(savedCourse);
    }

    /**
     * This method is used to get course by ID.
     *
     * @param id This parameter is ID of course.
     * @return courseDto This returns the course.
     */
    @Override
    public CourseDto getCourseById(Long id) {
        Validate.notNull(id);
        Course course = courseDao.getById(id);

        return Mapper.toDto(course);
    }

    /**
     * This method is used to delete course by ID.
     *
     * @param id This parameter is ID of course.
     * @return CourseDto This returns the course.
     */
    @Override
    public CourseDto deleteCourseById(Long id) {
        Validate.notNull(id);
        Course course = courseDao.getById(id);
        Category category = categoryDao.getById(course.getCategory().getId());

        courseDao.delete(course);
        category.setNumberCourses(category.getNumberCourses() - 1);

        return Mapper.toDto(course);
    }

    /**
     * This method is used to get course by user´s ID.
     *
     * @param userId This parameter is ID of user.
     * @return CourseDto This returns the CourseDto.
     */
    @Override
    public List<CourseDto> getAllCourseByUserId(String userId) {
        Validate.notNull(userId);
        List<CourseDto> listDto = new ArrayList<CourseDto>();
        List<Course> list = courseDao.getAllCourseByUserId(userId);
        for (Iterator<Course> it = list.iterator(); it.hasNext();) {
            Course course = it.next();
            listDto.add(Mapper.toDto(course));
        }
        return listDto;
    }

    /**
     * This method is used to add lesson to course.
     *
     * @param lesson This parameter is lesson.
     * @param id This parameter is ID of course.
     */
    @Override
    public void addLesson(LessonDto lesson, Long id) {
        Validate.notNull(id);

        Course course = new Course();
        course = courseDao.getById(id);

        Lesson lessonSaved = lessonDao.createO(Mapper.toEntity(null, lesson));
        //  lessonDao.create(Mapper.toEntity(null, lesson));
        //  LOG.info("Log message save lesson");
        //Lesson lessonLoaded = lessonDao.getById(lessonSaved.getId());
        lessonSaved.setCourseId(course);

        course.getLesson().add(lessonSaved);
        //  LOG.info("Log message lesson add to course");

        courseDao.update(course);
        // LOG.info("Log message save course");
    }

    /**
     * This method is used to remove lesson from course.
     *
     * @param lesson This parameter is lesson.
     * @param id This parameter is ID of course.
     */
    @Override
    public void removeLesson(LessonDto lesson, Long id) {
        Validate.notNull(id);
        Validate.notNull(lesson);

        Course course = courseDao.getById(id);
        Lesson lesson1 = lessonDao.getById(lesson.getId());

        course.getLesson().remove(lesson1);
        lessonDao.delete(lesson1);
    }

    /**
     * This method is used to get all lessons by course.
     *
     * @param course This parameter is course.
     * @return List This returns the list of lessons.
     */
    @Override
    public List<LessonDto> getAllLesson(CourseDto course) {
        return course.getLessonDto();
    }

    /**
     * This method is used to add test to course.
     *
     * @param idCourse This parameter is ID´s course.
     * @param idBankQuestions This parameter is ID of test.
     */
    @Override
    public void addBankQuestions(Long idCourse, Long idBankQuestions) {
        Validate.notNull(idBankQuestions);
        Validate.notNull(idCourse);

        Course entity = new Course();
        entity = courseDao.getById(idCourse);
        List<BankQuestions> list = new ArrayList<BankQuestions>();
        list = entity.getBankQuestions();

        BankQuestions question = new BankQuestions();
        question = bankDao.getById(idBankQuestions);
        //nastavenie poradoveho cisla
        int serialNumber = 1;
        if (list != null) {
            if (list.size() != 0) {
                serialNumber = list.size() + 1;
            }
        }
        question.setSerialNumber(serialNumber);
        question.setCourse(entity);

        entity.getBankQuestions().add(question);
        courseDao.update(entity);

        List<CourseItem> listCourseItem = new ArrayList<CourseItem>(courseItemDao.getAll());
        String courseId = String.valueOf(entity.getId());
        if (listCourseItem != null) {
            for (Iterator<CourseItem> it = listCourseItem.iterator(); it.hasNext();) {
                CourseItem courseItem = it.next();
                if (courseItem.getCourseId().equals(courseId)) {
                    UserBankQ userBankQ = new UserBankQ();
                    userBankQ.setId(null);
                    userBankQ.setBankQuestions(String.valueOf(idBankQuestions));
                    userBankQ.setCourseItem(courseItem);
                    userBankQ.setDone(false);
                    userBankQ.setItemAttempts(null);
                    userBankQ.setPoints(0);
                    userBankQ.setSerialNumber(serialNumber);
                    userBankDao.create(userBankQ);
                }
            }
        }
    }

    /**
     * This method is used to remove test from test.
     *
     * @param idCourse This parameter is ID´s course.
     * @param idBankQuestions This parameter is ID of test.
     */
    @Override
    public void removeBankQuestions(Long idCourse, Long idBankQuestions) {
        Validate.notNull(idCourse);
        Validate.notNull(idBankQuestions);

        Course entity = new Course();
        entity = courseDao.getById(idCourse);

        BankQuestions question = new BankQuestions();
        question = bankDao.getById(idBankQuestions);

        List<BankQuestions> list = entity.getBankQuestions();
        if (list.size() > 1) {
            for (Iterator<BankQuestions> it = list.iterator(); it.hasNext();) {
                BankQuestions bankQuestions = it.next();
                if (question.getSerialNumber() < bankQuestions.getSerialNumber()) {
                    bankQuestions.setSerialNumber(bankQuestions.getSerialNumber() - 1);
                }
            }
        }
        List<UserBankQ> listUserBankQ = new ArrayList<UserBankQ>(userBankDao.getAllUserBankQByBankQ(String.valueOf(idBankQuestions)));
        if (listUserBankQ != null) {
            for (UserBankQ userBankQ : listUserBankQ) {
                List<ItemAttempts> listItemAttempts = new ArrayList<ItemAttempts>(userBankQ.getItemAttempts());
                if (listItemAttempts != null) {
                    for (ItemAttempts itemAttempts : listItemAttempts) {
                        userBankQ.getItemAttempts().remove(itemAttempts);
                        userBankDao.update(userBankQ);
                        itemAttempts.setUserBankQ(null);
                        itemAttemptsDao.delete(itemAttempts);
                    }
                }
                userBankDao.delete(userBankQ);
            }
        }
        entity.getBankQuestions().remove(question);
        courseDao.update(entity);
        question.setCourse(null);
        question.setSerialNumber(0);
    }

    /**
     * This method is used to get all bank of documents.
     *
     * @param course This parameter is course.
     * @return List This returns the list of bank´s documents.
     */
    @Override
    public List<GroupDocumentsDto> getAllGroupDoc(CourseDto course) {
        return course.getGroupDocuments();
    }

    /**
     * This method is used to add bank of documents to course.
     *
     * @param groupDocDto This parameter is bank of documents course.
     * @param id This parameter is ID of course.
     */
    @Override
    public void addGroupDoc(GroupDocumentsDto groupDocDto, Long id) {
        Validate.notNull(id);
        Course course = new Course();
        course = courseDao.getById(id);
        List<GroupDocuments> listGroup = new ArrayList<GroupDocuments>();
        listGroup = course.getGroupDocuments();
        int serialNumber = 1;
        if (listGroup != null) {
            if (listGroup.size() != 0) {
                serialNumber = listGroup.size() + 1;
            }
        }
        groupDocDto.setSerialNumber(serialNumber);
        GroupDocuments groupDoc = groupDao.createO(Mapper.toEntity(null, groupDocDto));

        groupDoc.setCourse(course);

        course.getGroupDocuments().add(groupDoc);
        courseDao.update(course);
    }

    /**
     * This method is used to remove bank of documents from test.
     *
     * @param groupDocDto This parameter is bank of documents.
     * @param id This parameter is ID of course.
     */
    @Override
    public void removeGroupDoc(GroupDocumentsDto groupDocDto, Long id) {
        Validate.notNull(id);
        Validate.notNull(groupDocDto);

        Course entity = new Course();
        entity = courseDao.getById(id);

        GroupDocuments entityGroupDoc = new GroupDocuments();
        entityGroupDoc = groupDao.getById(groupDocDto.getId());

        List<GroupDocuments> list = new ArrayList<GroupDocuments>(entity.getGroupDocuments());
        if (list.size() > 1) {
            for (Iterator<GroupDocuments> it = list.iterator(); it.hasNext();) {
                GroupDocuments groupD = it.next();
                if (entityGroupDoc.getSerialNumber() < groupD.getSerialNumber()) {
                    LOG.info("old " + groupD.getSerialNumber());
                    groupD.setSerialNumber(groupD.getSerialNumber() - 1);
                    LOG.info("new " + groupD.getSerialNumber());
                }

            }
        }

        entity.getGroupDocuments().remove(entityGroupDoc);
        courseDao.update(entity);
        entityGroupDoc.setCourse(null);
        groupDao.delete(entityGroupDoc);
    }

    /**
     * This method is used to change visible and invisible course in the portal.
     *
     * @param item This parameter is boolean value, if it is true, course is
     * visible in the portal. If the value is false, course is invisible in the
     * portal.
     * @param id This parameter is ID of course.
     */
    @Override
    public void changeActivate(Boolean item, Long id) {
        Validate.notNull(id);
        Validate.notNull(item);

        Course entity = courseDao.getById(id);
        entity.setVisible(item);
    }

    /**
     * This method is used to change number of courses in category.
     *
     * @param coursePto This parameter is coursePto from form.
     * @param courseDto This parameter is courseDto.
     * @param categoryDto This parameter is categoryDto.
     */
    @Override
    public void changeStateCategory(CoursePto coursePto, CourseDto courseDto, CategoryDto categoryDto) {

        if (courseDto.getCategory().equals(coursePto.getCategory())) {
            courseDto.setCategory(categoryDto);
        } else {
            // add + 1 to new category
            Category entityCategory = categoryDao.getById(categoryDto.getId());
            entityCategory.setNumberCourses(entityCategory.getNumberCourses() + 1);
            // add -1 to old category
            Category entityOldCategory = categoryDao.getById(courseDto.getCategory().getId());
            entityOldCategory.setNumberCourses(entityOldCategory.getNumberCourses() - 1);

            courseDto.setCategory(categoryDto);
        }
    }

    /**
     * This method is used to add icon to test.
     *
     * @param listBankQ This parameter is list of tests. 
     * @return List This returns the list of tests with new icon.
     */
    @Override
    public List<BankQuestionsDto> addIconToItems(List<BankQuestionsDto> listBankQ) {
        if (listBankQ != null) {
            for (Iterator<BankQuestionsDto> it = listBankQ.iterator(); it.hasNext();) {
                BankQuestionsDto bankQuestionsDto = it.next();
                if (bankQuestionsDto.isCompleted() == true) {
                    bankQuestionsDto.setNameIcon("/images/testIconOk.png");
                } else {
                    bankQuestionsDto.setNameIcon("/images/testIcon.png");
                }

            }
        }
        return listBankQ;
    }

    /**
     * This method is used to get all course by category.
     *
     * @param category This parameter is category
     * @return List This returns the list of all courses.
     */
    @Override
    public List<CourseDto> getAllCourseByCategory(CategoryDto category) {
        Category entity = categoryDao.getById(category.getId());
        List<CourseDto> courseDto = new ArrayList<CourseDto>();
        List<Course> course = courseDao.getAll();
        for (Iterator<Course> it = course.iterator(); it.hasNext();) {
            Course course1 = it.next();
            if (course1.getCategory().getId().equals(category.getId())) {
                courseDto.add(Mapper.toDto(course1));
            }
        }
        return courseDto;
    }

    /**
     * This method is used to search in table of courses.
     *
     * @param searchWord This parameter is word which we want to search.
     * @param list This parameter is list of courses.
     * @return List This returns the list of courses where the word is being
     * searched.
     */
    @Override
    public List<CourseDto> searchInList(String searchWord, List<CourseDto> list) {
        String search = searchWord.toLowerCase();
        List<CourseDto> listNew = new ArrayList<CourseDto>();
        for (CourseDto courseDto : list) {
            String name = courseDto.getName().toLowerCase();
            if (name.contains(search)) {
                listNew.add(courseDto);
            }
        }
        return listNew;
    }

    /**
     * This method is used to finding the empty course. (without lesson, tests
     * and banks of documents)
     *
     * @param dto This parameter is course.
     * @return boolean This returns the value true if all course is
     * empty(without lesson, tests and banks of documents)
     */
    @Override
    public boolean isEmptyCourse(CourseDto dto) {
        boolean isEmpty = false;

        boolean bankQ = false;
        boolean bankDoc = false;
        boolean lesson = false;
        bankQ = ((dto.getBankQuestionsDto() == null || dto.getBankQuestionsDto().size() == 0) ? true : false);
        LOG.info("BankQ " + bankQ);

        bankDoc = ((dto.getGroupDocuments() == null) || (dto.getGroupDocuments().size() == 0) ? true : false);
        LOG.info("GroupDocuments " + bankDoc);

        lesson = ((dto.getLessonDto() == null) || (dto.getLessonDto().size() == 0) ? true : false);
        LOG.info("Lesson" + lesson);

        if (bankQ == true && bankDoc == true && lesson == true) {
            isEmpty = true;
        }

        return isEmpty;
    }

}
