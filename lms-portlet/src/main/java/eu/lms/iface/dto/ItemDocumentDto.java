package eu.lms.iface.dto;

/**
 *
 * @author Lubomir Lacika
 * Data Transfer Object for ItemDocument entity.
 */
public class ItemDocumentDto extends BaseDto<Long>{
    
    private Long id;
    private String link;
    private String name;
    private boolean visible;
    private GroupDocumentsDto groupDocuments;
    private String nameIcon;
    private boolean isLink;

    public ItemDocumentDto(Long id, String link, String name, boolean visible, GroupDocumentsDto groupDocuments, String nameIcon, boolean isLink) {
        this.id = id;
        this.link = link;
        this.name = name;
        this.visible = visible;
        this.groupDocuments = groupDocuments;
        this.nameIcon = nameIcon;
        this.isLink = isLink;
    } 

    public ItemDocumentDto() {
    }

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

    public GroupDocumentsDto getGroupDocuments() {
        return groupDocuments;
    }

    public void setGroupDocuments(GroupDocumentsDto groupDocuments) {
        this.groupDocuments = groupDocuments;
    }  

    public String getNameIcon() {
        return nameIcon;
    }

    public void setNameIcon(String nameIcon) {
        this.nameIcon = nameIcon;
    } 

    public boolean isIsLink() {
        return isLink;
    }

    public void setIsLink(boolean isLink) {
        this.isLink = isLink;
    }
    
}
