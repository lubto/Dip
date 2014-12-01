package eu.lms.portlet.mainright.pto;

import java.util.Date;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Lubomir Lacika PTO for CourseDto
 */
public class CoursePto {

    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String shortName;

    @NotEmpty
    @Length(min = 5, max = 499)
    private String description;

    private boolean visible;

    private Date dateOfCreation;

    //@NotEmpty
    private String createrId;

    private Long category;

    private boolean visibleDocuments;

    private boolean visibleQuestions;

    private boolean visibleLessons;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public boolean getVisibleDocuments() {
        return visibleDocuments;
    }

    public void setVisibleDocuments(boolean visibleDocuments) {
        this.visibleDocuments = visibleDocuments;
    }

    public boolean getVisibleQuestions() {
        return visibleQuestions;
    }

    public void setVisibleQuestions(boolean visibleQuestions) {
        this.visibleQuestions = visibleQuestions;
    }

    public boolean getVisibleLessons() {
        return visibleLessons;
    }

    public void setVisibleLessons(boolean visibleLessons) {
        this.visibleLessons = visibleLessons;
    }
    
}
