package eu.lms.portlet.mainright.pto;

import java.util.List;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Lubomir Lacika
 * PTO for MultipleCHoiceDto
 * 
 */
public class MultipleChoicePto {
    
    private Long id;
    
    private List<ItemMultiplechoicePto> itemsM;
    
    @NotEmpty
    private String question;
    
    private int points; 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ItemMultiplechoicePto> getItemsM() {
        return itemsM;
    }

    public void setItemsM(List<ItemMultiplechoicePto> itemsM) {
        this.itemsM = itemsM;
    } 

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }  
}
