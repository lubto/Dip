package eu.lms.iface.dto;

/**
 *
 * @author Lubomir Lacika
 * Data Transfer Object for help in service layer.
 */
public class HelperUserBankDto {

    private String name;
    private String description;
    private int points;
    private int attempts;

    public HelperUserBankDto(String name, String description, int points, int attempts) {
        this.name = name;
        this.description = description;
        this.points = points;
        this.attempts = attempts;
    }

    public HelperUserBankDto() {
    }
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }
    
    

}
