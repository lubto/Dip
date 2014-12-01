package eu.lms.portlet.mainright.pto;
 
import org.hibernate.validator.constraints.NotEmpty; 
/**
 *
 * @author Lubomir Lacika
 * PTO for Email
 */
public class EmailPto {

    private String userId;
    
    private String to;
    
    private String from;
    
    @NotEmpty
    private String subject;
     
     
    private String message;
    
    private String date;
 
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
     
}
