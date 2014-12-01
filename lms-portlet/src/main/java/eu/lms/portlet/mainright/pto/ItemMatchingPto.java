package eu.lms.portlet.mainright.pto;

import javax.validation.constraints.NotNull;

/**
 *
 * @author Lubomir Lacika
 * PTO for ItemMatchingDto
 */
public class ItemMatchingPto {
    
    private Long id;
    
    @NotNull
    private String question;
    
    @NotNull
    private String answer;
    
    private int points;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    } 
    
}
