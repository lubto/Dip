package eu.lms.iface.dao;

import eu.lms.core.entity.Category;
import eu.lms.core.entity.Course;
import java.util.List;

/**
 *
 * @author Lubomir Lacika
 * Base generic interface providing access to the data layer.
 */
public interface CourseDao {
    /**
   * This method is used to find all courses by userID. "ID" is user´s ID who created the course.
   * @param userId This parameter is user´s ID. 
   * @return List This returns the list of courses.
   */ 
    public List<Course> getAllCourseByUserId(String userId); 
    /**
   * This method is used to get all courses by category. 
   * @param category This parameter is category. 
   * @return List This returns the list of courses.
   */
    public List<Course> getAllCourseByCategory(Category category); 
}
