package eu.lms.iface.dao;

import eu.lms.core.entity.Course;
import eu.lms.core.entity.CourseItem;
import java.util.List;

/**
 *
 * @author Lubomir Lacika 
 * Base generic interface providing access to the data
 * layer.
 */
public interface CourseItemDao {

    /**
     * This method is used to find all users ID from CourseItem by course´s ID
     * and if is course item visible.
     *
     * @param courseId This parameter is course´s ID.
     * @param visibleCourseItem This parameter is true if is courseItem visible
     * on the portal.
     * @return List This returns the list of users ID´s.
     */
    public List<String> getAllCourseItemByUserId(String courseId, Boolean visibleCourseItem);

    /**
     * This method is used to get user ID from CourseItem.
     *
     * @param courseId This parameter is user´s ID.
     * @return courseItem This returns the courseItem.
     */
    public CourseItem getCourseItemByUserId(String userId);
/**
   * This method is used to find all courseItems from CourseItem by course´s ID.
   * @param courseId This parameter is course´s ID.  
   * @return List This returns the list of CourseItems.
   */ 
    public List<CourseItem> getAllCourseItemByUserId(Long courseId);
}
