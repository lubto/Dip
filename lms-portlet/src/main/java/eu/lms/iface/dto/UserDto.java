package eu.lms.iface.dto;

/**
 *
 * @author Lubomir Lacika
 * Data Transfer Object for help in service layer.
 */
public class UserDto extends BaseDto<Long>{
    
    private Long id;
    private String fullName;
    private String email;
    

    public UserDto(Long id, String name, String email) {
        this.id = id;
        this.fullName = name;
        this.email = email;
    }
    
    public UserDto(){
    
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    } 
    
}
