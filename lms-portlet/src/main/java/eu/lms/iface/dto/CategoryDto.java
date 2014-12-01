package eu.lms.iface.dto;

/**
 *
 * @author Lubomir Lacika
 * Data Transfer Object for Category entity.
 */
public class CategoryDto extends BaseDto<Long> implements Comparable<CategoryDto>{

    private Long id;
    private String name;
    private String description;
    private boolean visible;
    private int numberCourses;
    private boolean freeCourse;

    public CategoryDto(Long id, String name, String description, boolean visible, int numberCourses, boolean freeCourse) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.visible = visible;
        this.numberCourses = numberCourses;
        this.freeCourse = freeCourse;
    }
 
    public CategoryDto() {
    }

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

    @Override
    public int compareTo(CategoryDto o) {
        return (this.name).compareTo(o.name);
    }
 
 
}
