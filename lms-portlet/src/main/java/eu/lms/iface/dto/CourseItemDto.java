package eu.lms.iface.dto;
 
import java.util.Date;
import java.util.List;

/**
 *
 * @author Lubomir Lacika
 * Data Transfer Object for CourseItem entity.
 */
public class CourseItemDto extends BaseDto<Long> implements Comparable<CourseItemDto>{

    private Long id;
    private String userId;
    private String courseId;
    private AccountUserDto accountUser;
    private boolean finishedCourse;
    private boolean visitedCourse;
    private Date startCourse;
    private Date endCourse;
    private boolean visible;
    private String nameCourse;
    private boolean timesUp;
    private String fullName;
    private List<UserBankQDto> userBankQ; 
    private boolean visibleDocuments; 
    private boolean visibleQuestions; 
    private boolean visibleLessons;

    public CourseItemDto() {
    }

    public CourseItemDto(Long id, String userId, String courseId, AccountUserDto accountUser, boolean finishedCourse, boolean visitedCourse, Date startCourse, Date endCourse, boolean visible, String nameCourse, boolean timesUp, String fullName, List<UserBankQDto> userBankQ, boolean visibleDocuments,boolean visibleQuestions, boolean visibleLessons) {
        this.id = id;
        this.userId = userId;
        this.courseId = courseId;
        this.accountUser = accountUser;
        this.finishedCourse = finishedCourse;
        this.visitedCourse = visitedCourse;
        this.startCourse = startCourse;
        this.endCourse = endCourse;
        this.visible = visible;
        this.nameCourse = nameCourse;
        this.timesUp = timesUp;
        this.fullName = fullName;
        this.userBankQ = userBankQ;
          this.visibleDocuments = visibleDocuments;
        this.visibleLessons = visibleLessons;
        this.visibleQuestions = visibleQuestions;
    } 
  
    @Override
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
 
    public AccountUserDto getAccountUser() {
        return accountUser;
    }

    public void setAccountUser(AccountUserDto accountUser) {
        this.accountUser = accountUser;
    }

    public boolean isFinishedCourse() {
        return finishedCourse;
    }

    public void setFinishedCourse(boolean finishedCourse) {
        this.finishedCourse = finishedCourse;
    }

    public boolean isVisitedCourse() {
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

    public List<UserBankQDto> getUserBankQ() {
        return userBankQ;
    }

    public void setUserBankQ(List<UserBankQDto> userBankQ) {
        this.userBankQ = userBankQ;
    }  

    @Override
    public int compareTo(CourseItemDto o) {
       return (this.nameCourse).compareTo(o.nameCourse);
    }

    public boolean isVisibleDocuments() {
        return visibleDocuments;
    }

    public void setVisibleDocuments(boolean visibleDocuments) {
        this.visibleDocuments = visibleDocuments;
    }

    public boolean isVisibleQuestions() {
        return visibleQuestions;
    }

    public void setVisibleQuestions(boolean visibleQuestions) {
        this.visibleQuestions = visibleQuestions;
    }

    public boolean isVisibleLessons() {
        return visibleLessons;
    }

    public void setVisibleLessons(boolean visibleLessons) {
        this.visibleLessons = visibleLessons;
    }
     
}
