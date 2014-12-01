package eu.lms.iface.service;

import eu.lms.iface.dto.BankQuestionsDto;
import eu.lms.iface.dto.CategoryDto;
import eu.lms.iface.dto.CourseDto;
import eu.lms.iface.dto.GroupDocumentsDto;
import eu.lms.iface.dto.LessonDto;
import eu.lms.portlet.mainright.pto.CoursePto;
import java.util.List;

/**
 * Service providing access to the data-access-layer.
 *
 * @author Lubomir Lacika
 */
public interface CourseService {

    /**
     * This method is used to get all course.
     *
     * @return List This returns the list of course.
     */
    List<CourseDto> getAllCourse();

    /**
     * This method is used to save course.
     *
     * @param courseDto This parameter is entity which we want to save/update.
     */
    void saveCourse(CourseDto courseDto);

    /**
     * This method is used to create course.
     *
     * @param courseDto This parameter is entity which we want to create.
     */
    void createCourse(CourseDto courseDto);

    /**
     * This method is used to get course by ID.
     *
     * @param id This parameter is ID of course.
     * @return courseDto This returns the course.
     */
    CourseDto getCourseById(Long id);

    /**
     * This method is used to delete course by ID.
     *
     * @param id This parameter is ID of course.
     * @return CourseDto This returns the course.
     */
    CourseDto deleteCourseById(Long id);

    /**
     * This method is used to get course by user´s ID.
     *
     * @param userId This parameter is ID of user.
     * @return CourseDto This returns the CourseDto.
     */
    List<CourseDto> getAllCourseByUserId(String userId);

    /**
     * This method is used to add lesson to course.
     *
     * @param lesson This parameter is lesson.
     * @param id This parameter is ID of course.
     */
    void addLesson(LessonDto lesson, Long id);

    /**
     * This method is used to remove lesson from course.
     *
     * @param lesson This parameter is lesson.
     * @param id This parameter is ID of course.
     */
    void removeLesson(LessonDto lesson, Long id);

    /**
     * This method is used to get all lessons by course.
     *
     * @param course This parameter is course.
     * @return List This returns the list of lessons.
     */
    List<LessonDto> getAllLesson(CourseDto course);

    /**
     * This method is used to add test to course.
     *
     * @param idCourse This parameter is ID´s course.
     * @param idBankQuestions This parameter is ID of test.
     */
    void addBankQuestions(Long idCourse, Long idBankQuestions);

    /**
     * This method is used to remove test from test.
     *
     * @param idCourse This parameter is ID´s course.
     * @param idBankQuestions This parameter is ID of test.
     */
    void removeBankQuestions(Long idCourse, Long idBankQuestions);

    /**
     * This method is used to get all bank of documents.
     *
     * @param course This parameter is course.
     * @return List This returns the list of bank´s documents.
     */
    List<GroupDocumentsDto> getAllGroupDoc(CourseDto course);

    /**
     * This method is used to add bank of documents to course.
     *
     * @param groupDocDto This parameter is bank of documents course.
     * @param id This parameter is ID of course.
     */
    void addGroupDoc(GroupDocumentsDto groupDocDto, Long id);

    /**
     * This method is used to remove bank of documents from test.
     *
     * @param groupDocDto This parameter is bank of documents.
     * @param id This parameter is ID of course.
     */
    void removeGroupDoc(GroupDocumentsDto groupDocDto, Long id);

    /**
     * This method is used to change visible and invisible course in the portal.
     *
     * @param item This parameter is boolean value, if it is true, course is
     * visible in the portal. If the value is false, course is invisible in the
     * portal.
     * @param id This parameter is ID of course.
     */
    void changeActivate(Boolean item, Long id);

     /**
     * This method is used to change number of courses in category.
     *
     * @param coursePto This parameter is coursePto from form.
     * @param courseDto This parameter is courseDto.
     * @param categoryDto This parameter is categoryDto.
     */
    void changeStateCategory(CoursePto coursePto, CourseDto courseDto, CategoryDto categoryDto);

    /**
     * This method is used to get all course by category.
     *
     * @param category This parameter is category
     * @return List This returns the list of all courses.
     */
    List<CourseDto> getAllCourseByCategory(CategoryDto category);

    /**
     * This method is used to search in table of courses.
     *
     * @param searchWord This parameter is word which we want to search.
     * @param list This parameter is list of courses.
     * @return List This returns the list of courses where the word is being
     * searched.
     */
    List<CourseDto> searchInList(String searchWord, List<CourseDto> list);

   /**
     * This method is used to add icon to test.
     *
     * @param listBankQ This parameter is list of tests. 
     * @return List This returns the list of tests with new icon.
     */
    List<BankQuestionsDto> addIconToItems(List<BankQuestionsDto> listBankQ);

    /**
     * This method is used to finding the empty course. (without lesson, tests
     * and banks of documents)
     *
     * @param dto This parameter is course.
     * @return boolean This returns the value true if all course is
     * empty(without lesson, tests and banks of documents)
     */
    boolean isEmptyCourse(CourseDto dto);

}
