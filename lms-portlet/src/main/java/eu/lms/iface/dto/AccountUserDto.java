package eu.lms.iface.dto;
 
import java.util.Date;
import java.util.List;

/**
 *
 * @author Lubomir Lacika
 * Data Transfer Object for AccountUser entity.
 */
public class AccountUserDto extends BaseDto<Long> {

    private Long id;
    private String userId;
    private Date dateCreation;
    private List<CourseItemDto> listCourses;
    private Boolean activate;
    private String userName;

    public AccountUserDto() {
    }

    public AccountUserDto(Long id, String userId, Date dateCreation, List<CourseItemDto> listCourses, Boolean activate, String userName) {
        this.id = id;
        this.userId = userId;
        this.dateCreation = dateCreation;
        this.listCourses = listCourses;
        this.activate = activate;
        this.userName = userName;
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

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public List<CourseItemDto> getListCourses() {
        return listCourses;
    }

    public void setListCourses(List<CourseItemDto> listCourses) {
        this.listCourses = listCourses;
    }

    public void setActivate(Boolean activate) {
        this.activate = activate;
    } 
    
    public Boolean getActivate() {
        return activate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    } 
}
