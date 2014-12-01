package eu.lms.portlet.mainright.pto;

import java.util.List;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Lubomir Lacika
 * PTO for MatchingDto
 */
public class MatchingPto {
    
    private Long id;
    
    @NotNull
    private String Question;
    
    private List<ItemMatchingPto> items;
    
    private int points;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String Question) {
        this.Question = Question;
    }

    public List<ItemMatchingPto> getItems() {
        return items;
    }

    public void setItems(List<ItemMatchingPto> items) {
        this.items = items;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    } 
    
}
