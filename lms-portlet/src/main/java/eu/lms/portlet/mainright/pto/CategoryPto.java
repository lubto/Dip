package eu.lms.portlet.mainright.pto;
        
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Lubomir Lacika
 * PTO for CategoryDto
 */
public class CategoryPto {

    private Long id;

    @NotEmpty
    private String name;
   
    @NotEmpty
    @Length(min = 5, max= 499)
    private String description;
    
    private int numberCourses;
    
    private boolean visible;
    
    private boolean FreeCourse;

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

    public int getNumberCourses() {
        return numberCourses;
    }

    public void setNumberCourses(int numberCourses) {
        this.numberCourses = numberCourses;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean getFreeCourse() {
        return FreeCourse;
    }

    public void setFreeCourse(boolean FreeCourse) {
        this.FreeCourse = FreeCourse;
    }
 
}
