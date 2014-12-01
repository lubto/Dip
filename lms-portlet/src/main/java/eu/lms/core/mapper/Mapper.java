package eu.lms.core.mapper;

import eu.lms.core.entity.AccountUser;
import eu.lms.core.entity.BankQuestions;
import eu.lms.core.entity.Category;
import eu.lms.core.entity.Course;
import eu.lms.core.entity.CourseItem;
import eu.lms.core.entity.GroupDocuments;
import eu.lms.core.entity.ItemAttempts;
import eu.lms.core.entity.ItemDocument;
import eu.lms.core.entity.ItemMultipleChoice;
import eu.lms.core.entity.Lesson;
import eu.lms.core.entity.MultipleChoice;
import eu.lms.core.entity.UserBankQ;
import eu.lms.iface.dto.AccountUserDto;
import eu.lms.iface.dto.BankQuestionsDto;
import eu.lms.iface.dto.CategoryDto;
import eu.lms.iface.dto.CourseDto;
import eu.lms.iface.dto.CourseItemDto;
import eu.lms.iface.dto.GroupDocumentsDto;
import eu.lms.iface.dto.ItemAttemptsDto;
import eu.lms.iface.dto.ItemDocumentDto;
import eu.lms.iface.dto.ItemMultipleChoiceDto;
import eu.lms.iface.dto.LessonDto;
import eu.lms.iface.dto.MultipleChoiceDto;
import eu.lms.iface.dto.UserBankQDto;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 *
 * @author Lubomir Lacika 
 * Mapper for all service.
 *
 */
public class Mapper {

    private static final Logger LOG = Logger.getLogger(Mapper.class);

    public static Category toEntity(Category entity, CategoryDto dto) {
        Validate.notNull(dto);
        if (entity == null) {
            entity = new Category();
        }
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setVisible(dto.isVisible());
        entity.setFreeCourse(dto.isFreeCourse());
        entity.setNumberCourses(dto.getNumberCourses());

        return entity;
    }

    public static CategoryDto toDto(Category entity) {
        Validate.notNull(entity);
        CategoryDto dto = new CategoryDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setVisible(entity.isVisible());
        dto.setFreeCourse(entity.isFreeCourse());
        dto.setNumberCourses(entity.getNumberCourses());

        return dto;
    }

    public static Course toEntity(Course entity, CourseDto dto, Category category) {
        Validate.notNull(dto);
        if (entity == null) {
            entity = new Course();
        }

        entity.setName(dto.getName());
        if (category == null) {
            entity.setCategory(toEntity(null, dto.getCategory()));
        } else {
            entity.setCategory(category);
        }

        entity.setCreaterId(dto.getUserCreatorId());
        entity.setDescription(dto.getDescription());
        entity.setShortName(dto.getShortName());
        entity.setVisible(dto.isVisible());
        entity.setDateOfCreation(dto.getDateOfCreation());
        entity.setVisibleDocuments(dto.isVisibleDocuments());
        entity.setVisibleLessons(dto.isVisibleLessons());
        entity.setVisibleQuestions(dto.isVisibleQuestions());

        if (dto.getLessonDto() != null && dto.getLessonDto().size() > 0) {
            List<Lesson> lesson = null;
            for (int i = 0; i < dto.getLessonDto().size(); i++) {

                lesson.add(toEntity(null, dto.getLessonDto().get(i)));
            }
            entity.setLesson(lesson);
        }
        return entity;
    }

    public static Course toEntitys(Course entity, CourseDto dto, Category category) {
        Validate.notNull(dto);
        if (entity == null) {
            entity = new Course();
        }

        entity.setName(dto.getName());
        if (category == null) {
            entity.setCategory(toEntity(null, dto.getCategory()));
        } else {
            entity.setCategory(category);
        }

        entity.setCreaterId(dto.getUserCreatorId());
        entity.setDescription(dto.getDescription());
        entity.setShortName(dto.getShortName());
        entity.setVisible(dto.isVisible());
        entity.setDateOfCreation(dto.getDateOfCreation());
        entity.setVisibleDocuments(dto.isVisibleDocuments());
        entity.setVisibleLessons(dto.isVisibleLessons());
        entity.setVisibleQuestions(dto.isVisibleQuestions());

        return entity;
    }

    public static CourseDto toDto(Course entity) {
        Validate.notNull(entity);
        CourseDto dto = new CourseDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setUserCreatorId(entity.getCreaterId());
        dto.setVisible(entity.isVisible());
        dto.setDescription(entity.getDescription());
        dto.setCategory(toDto(entity.getCategory()));
        dto.setShortName(entity.getShortName());
        dto.setDateOfCreation(entity.getDateOfCreation());
        dto.setVisibleDocuments(entity.isVisibleDocuments());
        dto.setVisibleLessons(entity.isVisibleLessons());
        dto.setVisibleQuestions(entity.isVisibleQuestions());

        if (entity.getGroupDocuments() != null && entity.getGroupDocuments().size() > 0) {
            List<GroupDocumentsDto> groupDto = new ArrayList<GroupDocumentsDto>();
            for (int i = 0; i < entity.getGroupDocuments().size(); i++) {
                groupDto.add(toDto(entity.getGroupDocuments().get(i)));
            }
            dto.setGroupDocuments(groupDto);
        }

        if (entity.getLesson() != null && entity.getLesson().size() > 0) {
            List<LessonDto> lessonDto = new ArrayList<LessonDto>();
            for (int i = 0; i < entity.getLesson().size(); i++) {
                lessonDto.add(toDto(entity.getLesson().get(i)));
            }
            dto.setLessonDto(lessonDto);
        }

        if (entity.getBankQuestions() != null && entity.getBankQuestions().size() > 0) {
            List<BankQuestionsDto> listDto = new ArrayList<BankQuestionsDto>();
            for (int i = 0; i < entity.getBankQuestions().size(); i++) {
                listDto.add(toDto(entity.getBankQuestions().get(i)));
            }
            dto.setBankQuestionsDto(listDto);
        }
        return dto;
    }

    public static Lesson toEntity(Lesson entity, LessonDto dto) {
        Validate.notNull(dto);
        if (entity == null) {
            entity = new Lesson();
            entity.setCourseId(null);
        }
        entity.setName(dto.getName());
        entity.setContent(dto.getContent());
        entity.setVisible(dto.isVisible());
        entity.setStudyTime(dto.getStudyTime());
        entity.setSerialNumber(dto.getSerialNumber());

        return entity;
    }

    public static LessonDto toDto(Lesson entity) {
        Validate.notNull(entity);
        LessonDto dto = new LessonDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setVisible(entity.isVisible());
        dto.setContent(entity.getContent());
        dto.setStudyTime(entity.getStudyTime());
        dto.setSerialNumber(entity.getSerialNumber());

        return dto;
    }

    /**
     * only for LessonDto toDto and for view all lesson
     */
    public static CourseDto onlyToDto(Course entity) {
        Validate.notNull(entity);
        CourseDto dto = new CourseDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setVisibleDocuments(entity.isVisibleDocuments());
        dto.setVisibleLessons(entity.isVisibleLessons());
        dto.setVisibleQuestions(entity.isVisibleQuestions());

        return dto;
    }

    public static LessonDto toDtos(Lesson entity) {
        Validate.notNull(entity);
        LessonDto dto = new LessonDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setVisible(entity.isVisible());
        dto.setContent(entity.getContent());
        dto.setStudyTime(entity.getStudyTime());
        dto.setCourseDto(onlyToDto(entity.getCourseId()));
        dto.setSerialNumber(entity.getSerialNumber());

        return dto;
    }

    public static AccountUser toEntity(AccountUser entity, AccountUserDto dto) {
        Validate.notNull(dto);
        if (entity == null) {
            entity = new AccountUser();
        }

        entity.setUserId(dto.getUserId());
        entity.setDateCreation(dto.getDateCreation());
        entity.setActivate(dto.getActivate());

        if (dto.getListCourses() != null && dto.getListCourses().size() > 0) {
            List<CourseItem> courseItem = null;
            for (int i = 0; i < dto.getListCourses().size(); i++) {

                courseItem.add(toEntity(null, dto.getListCourses().get(i)));
            }
            entity.setListCourses(courseItem);
        }

        return entity;
    }

    public static AccountUser toEntitys(AccountUser entity, AccountUserDto dto) {
        Validate.notNull(dto);
        if (entity == null) {
            entity = new AccountUser();
        }

        entity.setUserId(dto.getUserId());
        entity.setDateCreation(dto.getDateCreation());
        entity.setActivate(dto.getActivate());

        return entity;
    }

    public static AccountUserDto toDto(AccountUser entity) {
        Validate.notNull(entity);
        AccountUserDto dto = new AccountUserDto();

        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setDateCreation(entity.getDateCreation());
        dto.setActivate(entity.isActivate());
        if (entity.getListCourseItems() != null && entity.getListCourseItems().size() > 0) {
            List<CourseItemDto> courseItemDto = new ArrayList<CourseItemDto>();
            for (int i = 0; i < entity.getListCourseItems().size(); i++) {
                courseItemDto.add(toDtos(entity.getListCourseItems().get(i)));
            }
            dto.setListCourses(courseItemDto);
        }

        return dto;
    }

    public static CourseItem toEntity(CourseItem entity, CourseItemDto dto) {
        Validate.notNull(dto);
        if (entity == null) {
            entity = new CourseItem();
        }
        entity.setCourseId(dto.getCourseId());
        entity.setUserId(dto.getUserId());
        entity.setVisitedCourse(dto.isVisitedCourse());
        entity.setFinishedCourse(dto.isFinishedCourse());
        // entity.setAccountUser(toEntitys(null, dto.getAccountUser()));
        entity.setStartCourse(dto.getStartCourse());
        entity.setEndCourse(dto.getEndCourse());
        entity.setVisible(dto.getVisible());

        return entity;
    }

    public static CourseItemDto toDto(CourseItem entity) {
        Validate.notNull(entity);
        CourseItemDto dto = new CourseItemDto();

        dto.setId(entity.getId());
        dto.setVisitedCourse(entity.isVisitedCourse());
        dto.setUserId(entity.getUserId());
        dto.setFinishedCourse(entity.isFinishedCourse());
        dto.setStartCourse(entity.getStartCourse());
        dto.setEndCourse(entity.getEndCourse());
        dto.setCourseId(entity.getCourseId());
        dto.setAccountUser(toDto(entity.getAccountUser()));
        dto.setVisible(entity.getVisible());

        if (entity.getUserBankQ() != null && entity.getUserBankQ().size() > 0) {
            List<UserBankQDto> userBQDto = new ArrayList<UserBankQDto>();
            for (int i = 0; i < entity.getUserBankQ().size(); i++) {
                userBQDto.add(toDto(entity.getUserBankQ().get(i)));
            }
            dto.setUserBankQ(userBQDto);
        }
        return dto;
    }

    /**
     * only for view to AccountUser..because loop
     */
    public static CourseItemDto toDtos(CourseItem entity) {
        Validate.notNull(entity);
        CourseItemDto dto = new CourseItemDto();

        dto.setId(entity.getId());
        dto.setVisitedCourse(entity.isVisitedCourse());
        dto.setUserId(entity.getUserId());
        dto.setFinishedCourse(entity.isFinishedCourse());
        dto.setStartCourse(entity.getStartCourse());
        dto.setEndCourse(entity.getEndCourse());
        dto.setCourseId(entity.getCourseId());
        dto.setVisible(entity.getVisible());

        return dto;
    }

    /**
     * only for view questionTrueFalseDto to Dto
     */
    public static BankQuestionsDto toDtos(BankQuestions entity) {
        BankQuestionsDto dto = new BankQuestionsDto();

        if (entity != null) {
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setDescription(entity.getDescription());
            dto.setInPercentDone(entity.getInPercentDone());
            dto.setUserId(entity.getUserId());
            dto.setSerialNumber(entity.getSerialNumber());
        }
        return dto;
    }

    public static ItemMultipleChoice toEntity(ItemMultipleChoice entity, ItemMultipleChoiceDto dto) {
        Validate.notNull(dto);
        if (entity == null) {
            entity = new ItemMultipleChoice();
        }
        entity.setPoints(dto.getPoints());
        entity.setRightAnswer(dto.isAnswer());
        entity.setText(dto.getText());

        return entity;
    }

    public static ItemMultipleChoiceDto toDto(ItemMultipleChoice entity) {
        Validate.notNull(entity);
        ItemMultipleChoiceDto dto = new ItemMultipleChoiceDto();

        dto.setId(entity.getId());
        dto.setAnswer(entity.isRightAnswer());
        dto.setPoints(entity.getPoints());
        dto.setText(entity.getText());
        dto.setMultipleChoiceDto(toDtos(entity.getMultipleChoice()));

        return dto;
    }

    public static MultipleChoiceDto toDtos(MultipleChoice entity) {
        Validate.notNull(entity);
        MultipleChoiceDto dto = new MultipleChoiceDto();

        dto.setId(entity.getId());
        dto.setPoints(entity.getPoint());
        dto.setUserId(entity.getUserId());
        dto.setQuestionShort(entity.getQuestionShort());

        return dto;
    }

    public static MultipleChoice toEntity(MultipleChoice entity, MultipleChoiceDto dto) {
        Validate.notNull(dto);
        if (entity == null) {
            entity = new MultipleChoice();
        }

        entity.setPoint(dto.getPoints());
        entity.setQuestion(dto.getQuestion());
        entity.setUserId(dto.getUserId());
        entity.setQuestionShort(dto.getQuestionShort());

//        if (dto.getItems() != null && dto.getItems().size() > 0) {
//            List<ItemMultipleChoice> itemMChoice = null;
//            for (int i = 0; i < dto.getItems().size(); i++) {
//                itemMChoice.add(toEntity(null, dto.getItems().get(i)));
//            }
//            entity.setItemMultiplechoice(itemMChoice);
//        }
        return entity;
    }

    public static MultipleChoiceDto toDto(MultipleChoice entity) {
        Validate.notNull(entity);
        MultipleChoiceDto dto = new MultipleChoiceDto();

        dto.setId(entity.getId());
        dto.setPoints(entity.getPoint());
        dto.setQuestion(entity.getQuestion());
        dto.setBankQuestions(toDtos(entity.getBankQuestions()));
        dto.setUserId(entity.getUserId());
        dto.setQuestionShort(entity.getQuestionShort());
        if (entity.getItemMultiplechoice() != null && entity.getItemMultiplechoice().size() > 0) {
            List<ItemMultipleChoiceDto> itemDto = new ArrayList<ItemMultipleChoiceDto>();
            for (int i = 0; i < entity.getItemMultiplechoice().size(); i++) {
                itemDto.add(toDto(entity.getItemMultiplechoice().get(i)));
            }
            dto.setItems(itemDto);
        }
        return dto;
    }

    public static BankQuestions toEntity(BankQuestions entity, BankQuestionsDto dto) {
        Validate.notNull(dto);
        if (entity == null) {
            entity = new BankQuestions();
        }
        //  entity.setPoint(dto.getPoints());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setNumberAllQ(dto.getNumberAllQ());
        entity.setNumberQForTest(dto.getNumberQForTest());
        entity.setRandomQ(dto.isRandomQ());
        entity.setRandomSubQ(dto.isRandomSubQ());
        entity.setAllQBank(dto.isAllQBank());
        entity.setInPercentDone(dto.getInPercentDone());
        entity.setUserId(dto.getUserId());
        entity.setSerialNumber(dto.getSerialNumber());

        return entity;
    }

    public static BankQuestionsDto toDto(BankQuestions entity) {
        Validate.notNull(entity);
        BankQuestionsDto dto = new BankQuestionsDto();

        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setNumberAllQ(entity.getNumberAllQ());
        dto.setNumberQForTest(entity.getNumberQForTest());
        dto.setRandomQ(entity.isRandomQ());
        dto.setRandomSubQ(entity.isRandomSubQ());
        dto.setAllQBank(entity.isAllQBank());
        dto.setInPercentDone(entity.getInPercentDone());
        dto.setSerialNumber(entity.getSerialNumber());
        if (entity.getCourse() != null) {
            dto.setCourse(toDtos(entity.getCourse()));
        }
        //dto.setPoints(entity.getPoint());

        if (entity.getMultipleChoice() != null && entity.getMultipleChoice().size() > 0) {
            List<MultipleChoiceDto> itemMultipleDto = new ArrayList<MultipleChoiceDto>();
            for (int i = 0; i < entity.getMultipleChoice().size(); i++) {
                itemMultipleDto.add(toDto(entity.getMultipleChoice().get(i)));
            }
            dto.setMultipleChoiceDto(itemMultipleDto);
        }
        return dto;
    }

    /**
     * only for Quiz bank for view
     */
    public static CourseDto toDtos(Course entity) {
        Validate.notNull(entity);
        CourseDto dto = new CourseDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setVisibleDocuments(entity.isVisibleDocuments());
        dto.setVisibleLessons(entity.isVisibleLessons());
        dto.setVisibleQuestions(entity.isVisibleQuestions());

        return dto;
    }

    public static ItemDocument toEntity(ItemDocument entity, ItemDocumentDto dto) {
        Validate.notNull(dto);
        if (entity == null) {
            entity = new ItemDocument();
        }
        entity.setLink(dto.getLink());
        entity.setName(dto.getName());
        entity.setVisible(dto.isVisible());
        entity.setIsLink(dto.isIsLink());

        return entity;
    }

    public static ItemDocumentDto toDto(ItemDocument entity) {
        Validate.notNull(entity);
        ItemDocumentDto dto = new ItemDocumentDto();

        dto.setId(entity.getId());
        dto.setLink(entity.getLink());
        dto.setName(entity.getName());
        dto.setVisible(entity.isVisible());
        dto.setGroupDocuments(toDtos(entity.getGroupDocuments()));
        dto.setIsLink(entity.isIsLink());
        return dto;
    }

    public static GroupDocumentsDto toDtos(GroupDocuments entity) {
        Validate.notNull(entity);
        GroupDocumentsDto dto = new GroupDocumentsDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSerialNumber(entity.getSerialNumber());

        return dto;
    }

    public static GroupDocuments toEntity(GroupDocuments entity, GroupDocumentsDto dto) {
        Validate.notNull(dto);
        if (entity == null) {
            entity = new GroupDocuments();
        }

        entity.setName(dto.getName());
        entity.setVisible(dto.isVisible());
        entity.setDescription(dto.getDescription());
        entity.setSerialNumber(dto.getSerialNumber());
        /* if (dto.getCourse() != null) {
         entity.setCourse(toEntity(null, dto.getCourse(), null));
         }*/
        return entity;
    }

    public static GroupDocumentsDto toDto(GroupDocuments entity) {
        Validate.notNull(entity);
        GroupDocumentsDto dto = new GroupDocumentsDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setVisible(entity.isVisible());
        dto.setDescription(entity.getDescription());
        dto.setSerialNumber(entity.getSerialNumber());
        if (entity.getCourse() != null) {
            dto.setCourse(toDtos(entity.getCourse()));
        }

        if (entity.getItemDocument() != null && entity.getItemDocument().size() > 0) {
            List<ItemDocumentDto> itemDto = new ArrayList<ItemDocumentDto>();
            for (int i = 0; i < entity.getItemDocument().size(); i++) {
                itemDto.add(toDto(entity.getItemDocument().get(i)));
            }
            dto.setItemDocumentDto(itemDto);
        }
        return dto;
    }

    public static UserBankQ toEntity(UserBankQ entity, UserBankQDto dto) {
        Validate.notNull(dto);
        if (entity == null) {
            entity = new UserBankQ();
        }
        entity.setPoints(dto.getPoints());
        entity.setDone(dto.isDone());
        //entity.setCourseItem(toEntity(null, dto.getCourseItem()));
        entity.setBankQuestions(dto.getBankQuestions());
        entity.setSerialNumber(dto.getSerialNumber());

        return entity;
    }

    public static UserBankQDto toDto(UserBankQ entity) {
        Validate.notNull(entity);
        UserBankQDto dto = new UserBankQDto();

        dto.setId(entity.getId());
        dto.setPoints(entity.getPoints());
        dto.setDone(entity.isDone());
        dto.setBankQuestions(entity.getBankQuestions());
        dto.setCourseItem(toDtos(entity.getCourseItem()));
        dto.setSerialNumber(entity.getSerialNumber());

        if (entity.getItemAttempts() != null && entity.getItemAttempts().size() > 0) {
            List<ItemAttemptsDto> itemDto = new ArrayList<ItemAttemptsDto>();
            for (int i = 0; i < entity.getItemAttempts().size(); i++) {
                itemDto.add(toDto(entity.getItemAttempts().get(i)));
            }
            dto.setListAttempts(itemDto);
        }

        return dto;
    }

    public static ItemAttempts toEntity(ItemAttempts entity, ItemAttemptsDto dto) {
        Validate.notNull(dto);
        if (entity == null) {
            entity = new ItemAttempts();
        }
        entity.setPoints(dto.getPoints());

        return entity;
    }

    public static ItemAttemptsDto toDto(ItemAttempts entity) {
        Validate.notNull(entity);
        ItemAttemptsDto dto = new ItemAttemptsDto();

        dto.setId(entity.getId());
        dto.setPoints(entity.getPoints());
        if (entity.getUserBankQ() != null) {
            dto.setUserBankQ(toDtos(entity.getUserBankQ()));
        }
        return dto;
    }

    public static UserBankQDto toDtos(UserBankQ entity) {
        Validate.notNull(entity);
        UserBankQDto dto = new UserBankQDto();

        dto.setId(entity.getId());
        dto.setBankQuestions(entity.getBankQuestions());
        dto.setSerialNumber(entity.getSerialNumber());

        return dto;
    }
}
