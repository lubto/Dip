package eu.lms.core.serviceImpl;

import eu.lms.core.daoImpl.CourseDaoImpl;
import eu.lms.core.daoImpl.LessonDaoImpl;
import eu.lms.core.entity.Course;
import eu.lms.core.entity.Lesson;
import eu.lms.core.mapper.Mapper;
import eu.lms.iface.dto.LessonDto;
import eu.lms.iface.service.LessonService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Lubomir Lacika Service providing access to the data-access-layer.
 */
@Service
@Transactional
public class LessonServiceImpl implements LessonService {

    protected final Logger LOG = Logger.getLogger(LessonServiceImpl.class);

    @Autowired
    private LessonDaoImpl lessonDao;
    @Autowired
    private CourseDaoImpl courseDao;

    @Override
    public List<LessonDto> getAllLesson() {
        List<Lesson> entities = lessonDao.getAll();

        List<LessonDto> dtos = new ArrayList<LessonDto>(entities.size());

        for (Lesson entity : entities) {
            dtos.add(Mapper.toDtos(entity));
        }
        return dtos;
    }

    @Override
    public void save(LessonDto lessonDto) {
        Validate.notNull(lessonDto);
        Validate.notNull(lessonDto.getId());

        Lesson lesson = lessonDao.getById(lessonDto.getId());
        //Lesson updateLesson = lessonDao.update(Mapper.toEntity(lesson, lessonDto));
        lessonDao.update(Mapper.toEntity(lesson, lessonDto));
        //LOG.info("new log " + lessonDto.getCourseDto().getId()); 
        //return Mapper.toDto(updateLesson);
    }

    @Override
    public void create(LessonDto lessonDto) {
        Validate.notNull(lessonDto);
        Validate.isTrue(lessonDto.getId() == null);

        // Lesson savedLesson = lessonDao.create(Mapper.toEntity(null, lessonDto));
        lessonDao.create(Mapper.toEntity(null, lessonDto));

        // return Mapper.toDto(savedLesson);
    }

    @Override
    public LessonDto getById(Long id) {
        Validate.notNull(id);

        Lesson lesson = lessonDao.getById(id);
        return Mapper.toDtos(lesson);
    }

    @Override
    public LessonDto deleteById(Long id) {
        Validate.notNull(id);

        Lesson lesson = lessonDao.getById(id);

        Course dto = courseDao.getById(lesson.getCourseId().getId());
        List<Lesson> list = dto.getLesson();
        if (list.size() > 1) {
            for (Iterator<Lesson> it = list.iterator(); it.hasNext();) {
                Lesson lesson1 = it.next();
                if (lesson.getSerialNumber() < lesson1.getSerialNumber()) {
                    LOG.info("old " + lesson1.getSerialNumber());
                    lesson1.setSerialNumber(lesson1.getSerialNumber() - 1);
                    LOG.info("new " + lesson1.getSerialNumber());
                }

            }
        } 
        
        dto.getLesson().remove(lesson);
        courseDao.update(dto);
        lesson.setCourseId(null);
        lessonDao.delete(lesson);
        return Mapper.toDto(lesson);
    }

    @Override
    public LessonDto getByPageNumber(int number) {
        Validate.notNull(number);

        Lesson lesson = lessonDao.getLessonByPageNumber(number);

        return Mapper.toDto(lesson);
    }

    @Override
    public void changeActivate(Boolean item, Long id) {
        Validate.notNull(id);
        Validate.notNull(item);

        Lesson entity = lessonDao.getById(id);
        entity.setVisible(item);
    }

    @Override
    public void changePosition(boolean upOrDown, LessonDto lesson) {
        Lesson entity = lessonDao.getById(lesson.getId());
        Course course = courseDao.getById(entity.getCourseId().getId());
        int number = entity.getSerialNumber();
        List<Lesson> list = course.getLesson();
        if (upOrDown == true) {
            if (entity.getSerialNumber() != 1 || entity.getSerialNumber() != 0) {
                for (Lesson lesson1 : list) {
                    if (lesson1.getSerialNumber() == number - 1) {
                        Lesson l = lessonDao.getById(lesson1.getId());
                        l.setSerialNumber(number);
                        entity.setSerialNumber(number - 1);
                        break;
                    }
                }
            }
        } else {
            for (Lesson lesson1 : list) {
                if (lesson1.getSerialNumber() == number + 1) {
                    Lesson l = lessonDao.getById(lesson1.getId());
                    l.setSerialNumber(number);
                    entity.setSerialNumber(number + 1);
                    break;
                }
            }
        }
    }

    @Override
    public List<LessonDto> searchInList(String searchWord, List<LessonDto> list) {
        String search = searchWord.toLowerCase();
        List<LessonDto> listNew = new ArrayList<LessonDto>();
        for (Iterator<LessonDto> it = list.iterator(); it.hasNext();) {
            LessonDto lesson = it.next();
            String name = lesson.getName().toLowerCase();
            if (name.contains(search)) {
                listNew.add(lesson);
            }
        }
        return listNew;
    }

}
