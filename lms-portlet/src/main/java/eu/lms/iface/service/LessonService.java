package eu.lms.iface.service;
 
import eu.lms.iface.dto.LessonDto;
import java.util.List;

/**
 *
 * @author Lubomir Lacika
 * Service providing access to the data-access-layer.
 */
public interface LessonService {
    
    List<LessonDto> getAllLesson();
    
    void save(LessonDto lessonDto);
    
    void create(LessonDto lessonDto);
    
    LessonDto getById(Long id);
    
    LessonDto deleteById(Long id);
    
    LessonDto getByPageNumber(int number);
    
    void changeActivate(Boolean item, Long id);
    
    void changePosition(boolean upOrDown, LessonDto lesson);
    
    List<LessonDto> searchInList(String searchWord, List<LessonDto> list);
}
