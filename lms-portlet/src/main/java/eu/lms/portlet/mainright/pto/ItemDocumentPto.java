package eu.lms.portlet.mainright.pto;

/**
 *
 * @author Lubomir Lacika
 * PTO for ItemDocumentsDto
 */
public class ItemDocumentPto {
    
    private Long id;
    private String name;
    private String link;
    private boolean visible;

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    
}
