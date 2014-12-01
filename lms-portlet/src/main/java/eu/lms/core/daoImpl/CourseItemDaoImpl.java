package eu.lms.core.daoImpl;

import eu.lms.core.entity.Course;
import eu.lms.core.entity.CourseItem;
import eu.lms.iface.dao.CourseItemDao;
import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lubomir Lacika
 * Base generic class providing access to the data layer.
 */
@Repository
public class CourseItemDaoImpl extends GenericDaoImpl<CourseItem> implements CourseItemDao {
/**
   * This method is used to find all users ID from CourseItem by course´s ID and if is course item visible.
   * @param courseId This parameter is course´s ID. 
   * @param visibleCourseItem This parameter is true if is courseItem visible on the portal.
   * @return List This returns the list of users ID´s.
   */ 
    @Override
    public List<String> getAllCourseItemByUserId(String courseId, Boolean visibleCourseItem) {
        if (courseId.isEmpty()) {
            throw new IllegalArgumentException("UserId is null");
        }
        List<String> result = null;
        TypedQuery<String> query = null;
        query = em.createQuery("SELECT u.userId FROM CourseItem u WHERE u.courseId = :courseId AND u.visible = :visible", String.class).setParameter("courseId", courseId).setParameter("visible", visibleCourseItem);
        result = query.getResultList();

        return result;
    }
/**
   * This method is used to get user ID from CourseItem.
   * @param courseId This parameter is user´s ID.  
   * @return courseItem This returns the courseItem.
   */
    @Override
    public CourseItem getCourseItemByUserId(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId is null");
        }
        CourseItem courseItem = null;
        TypedQuery query = null;
        query = em.createQuery("SELECT u FROM CourseItem u WHERE u.userId = :userId", CourseItem.class).setParameter("userId", userId);
        courseItem = (CourseItem) query.getSingleResult();

        return courseItem;
    } 
/**
   * This method is used to find all courseItems from CourseItem by course´s ID.
   * @param courseId This parameter is course´s ID.  
   * @return List This returns the list of CourseItems.
   */ 
    @Override
    public List<CourseItem> getAllCourseItemByUserId(Long courseId) {
              if (courseId ==0 || courseId <= 0) {
            throw new IllegalArgumentException("UserId is 0 or negative");
        }
        List<CourseItem> result = null;
        TypedQuery<CourseItem> query = null;
        query = em.createQuery("SELECT u FROM CourseItem u WHERE u.courseId = :courseId", CourseItem.class).setParameter("courseId", courseId.toString());
        result = query.getResultList();

        return result;
    }

 
}
