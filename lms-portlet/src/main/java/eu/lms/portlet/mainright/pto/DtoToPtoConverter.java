package eu.lms.portlet.mainright.pto;

import eu.lms.iface.dto.BankQuestionsDto;
import eu.lms.iface.dto.CategoryDto;
import eu.lms.iface.dto.CourseDto;
import eu.lms.iface.dto.CourseItemDto;
import eu.lms.iface.dto.GroupDocumentsDto;
import eu.lms.iface.dto.ItemMultipleChoiceDto;
import eu.lms.iface.dto.LessonDto;
import eu.lms.iface.dto.MultipleChoiceDto;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converter for CategoryDto, CourseDto, LessonDto, BankQuestionsDto,
 * CourseItemDto, GroupDocumetnsDto, MultipleChoiceDto, ItemMultipleChoiceDto.
 */
@Component
public class DtoToPtoConverter implements Converter<CategoryDto, CategoryPto> {

    private static final Logger LOG = Logger.getLogger(DtoToPtoConverter.class);

    @Override
    public CategoryPto convert(CategoryDto s) {
        CategoryPto pto = new CategoryPto();

        pto.setId(s.getId());
        pto.setName(s.getName());
        pto.setDescription(s.getDescription());
        pto.setVisible(s.isVisible());
        pto.setFreeCourse(s.isFreeCourse());
        pto.setNumberCourses(s.getNumberCourses());

        return pto;
    }

    public CoursePto convert(CourseDto s) {
        CoursePto pto = new CoursePto();

        pto.setId(s.getId());
        pto.setName(s.getName());
        pto.setShortName(s.getShortName());
        pto.setDescription(s.getDescription());
        pto.setCategory(s.getCategory().getId());
        pto.setCreaterId(s.getUserCreatorId());
        //pto.setVisible(s.getVisible());
        pto.setDateOfCreation(s.getDateOfCreation());
        pto.setVisibleDocuments(s.isVisibleDocuments());
        pto.setVisibleLessons(s.isVisibleLessons());
        pto.setVisibleDocuments(s.isVisibleDocuments());
        
        return pto;
    }

    public LessonPto convert(LessonDto s) {
        LessonPto pto = new LessonPto();

        pto.setId(s.getId());
        pto.setName(s.getName());
        pto.setStudyTime(s.getStudyTime());
        pto.setVisible(s.isVisible());
        pto.setContent(s.getContent());
        pto.setCourseId(s.getCourseDto().getId());
        pto.setSerialNumber(s.getSerialNumber());
        LOG.info("pto converter: " + s.getSerialNumber());
        return pto;

    }

    public UserPto convert(Long s, String s1) {
        UserPto pto = new UserPto();

        pto.setId(s);
        pto.setFullName(s1);

        return pto;
    }

    public BankQuestionsPto convert(BankQuestionsDto dto) {
        BankQuestionsPto pto = new BankQuestionsPto();

        pto.setId(dto.getId());
        pto.setName(dto.getName());
        pto.setDescription(dto.getDescription());
        pto.setAllQBank(dto.isAllQBank());
        pto.setRandomQ(dto.isRandomQ());
        pto.setRandomSubQ(dto.isRandomSubQ());
        pto.setNumberAllQ(dto.getNumberAllQ());
        pto.setCompleted(dto.isCompleted());
        pto.setInPercentDone(dto.getInPercentDone());
        pto.setNumberQForTest(dto.getNumberQForTest());

        return pto;
    }

    public GroupDocPto convert(GroupDocumentsDto dto) {
        GroupDocPto pto = new GroupDocPto();

        pto.setId(dto.getId());
        pto.setName(dto.getName());
        pto.setDescription(dto.getDescription());
        pto.setVisible(dto.isVisible());

        return pto;
    }

    public CourseItemPto convert(CourseItemDto dto) {
        CourseItemPto pto = new CourseItemPto();

        pto.setId(dto.getId());
        pto.setFullName(dto.getFullName());
        pto.setNameCourse(dto.getNameCourse());
        pto.setVisible(dto.getVisible());
        pto.setFinishedCourse(dto.isFinishedCourse());
        pto.setVisitedCourse(dto.isVisitedCourse());
        pto.setStartCourse(dto.getStartCourse());
        pto.setEndCourse(dto.getEndCourse());

        return pto;
    }

    public MultipleChoicePto convert(MultipleChoiceDto dto) {
        MultipleChoicePto pto = new MultipleChoicePto();

        pto.setId(dto.getId());
        pto.setPoints(dto.getPoints());
        pto.setQuestion(dto.getQuestion());
        if (dto.getItems() != null && dto.getItems().size() > 0) {
            List<ItemMultiplechoicePto> itemMC = new ArrayList<ItemMultiplechoicePto>();
            for (int i = 0; i < dto.getItems().size(); i++) {
                LOG.info("LOG convert " + convert(dto.getItems().get(i)).getText());
                itemMC.add(convert(dto.getItems().get(i)));
            }
            pto.setItemsM(itemMC);
        }
        return pto;
    }

    public ItemMultiplechoicePto convert(ItemMultipleChoiceDto dto) {
        ItemMultiplechoicePto pto = new ItemMultiplechoicePto();

        pto.setId(dto.getId());
        pto.setText(dto.getText());
        pto.setAnswer(dto.isAnswer());

        return pto;
    }
}
