package eu.lms.core.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Lubomir Lacika
 * AccountUser entity.
 */
@Entity
@Table(name = "account_user")
public class AccountUser extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "date_creation")
    private Date dateCreation;

    @OneToMany(mappedBy = "accountUser", cascade = CascadeType.ALL)
    private List<CourseItem> listCourseItems;
 
    private Boolean activate;
    //list pokusov daneho testu.
    //private List<> 
    
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

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public List<CourseItem> getListCourseItems() {
        return listCourseItems;
    }

    public void setListCourses(List<CourseItem> listCourseItems) {
        this.listCourseItems = listCourseItems;
    }

    public Boolean isActivate() {
        return activate;
    }

    public void setActivate(Boolean activate) {
        this.activate = activate;
    } 
     
}
