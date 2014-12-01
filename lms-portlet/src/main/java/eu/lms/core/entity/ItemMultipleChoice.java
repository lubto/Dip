package eu.lms.core.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Lubomir Lacika
 * ItemMultipleChoice entity. 
 */
@Entity
@Table(name="item_multiplechoice")
public class ItemMultipleChoice extends BaseEntity<Long> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private MultipleChoice multipleChoice;
    
    @Column(nullable = true, length = 300)
    private String text;
    
    @Column(nullable = true)
    private Boolean rightAnswer;
    //vymayat body
    private int points;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MultipleChoice getMultipleChoice() {
        return multipleChoice;
    }

    public void setMultipleChoice(MultipleChoice multipleChoice) {
        this.multipleChoice = multipleChoice;
    } 

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean isRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(Boolean rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    } 
    
}
