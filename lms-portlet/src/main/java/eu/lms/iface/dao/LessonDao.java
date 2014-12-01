package eu.lms.iface.dao;

import eu.lms.core.entity.Lesson;

/**
 *
 * @author TO-Lubomir
 * Base generic interface providing access to the data layer.
 */
public interface LessonDao {
/**
   * This method is used to find lesson by page number.
   * @param pageNumber This parameter is page number of lesson.  
   * @return Lesson This returns the Lesson.
   */
    public Lesson getLessonByPageNumber(int number);
}
