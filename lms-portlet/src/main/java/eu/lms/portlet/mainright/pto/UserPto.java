package eu.lms.portlet.mainright.pto;

/**
 *
 * @author Lubomir Lacika
 * PTO for UserPto
 */

public class UserPto {
    private long id;
    private String fullName;
    private String firstName;
    private String lastName;
    private String gender;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    } 
}
