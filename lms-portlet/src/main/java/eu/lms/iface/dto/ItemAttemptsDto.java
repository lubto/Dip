package eu.lms.iface.dto;

/**
 *
 * @author Lubomir Lacika
 * Data Transfer Object for ItemAttempts entity.
 */
public class ItemAttemptsDto extends BaseDto<Long> {

    private Long id; 
    private UserBankQDto userBankQ; 
    private int points;

    public ItemAttemptsDto(Long id, UserBankQDto userBankQ, int points) {
        this.id = id;
        this.userBankQ = userBankQ;
        this.points = points;
    }

    public ItemAttemptsDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserBankQDto getUserBankQ() {
        return userBankQ;
    }

    public void setUserBankQ(UserBankQDto userBankQ) {
        this.userBankQ = userBankQ;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
     
}
