package eu.lms.core.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Lubomir Lacika
 * BankQuestions entity.
 */
@Entity
@Table(name = "bank_questions")
public class BankQuestions extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String name;
    
    private String description;
    
    private int inPercentDone;

    @OneToMany(mappedBy = "bankQuestions", cascade = CascadeType.PERSIST)
    private List<MultipleChoice> multipleChoice;
 
    @Column(nullable = true, name = "n_all_questions")
    private int numberAllQ;

    @Column(name = "n_questions_for_test")
    private int numberQForTest;

    @Column(name = "random_q")
    private Boolean randomQ;

    @Column(name = "random_sub_q")
    private Boolean randomSubQ;

    @Column(name = "all_q_bank")
    private Boolean allQBank;
     
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Course course;
    
    @Column(nullable = false)
    private String userId;
    
    @Column(nullable = false)
    private int serialNumber;
  
    @Override
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

    public List<MultipleChoice> getMultipleChoice() {
        return multipleChoice;
    }

    public void setMultipleChoice(List<MultipleChoice> multipleChoice) {
        this.multipleChoice = multipleChoice;
    } 

    public int getNumberAllQ() {
        return numberAllQ;
    }

    public void setNumberAllQ(int numberAllQ) {
        this.numberAllQ = numberAllQ;
    }

    public int getNumberQForTest() {
        return numberQForTest;
    }

    public void setNumberQForTest(int numberQForTest) {
        this.numberQForTest = numberQForTest;
    }

    public Boolean isRandomQ() {
        return randomQ;
    }

    public void setRandomQ(Boolean randomQ) {
        this.randomQ = randomQ;
    }

    public Boolean isRandomSubQ() {
        return randomSubQ;
    }

    public void setRandomSubQ(Boolean randomSubQ) {
        this.randomSubQ = randomSubQ;
    }

    public Boolean isAllQBank() {
        return allQBank;
    }

    public void setAllQBank(Boolean allQBank) {
        this.allQBank = allQBank;
    }  
    
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }  

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    } 

    public int getInPercentDone() {
        return inPercentDone;
    }

    public void setInPercentDone(int inPercentDone) {
        this.inPercentDone = inPercentDone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    } 

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }
    
}
