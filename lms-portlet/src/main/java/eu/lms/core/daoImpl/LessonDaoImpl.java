package eu.lms.core.daoImpl;

import eu.lms.core.entity.Lesson;
import eu.lms.iface.dao.LessonDao;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lubomir Lacika
 * Base generic class providing access to the data layer.
 */
@Repository
public class LessonDaoImpl extends GenericDaoImpl<Lesson> implements LessonDao {
/**
   * This method is used to find lesson by page number.
   * @param pageNumber This parameter is page number of lesson.  
   * @return Lesson This returns the Lesson.
   */
    @Override
    public Lesson getLessonByPageNumber(int pageNumber) {
        if (pageNumber != -1) {
            throw new IllegalArgumentException("Page number is a negative number.");
        }
        Lesson lesson = null;
        TypedQuery query = null;

        query = em.createQuery("SELECT u FORM Lesson u WHERE u.serialNumber = :serialNumber", Lesson.class).setParameter("serialNumber", pageNumber);
        lesson = (Lesson) query.getSingleResult();

        return lesson;

    }

}
