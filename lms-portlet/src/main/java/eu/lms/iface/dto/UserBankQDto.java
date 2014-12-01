package eu.lms.iface.dto;

import java.util.List;

/**
 *
 * @author Lubomir Lacika 
 * Data Transfer Object for UserBankQ entity.
 */
public class UserBankQDto extends BaseDto<Long> implements Comparable<UserBankQDto> {

    private Long id;
    private boolean done;
    private int points;
    private CourseItemDto courseItem;
    private String bankQuestions;
    private List<ItemAttemptsDto> listAttempts;
    private int serialNumber;

    public UserBankQDto(Long id, boolean done, int points, CourseItemDto courseItem, String bankQuestions, List<ItemAttemptsDto> listAttempts, int serialNumber) {
        this.id = id;
        this.done = done;
        this.points = points;
        this.courseItem = courseItem;
        this.bankQuestions = bankQuestions;
        this.listAttempts = listAttempts;
        this.serialNumber = serialNumber;
    }

    public UserBankQDto() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public CourseItemDto getCourseItem() {
        return courseItem;
    }

    public void setCourseItem(CourseItemDto courseItem) {
        this.courseItem = courseItem;
    }

    public String getBankQuestions() {
        return bankQuestions;
    }

    public void setBankQuestions(String bankQuestions) {
        this.bankQuestions = bankQuestions;
    }

    public List<ItemAttemptsDto> getListAttempts() {
        return listAttempts;
    }

    public void setListAttempts(List<ItemAttemptsDto> listAttempts) {
        this.listAttempts = listAttempts;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public int compareTo(UserBankQDto o) {
        int serialNumber = ((UserBankQDto) o).getSerialNumber();

        return this.serialNumber - serialNumber;
    }

}
