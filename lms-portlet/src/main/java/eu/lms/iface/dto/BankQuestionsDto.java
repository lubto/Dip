package eu.lms.iface.dto;

import java.util.List;

/**
 *
 * @author Lubomir Lacika Data Transfer Object for BankQuestions entity.
 */
public class BankQuestionsDto extends BaseDto<Long> implements Comparable<BankQuestionsDto> {

    private Long id;
    private String name;
    private String description;
    private List<MultipleChoiceDto> multipleChoiceDto;
    private int numberAllQ;
    private int numberQForTest;
    private Boolean randomQ;
    private Boolean randomSubQ;
    private Boolean allQBank;
    private CourseDto course;
    private boolean completed;
    private String nameIcon;
    private int inPercentDone;
    private String idUserBankQ;
    private String userId;
    private int serialNumber;

    public BankQuestionsDto(Long id, String name, String description, List<MultipleChoiceDto> multipleChoiceDto, int numberAllQ, int numberQForTest, Boolean randomQ, Boolean randomSubQ, Boolean allQBank, CourseDto course, boolean completed, String nameIcon, int inPercentDone, String idUserBankQ, String userId, int serialNumber) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.multipleChoiceDto = multipleChoiceDto;
        this.numberAllQ = numberAllQ;
        this.numberQForTest = numberQForTest;
        this.randomQ = randomQ;
        this.randomSubQ = randomSubQ;
        this.allQBank = allQBank;
        this.course = course;
        this.completed = completed;
        this.nameIcon = nameIcon;
        this.inPercentDone = inPercentDone;
        this.idUserBankQ = idUserBankQ;
        this.userId = userId;
        this.serialNumber = serialNumber;
    }

    public BankQuestionsDto() {
    }

    @Override
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

    public List<MultipleChoiceDto> getMultipleChoiceDto() {
        return multipleChoiceDto;
    }

    public void setMultipleChoiceDto(List<MultipleChoiceDto> multipleChoiceDto) {
        this.multipleChoiceDto = multipleChoiceDto;
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

    public Boolean isRandomQ() {
        return randomQ;
    }

    public void setRandomQ(Boolean randomQ) {
        this.randomQ = randomQ;
    }

    public Boolean isRandomSubQ() {
        return randomSubQ;
    }

    public void setRandomSubQ(Boolean randomSubQ) {
        this.randomSubQ = randomSubQ;
    }

    public Boolean isAllQBank() {
        return allQBank;
    }

    public void setAllQBank(Boolean allQBank) {
        this.allQBank = allQBank;
    }

    public CourseDto getCourse() {
        return course;
    }

    public void setCourse(CourseDto course) {
        this.course = course;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getNameIcon() {
        return nameIcon;
    }

    public void setNameIcon(String nameIcon) {
        this.nameIcon = nameIcon;
    }

    public int getInPercentDone() {
        return inPercentDone;
    }

    public void setInPercentDone(int inPercentDone) {
        this.inPercentDone = inPercentDone;
    }

    public String getIdUserBankQ() {
        return idUserBankQ;
    }

    public void setIdUserBankQ(String idUserBankQ) {
        this.idUserBankQ = idUserBankQ;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int compareTo(BankQuestionsDto o) {
        int serialNumber = ((BankQuestionsDto) o).getSerialNumber();

        return this.serialNumber - serialNumber;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }
    
}
