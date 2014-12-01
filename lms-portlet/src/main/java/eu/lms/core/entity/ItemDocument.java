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
 * ItemDocument entity. 
 */
@Entity
@Table(name = "item_document")
public class ItemDocument extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String link;

    private String name;

    private boolean visible; 
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private GroupDocuments groupDocuments;
    
    private boolean isLink;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public GroupDocuments getGroupDocuments() {
        return groupDocuments;
    }

    public void setGroupDocuments(GroupDocuments groupDocuments) {
        this.groupDocuments = groupDocuments;
    } 

    public boolean isIsLink() {
        return isLink;
    }

    public void setIsLink(boolean isLink) {
        this.isLink = isLink;
    }
    
}
