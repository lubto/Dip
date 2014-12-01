package eu.lms.iface.dto;

import java.util.List;

/**
 *
 * @author Lubomir Lacika 
 * Data Transfer Object for MultipleChoice entity.
 */
public class MultipleChoiceDto extends BaseDto<Long> {

    private Long id;
    private List<ItemMultipleChoiceDto> items;
    private String question;
    private int points;
    private BankQuestionsDto bankQuestions;
    private String userId;
    private String questionShort;

    public MultipleChoiceDto(Long id, List<ItemMultipleChoiceDto> items, String question, int points, BankQuestionsDto bankQuestions, String userId, String questionShort) {
        this.id = id;
        this.items = items;
        this.question = question;
        this.points = points;
        this.bankQuestions = bankQuestions;
        this.userId= userId;
        this.questionShort = questionShort;
    }

    public MultipleChoiceDto() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ItemMultipleChoiceDto> getItems() {
        return items;
    }

    public void setItems(List<ItemMultipleChoiceDto> items) {
        this.items = items;
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

    public BankQuestionsDto getBankQuestions() {
        return bankQuestions;
    }

    public void setBankQuestions(BankQuestionsDto bankQuestions) {
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
