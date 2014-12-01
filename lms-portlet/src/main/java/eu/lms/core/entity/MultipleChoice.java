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
 * @author Lubomir Lacika MultipleChoice entity.
 */
@Entity
@Table(name = "multiple_choice")
public class MultipleChoice extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "multipleChoice", cascade = CascadeType.ALL)
    private List<ItemMultipleChoice> itemMultiplechoice;

    @Column(nullable = true, length = 500)
    private String question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private BankQuestions bankQuestions;

    private int point;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    String questionShort;
    
    @Override

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ItemMultipleChoice> getItemMultiplechoice() {
        return itemMultiplechoice;
    }

    public void setItemMultiplechoice(List<ItemMultipleChoice> itemMultiplechoice) {
        this.itemMultiplechoice = itemMultiplechoice;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public BankQuestions getBankQuestions() {
        return bankQuestions;
    }

    public void setBankQuestions(BankQuestions bankQuestions) {
        this.bankQuestions = bankQuestions;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestionShort() {
        return questionShort;
    }

    public void setQuestionShort(String questionShort) {
        this.questionShort = questionShort;
    }
    
}
