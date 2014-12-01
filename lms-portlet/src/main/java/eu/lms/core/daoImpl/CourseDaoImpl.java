package eu.lms.core.daoImpl;

import eu.lms.core.entity.Category;
import eu.lms.core.entity.Course;
import eu.lms.iface.dao.CourseDao;
import java.util.List; 
import javax.persistence.TypedQuery; 
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lubomir Lacika
 * Base generic class providing access to the data layer.
 */
@Repository
public class CourseDaoImpl extends GenericDaoImpl<Course> implements CourseDao {
/**
   * This method is used to find all courses by userID. "ID" is user´s ID who created the course.
   * @param userId This parameter is user´s ID. 
   * @return List This returns the list of courses.
   */ 
    @Override
    public List<Course> getAllCourseByUserId(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId is null");
        }
        List<Course> course = null;
        TypedQuery<Course> query = null;
        query = em.createQuery("SELECT u FROM Course u WHERE u.createrId = :createrId", Course.class).setParameter("createrId", userId);
        course = query.getResultList();

        return course;
    }
/**
   * This method is used to get all courses by category. 
   * @param category This parameter is category. 
   * @return List This returns the list of courses.
   */
    @Override
    public List<Course> getAllCourseByCategory(Category category) {
       if (category == null) {
            throw new IllegalArgumentException("Category is null");
        }
        List<Course> course = null;
        TypedQuery<Course> query = null;
        query = em.createQuery("SELECT u FROM Course u WHERE u.category = :category", Course.class).setParameter("category", category.getId());
        course = query.getResultList();

        return course;
    }

}
