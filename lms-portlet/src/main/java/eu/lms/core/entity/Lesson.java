package eu.lms.core.entity;
 
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
 * Lesson entity. 
 */
@Entity
@Table(name="lesson")
public class Lesson extends BaseEntity<Long> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="course_id")
    private Course courseId;

    @Column(nullable = true)
    private String name;
    
    @Column(nullable = true, length = 3550)
    private String content;
    
    @Column(nullable = true)
    private boolean visible;
    
    @Column(nullable = true)
    private int serialNumber;
     
    @Column(name="study_time")
    private int studyTime;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourseId() {
        return courseId;
    }

    public void setCourseId(Course courseId) {
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
