package eu.lms.portlet.mainright.pto;

import java.util.List;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Lubomir Lacika
 * PTO for GroupDocumentsDto
 */
public class GroupDocPto {

    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    private boolean visible;
    private List<ItemDocumentPto> itemDocument;

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

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public List<ItemDocumentPto> getItemDocument() {
        return itemDocument;
    }

    public void setItemDocument(List<ItemDocumentPto> itemDocument) {
        this.itemDocument = itemDocument;
    } 
}
