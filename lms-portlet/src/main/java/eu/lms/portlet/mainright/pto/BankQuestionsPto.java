package eu.lms.portlet.mainright.pto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Lubomir Lacika
 * PTO for BankQuestionsDto
 */
public class BankQuestionsPto {

    private Long id;
    @NotEmpty
    private String name;
 
    private String description;
    private Boolean completed;   
    private int numberAllQ;
    private int numberQForTest;
    private Boolean randomQ;
    private Boolean randomSubQ;
    private Boolean allQBank;  
    private int inPercentDone;
    
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

    public Boolean getRandomQ() {
        return randomQ;
    }

    public void setRandomQ(Boolean randomQ) {
        this.randomQ = randomQ;
    }

    public Boolean getRandomSubQ() {
        return randomSubQ;
    }

    public void setRandomSubQ(Boolean randomSubQ) {
        this.randomSubQ = randomSubQ;
    }

    public Boolean getAllQBank() {
        return allQBank;
    }

    public void setAllQBank(Boolean allQBank) {
        this.allQBank = allQBank;
    }
  
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    } 

    public int getInPercentDone() {
        return inPercentDone;
    }

    public void setInPercentDone(int inPercentDone) {
        this.inPercentDone = inPercentDone;
    }
     
}
