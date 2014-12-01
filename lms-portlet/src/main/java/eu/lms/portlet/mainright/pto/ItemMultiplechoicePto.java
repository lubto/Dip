package eu.lms.portlet.mainright.pto;

import javax.validation.constraints.NotNull; 

/**
 *
 * @author Lubomir Lacika
 * PTO for ItemMultipleChoiceDto
 */
public class ItemMultiplechoicePto {

    private Long id;
     
    @NotNull
    private String text;

    private Boolean answer;

    private int points;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
      
}
