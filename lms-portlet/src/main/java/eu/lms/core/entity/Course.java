package eu.lms.core.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany; 
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Lubomir Lacika
 * Course entity.
 */
@Entity
@Table(name = "course")
public class Course extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_category")
    private Category category;

    @OneToMany(mappedBy = "courseId", cascade = CascadeType.ALL)
    private List<Lesson> lesson;
    
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<GroupDocuments> groupDocuments;

    @Column(name = "creater_id")
    private String createrId;

    private String name;

    private String shortName;
    
    @Column(length = 500)    
    private String description;

    private boolean visible;
    
    @Column(name = "bank_questions")
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<BankQuestions> bankQuestions;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateOfCreation;
    
    // settings for visible or invisible bank of documents, bank of questions, lessons.
    private boolean visibleDocuments;
    
    private boolean visibleQuestions;
    
    private boolean visibleLessons;

 
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
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
 
    public List<Lesson> getLesson() {
        return lesson;
    }

    public void setLesson(List<Lesson> lesson) {
        this.lesson = lesson;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public List<BankQuestions> getBankQuestions() {
        return bankQuestions;
    }

    public void setBankQuestions(List<BankQuestions> bankQuestions) {
        this.bankQuestions = bankQuestions;
    } 

    public List<GroupDocuments> getGroupDocuments() {
        return groupDocuments;
    }

    public void setGroupDocuments(List<GroupDocuments> groupDocuments) {
        this.groupDocuments = groupDocuments;
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
