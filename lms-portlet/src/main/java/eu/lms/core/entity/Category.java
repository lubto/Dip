package eu.lms.core.entity;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Lubomir Lacika
 * Category entity.
 */
@Entity
@Table(name="category")
public class Category extends BaseEntity<Long>{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="name", nullable=false)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;
    
    @Column(name="number_courses")
    private int numberCourses;
    
    @Column(name="visible")
    private boolean visible;
    
    private boolean freeCourse;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getNumberCourses() {
        return numberCourses;
    }

    public void setNumberCourses(int numberCourses) {
        this.numberCourses = numberCourses;
    } 

    public boolean isFreeCourse() {
        return freeCourse;
    }

    public void setFreeCourse(boolean freeCourse) {
        this.freeCourse = freeCourse;
    } 
     
}
