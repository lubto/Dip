package eu.lms.core.entity;

import java.util.Date;
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
import javax.persistence.Temporal;

/**
 *
 * @author Lubomir Lacika
 * CourseItem entity. 
 */
@Entity
@Table(name = "course_item")
public class CourseItem extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id")
    private String userId;
 
    @Column(name = "id_course")
    private String courseId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_user")
    private AccountUser accountUser;

    @Column(name = "finish_course")
    private boolean finishedCourse;

    @Column(name = "visited_course")
    private boolean visitedCourse;

    @Column(name = "start_course")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date startCourse;

    @Column(name = "end_course")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date endCourse;

    private Boolean visible;
    
    @OneToMany(mappedBy = "courseItem", cascade = CascadeType.ALL)
    private List<UserBankQ> userBankQ; 

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

    public AccountUser getAccountUser() {
        return accountUser;
    }

    public void setAccountUser(AccountUser accountUser) {
        this.accountUser = accountUser;
    }

    public boolean isFinishedCourse() {
        return finishedCourse;
    }

    public void setFinishedCourse(boolean finishedCourse) {
        this.finishedCourse = finishedCourse;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Date getEndCourse() {
        return endCourse;
    }

    public void setEndCourse(Date endCourse) {
        this.endCourse = endCourse;
    }

    public List<UserBankQ> getUserBankQ() {
        return userBankQ;
    }

    public void setUserBankQ(List<UserBankQ> userBankQ) {
        this.userBankQ = userBankQ;
    } 
     
}
