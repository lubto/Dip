 package eu.lms.portlet.mainright.pto;
 
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
/**
 *
 * @author Lubomir Lacika
 * PTO for LessonDto
 */
public class LessonPto {

    private Long id;
    
    private Long courseId;
 
    @NotEmpty
    private String name;
      
    private String content;
    
    private boolean visible; 
    
    private int studyTime;
    
    @NotNull
    private int serialNumber;

    //quiz list
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
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
}
