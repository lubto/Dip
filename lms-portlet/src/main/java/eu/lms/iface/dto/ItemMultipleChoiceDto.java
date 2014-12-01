package eu.lms.iface.dto;

/**
 *
 * @author Lubomir Lacika
 * Data Transfer Object for ItemMUlitpleChoice entity.
 */
public class ItemMultipleChoiceDto extends BaseDto<Long>{
    
    private Long id; 
    private String text; 
    private Boolean answer; 
    private int points;
    private MultipleChoiceDto multipleChoiceDto;

    public ItemMultipleChoiceDto(Long id, String text, Boolean answer, int points, MultipleChoiceDto multipleChoiceDto) {
        this.id = id;
        this.text = text;
        this.answer = answer;
        this.points = points;
        this.multipleChoiceDto = multipleChoiceDto;
    } 

    public ItemMultipleChoiceDto() {
    }

    @Override
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

    public Boolean isAnswer() {
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

    public MultipleChoiceDto getMultipleChoiceDto() {
        return multipleChoiceDto;
    }

    public void setMultipleChoiceDto(MultipleChoiceDto multipleChoiceDto) {
        this.multipleChoiceDto = multipleChoiceDto;
    }
    
}
