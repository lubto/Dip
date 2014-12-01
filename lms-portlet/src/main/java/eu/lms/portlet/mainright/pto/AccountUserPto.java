package eu.lms.portlet.mainright.pto;

import java.util.Date;

/**
 *
 * @author Lubomir Lacika
 * PTO for AccountUserDto
 */
public class AccountUserPto {
    
    private Long id; 
    private String userId; 
    private Date dateCreation;  
    private Boolean activate;

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

    public Boolean isActivate() {
        return activate;
    }

    public void setActivate(Boolean activate) {
        this.activate = activate;
    }  
}
