package eu.lms.portlet.mainright.pto;

import java.util.Date;

/**
 *
 * @author Lubomir Lacika
 * PTO for CourseItemDto
 */
public class CourseItemPto {
    
    private Long id;
    private String userId;
    private String courseId; 
    private boolean finishedCourse;
    private boolean visitedCourse;
    private Date startCourse;
    private Date endCourse;
    private boolean visible;
    private String nameCourse;
    private boolean timesUp;
    private String fullName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public boolean getFinishedCourse() {
        return finishedCourse;
    }

    public void setFinishedCourse(boolean finishedCourse) {
        this.finishedCourse = finishedCourse;
    }

    public boolean getVisitedCourse() {
        return visitedCourse;
    }

    public void setVisitedCourse(boolean visitedCourse) {
        this.visitedCourse = visitedCourse;
    }

    public Date getStartCourse() {
        return startCourse;
    }

    public void setStartCourse(Date startCourse) {
        this.startCourse = startCourse;
    }

    public Date getEndCourse() {
        return endCourse;
    }

    public void setEndCourse(Date endCourse) {
        this.endCourse = endCourse;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public boolean getTimesUp() {
        return timesUp;
    }

    public void setTimesUp(boolean timesUp) {
        this.timesUp = timesUp;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    
}
