/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.lms.core.entity;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Lubomir Lacika
 * UserBankQ entity. 
 */
@Entity
@Table(name = "user_bankq")
public class UserBankQ extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean done;

    private int points;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_item")
    private CourseItem courseItem;

    @OneToMany(mappedBy = "userBankQ", cascade = CascadeType.ALL) 
    private List<ItemAttempts> itemAttempts;

    @Column(nullable = false)
    private String bankQuestions;

    private int numberOfAttempts;
    
   @Column(nullable = true)
    private int serialNumber;
           

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public CourseItem getCourseItem() {
        return courseItem;
    }

    public void setCourseItem(CourseItem courseItem) {
        this.courseItem = courseItem;
    }

    public String getBankQuestions() {
        return bankQuestions;
    }

    public void setBankQuestions(String bankQuestions) {
        this.bankQuestions = bankQuestions;
    }

    public int getNumberOfAttempts() {
        return numberOfAttempts;
    }

    public void setNumberOfAttempts(int numberOfAttempts) {
        this.numberOfAttempts = numberOfAttempts;
    }

    public List<ItemAttempts> getItemAttempts() {
        return itemAttempts;
    }

    public void setItemAttempts(List<ItemAttempts> itemAttempts) {
        this.itemAttempts = itemAttempts;
    } 

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }
    
}
