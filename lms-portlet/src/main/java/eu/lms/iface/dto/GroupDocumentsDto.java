package eu.lms.iface.dto;

import java.util.List;

/**
 *
 * @author Lubomir Lacika Data Transfer Object for GroupDocuments entity.
 */
public class GroupDocumentsDto extends BaseDto<Long> implements Comparable<GroupDocumentsDto> {

    private Long id;
    private String name;
    private String description;
    private List<ItemDocumentDto> itemDocumentDto;
    private CourseDto course;
    private boolean visible;
    private int sizeItemDocument;
    private int serialNumber;

    public GroupDocumentsDto(Long id, String name, String description, List<ItemDocumentDto> itemDocumentDto, CourseDto course, boolean visible, int sizeItemDocument, int serialNumber) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.itemDocumentDto = itemDocumentDto;
        this.course = course;
        this.visible = visible;
        this.sizeItemDocument = sizeItemDocument;
        this.serialNumber = serialNumber;
    }

    public GroupDocumentsDto() {
    }

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

    public List<ItemDocumentDto> getItemDocumentDto() {
        return itemDocumentDto;
    }

    public void setItemDocumentDto(List<ItemDocumentDto> itemDocumentDto) {
        this.itemDocumentDto = itemDocumentDto;
    }

    public CourseDto getCourse() {
        return course;
    }

    public void setCourse(CourseDto course) {
        this.course = course;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getSizeItemDocument() {
        return sizeItemDocument;
    }

    public void setSizeItemDocument(int sizeItemDocument) {
        this.sizeItemDocument = sizeItemDocument;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public int compareTo(GroupDocumentsDto o) {
        int serialNumber = ((GroupDocumentsDto) o).getSerialNumber();
        return this.serialNumber - serialNumber;
    }

}
