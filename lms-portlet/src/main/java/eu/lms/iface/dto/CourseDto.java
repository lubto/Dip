package eu.lms.iface.dto;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Lubomir Lacika Data Transfer Object for Course entity.
 */
public class CourseDto extends BaseDto<Long> implements Comparable<CourseDto> {

    private Long id;
    private CategoryDto category;
    private String name;
    private String shortName;
    private String description;
    private String userCreatorId;
    private boolean visible;
    private List<LessonDto> lessonDto;
    private Date dateOfCreation;
    private List<BankQuestionsDto> bankQuestionsDto;
    private List<GroupDocumentsDto> groupDocuments;
    private boolean added;

    private boolean visibleDocuments; 

    private boolean visibleQuestions;

    private boolean visibleLessons;

    public CourseDto(Long id, CategoryDto category, String name, String shortName, String description, String userCreatorId, boolean visible, List<LessonDto> lessonDto, Date dateOfCreation, List<BankQuestionsDto> bankQuestionsDto, List<GroupDocumentsDto> groupDocuments, boolean added, boolean visibleDocuments, boolean visibleQuestions,boolean visibleLessons) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.shortName = shortName;
        this.description = description;
        this.userCreatorId = userCreatorId;
        this.visible = visible;
        this.lessonDto = lessonDto;
        this.dateOfCreation = dateOfCreation;
        this.bankQuestionsDto = bankQuestionsDto;
        this.groupDocuments = groupDocuments;
        this.added = added;
        this.visibleDocuments = visibleDocuments;
        this.visibleLessons = visibleLessons;
        this.visibleQuestions = visibleQuestions;
    }

    public CourseDto() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserCreatorId() {
        return userCreatorId;
    }

    public void setUserCreatorId(String userCreatorId) {
        this.userCreatorId = userCreatorId;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public List<LessonDto> getLessonDto() {
        return lessonDto;
    }

    public void setLessonDto(List<LessonDto> lessonDto) {
        this.lessonDto = lessonDto;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public List<BankQuestionsDto> getBankQuestionsDto() {
        return bankQuestionsDto;
    }

    public void setBankQuestionsDto(List<BankQuestionsDto> bankQuestionsDto) {
        this.bankQuestionsDto = bankQuestionsDto;
    }

    public List<GroupDocumentsDto> getGroupDocuments() {
        return groupDocuments;
    }

    public void setGroupDocuments(List<GroupDocumentsDto> groupDocuments) {
        this.groupDocuments = groupDocuments;
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    @Override
    public int compareTo(CourseDto o) {
        return (this.name).compareTo(o.name);
    }

    public boolean isVisibleDocuments() {
        return visibleDocuments;
    }

    public void setVisibleDocuments(boolean visibleDocuments) {
        this.visibleDocuments = visibleDocuments;
    }

    public boolean isVisibleQuestions() {
        return visibleQuestions;
    }

    public void setVisibleQuestions(boolean visibleQuestions) {
        this.visibleQuestions = visibleQuestions;
    }

    public boolean isVisibleLessons() {
        return visibleLessons;
    }

    public void setVisibleLessons(boolean visibleLessons) {
        this.visibleLessons = visibleLessons;
    }

}
