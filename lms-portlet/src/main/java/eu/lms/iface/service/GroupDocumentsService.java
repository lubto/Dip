package eu.lms.iface.service;

import com.liferay.portlet.documentlibrary.model.DLFolder;
import eu.lms.iface.dto.GroupDocumentsDto;
import eu.lms.iface.dto.ItemDocumentDto;
import java.util.List;

/**
 * Service providing access to the data-access-layer.
 *
 * @author Lubomir Lacika
 *
 */
public interface GroupDocumentsService {

   /**
     * This method is used to get all groups of documents
     *
     * @return List This returns the list of groups documents.
     */ 
    List<GroupDocumentsDto> getAllItems();

    /**
     * This method is used to save entity.
     *
     * @param groupDocumentsDto This parameter is entity which we want to save/update.
     */ 
    void save(GroupDocumentsDto groupDocumentsDto);

     /**
     * This method is used to create entity.
     *
     * @param groupDocumentsDto This parameter is entity which we want to create.
     */
    void create(GroupDocumentsDto groupDocumentsDto);

    /**
     * This method is used to get group of documents by ID.
     *
     * @param id This parameter is ID of group of documents.
     * @return GroupDocumentsDto This returns the group of documents.
     */
    GroupDocumentsDto getById(Long id);

     /**
     * This method is used to delete entity by ID.
     *
     * @param id This parameter is ID of entity which we want to delete.
     * @return GroupDocumentsDto This returns the entity.
     */
    GroupDocumentsDto deleteById(Long id);

      /**
     * This method is used to create entity.
     *
     * @param groupDocumentsDto This parameter is entity which we want to create.
     * @return GroupDocumentsDto This returns the new created group of documents.
     */
    GroupDocumentsDto createO(GroupDocumentsDto groupDocumentsDto);
 
      /**
     * This method is used to add item of document.
     *
     * @param itemDto This parameter is item of documents.
     * @param id This parameter is ID of group of documents.
     */
    void addItemDoc(ItemDocumentDto itemDto, Long id);

    /**
     * This method is used to remove item´s document from group of documents.
     *
     * @param itemDto This parameter is item documents.
     * @param id This parameter is ID´s group of documents.
     */
    void removeItemDoc(ItemDocumentDto itemDto, Long id);

     /**
     * This method is used to add icon to document item.
     *
     * @param listItemDoc This parameter is list of document item. 
     * @return List This returns the list of document item with new icon.
     */ 
    List<ItemDocumentDto> addIconToItems(List<ItemDocumentDto> listItemDoc);

        /**
     * This method is used to change visible and invisible group of documents in the portal.
     *
     * @param item This parameter is boolean value, if it is true, group of documents is
     * visible in the portal. If the value is false, group of documents is invisible in the
     * portal.
     * @param id This parameter is ID´s group of documents.
     */
    void changeActivate(Boolean item, Long id);

       /**
     * This method is used to set size of group documents.
     * 
     * @param groupDocumentsDto This parameter is list of group of documents.
     * @return List This returns the list of group of documents where param itemDocuemnts have new value.
     * 
     */
    List<GroupDocumentsDto> setSizeItemDocuments(List<GroupDocumentsDto> groupDocumentsDto);

     /**
     * This method is used to search in table of group of documents.
     *
     * @param searchWord This parameter is word which we want to search.
     * @param list This parameter is list of group of documents.
     * @return List This returns the list of group of documents where the word is being
     * searched.
     */
    List<GroupDocumentsDto> searchInList(String searchWord, List<GroupDocumentsDto> list);

       /**
     * This method is used to change position of bank of tests
     *
     * @param upOrDown This parameter is boolean value, if it is true, value of test
     * is incremented by one. If the value is false, value of test is reduced by one.
     * @param groupDocuments This parameter is group of documents which we want to change position.
     */
    void changePosition(boolean upOrDown, GroupDocumentsDto groupDocuments);

}
