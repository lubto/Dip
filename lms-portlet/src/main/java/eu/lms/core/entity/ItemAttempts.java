package eu.lms.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Lubomir Lacika
 * ItemAttempts entity. 
 */
@Entity
@Table(name = "item_attempts")
public class ItemAttempts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_bankQ")
    private UserBankQ userBankQ;
//nemusi byt
//    @Column(nullable = false)
//    private String bankQuestions;

    @Column(nullable = false)
    private int points;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserBankQ getUserBankQ() {
        return userBankQ;
    }

    public void setUserBankQ(UserBankQ userBankQ) {
        this.userBankQ = userBankQ;
    }

//    public String getBankQuestions() {
//        return bankQuestions;
//    }
//
//    public void setBankQuestions(String bankQuestions) {
//        this.bankQuestions = bankQuestions;
//    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
