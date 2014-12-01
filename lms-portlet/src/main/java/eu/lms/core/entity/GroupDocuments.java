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
import javax.persistence.Table;

/**
 *
 * @author Lubomir Lacika
 * GroupDocuments entity.
 */
@Entity
@Table(name = "group_documents")
public class GroupDocuments extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(nullable = true, length = 3000)
    private String description;

    @OneToMany(mappedBy = "groupDocuments", cascade = CascadeType.ALL)
    private List<ItemDocument> itemDocument;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Course course;

    private boolean visible;
    
    @Column(nullable = false)
    private int serialNumber;

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

    public List<ItemDocument> getItemDocument() {
        return itemDocument;
    }

    public void setItemDocument(List<ItemDocument> itemDocument) {
        this.itemDocument = itemDocument;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }
    
}
