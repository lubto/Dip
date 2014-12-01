package eu.lms.iface.dto;

/**
 *
 * @author Lubomir Lacika
 * Data Transfer Object for Lesson entity.
 */
public class LessonDto extends BaseDto<Long> implements Comparable<LessonDto> {

    private Long id;
    private CourseDto courseDto;
    private String name;
    private String content;
    private boolean visible;
    private int studyTime;
    private int serialNumber;

    public LessonDto(Long id, CourseDto courseDto, String name, String content, int studyTime, boolean visible, int serialNumber) {
        this.id = id;
        this.courseDto = courseDto;
        this.name = name;
        this.content = content;
        this.studyTime = studyTime;
        this.visible = visible;
        this.serialNumber = serialNumber;
    }

    public LessonDto() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CourseDto getCourseDto() {
        return courseDto;
    }

    public void setCourseDto(CourseDto courseDto) {
        this.courseDto = courseDto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getStudyTime() {
        return studyTime;
    }

    public void setStudyTime(int studyTime) {
        this.studyTime = studyTime;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public int compareTo(LessonDto o) {
        int serialNumber = ((LessonDto) o).getSerialNumber();

        return this.serialNumber - serialNumber;
    }

}
