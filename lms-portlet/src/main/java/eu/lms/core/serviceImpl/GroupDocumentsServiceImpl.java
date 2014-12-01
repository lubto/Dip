package eu.lms.core.serviceImpl;

import eu.lms.core.daoImpl.CourseDaoImpl;
import eu.lms.core.daoImpl.GroupDocumentsDaoImpl;
import eu.lms.core.daoImpl.ItemDocumentDaoImpl;
import eu.lms.core.entity.Course;
import eu.lms.core.entity.GroupDocuments;
import eu.lms.core.entity.ItemDocument;
import eu.lms.core.mapper.Mapper;
import eu.lms.iface.dto.GroupDocumentsDto;
import eu.lms.iface.dto.ItemDocumentDto;
import eu.lms.iface.service.GroupDocumentsService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service providing access to the data-access-layer.
 *
 * @author Lubomir Lacika
 *
 */
@Service
@Transactional
public class GroupDocumentsServiceImpl implements GroupDocumentsService {

    protected final Logger LOG = Logger.getLogger(GroupDocumentsServiceImpl.class);
    @Autowired
    private GroupDocumentsDaoImpl groupDocumentsDao;

    @Autowired
    private ItemDocumentDaoImpl itemDocumentDao;

    @Autowired
    private CourseDaoImpl courseDao;

     /**
     * This method is used to get all groups of documents
     *
     * @return List This returns the list of groups documents.
     */
    @Override
    public List<GroupDocumentsDto> getAllItems() {
        List<GroupDocuments> entities = groupDocumentsDao.getAll();
        List<GroupDocumentsDto> dtos = new ArrayList<GroupDocumentsDto>(entities.size());

        for (GroupDocuments entity : entities) {
            dtos.add(Mapper.toDto(entity));
        }
        return dtos;
    }

    /**
     * This method is used to save entity.
     *
     * @param groupDocumentsDto This parameter is entity which we want to save/update.
     */ 
    @Override
    public void save(GroupDocumentsDto groupDocumentsDto) {
        Validate.notNull(groupDocumentsDto);
        Validate.notNull(groupDocumentsDto.getId());

        GroupDocuments entity = groupDocumentsDao.getById(groupDocumentsDto.getId());
        groupDocumentsDao.update(Mapper.toEntity(entity, groupDocumentsDto));
    }

    /**
     * This method is used to create entity.
     *
     * @param groupDocumentsDto This parameter is entity which we want to create.
     */
    @Override
    public void create(GroupDocumentsDto groupDocumentsDto) {
        Validate.notNull(groupDocumentsDto);
        Validate.notNull(groupDocumentsDto.getId() == null);

        groupDocumentsDao.create(Mapper.toEntity(null, groupDocumentsDto));
    }

    /**
     * This method is used to get group of documents by ID.
     *
     * @param id This parameter is ID of group of documents.
     * @return GroupDocumentsDto This returns the group of documents.
     */
    @Override
    @Transactional(readOnly = true)
    public GroupDocumentsDto getById(Long id) {
        Validate.notNull(id);

        GroupDocuments entity = groupDocumentsDao.getById(id);
        return Mapper.toDto(entity);
    }

     /**
     * This method is used to delete entity by ID.
     *
     * @param id This parameter is ID of entity which we want to delete.
     * @return GroupDocumentsDto This returns the entity.
     */
    @Override
    public GroupDocumentsDto deleteById(Long id) {
        Validate.notNull(id);

        GroupDocuments entity = groupDocumentsDao.getById(id);
        groupDocumentsDao.delete(entity);

        return Mapper.toDto(entity);
    }

    /**
     * This method is used to create entity.
     *
     * @param groupDocumentsDto This parameter is entity which we want to create.
     * @return GroupDocumentsDto This returns the new created group of documents.
     */
    @Override
    public GroupDocumentsDto createO(GroupDocumentsDto groupDocumentsDto) {
        Validate.notNull(groupDocumentsDto);
        Validate.isTrue(groupDocumentsDto.getId() == null);

        GroupDocuments savedEntity = groupDocumentsDao.createO(Mapper.toEntity(null, groupDocumentsDto));

        return Mapper.toDto(savedEntity);
    }

      /**
     * This method is used to add item of document.
     *
     * @param itemDto This parameter is item of documents.
     * @param id This parameter is ID of group of documents.
     */
    @Override
    public void addItemDoc(ItemDocumentDto itemDto, Long id) {
        Validate.notNull(itemDto);
        Validate.notNull(id);

        GroupDocuments entityGroup = new GroupDocuments();
        entityGroup = groupDocumentsDao.getById(id);

        ItemDocument entity = new ItemDocument();
        entity = itemDocumentDao.createO(Mapper.toEntity(null, itemDto));
        entity.setGroupDocuments(entityGroup);

        entityGroup.getItemDocument().add(entity);
        groupDocumentsDao.update(entityGroup);
    }

     /**
     * This method is used to remove item´s document from group of documents.
     *
     * @param itemDto This parameter is item documents.
     * @param id This parameter is ID´s group of documents.
     */
    @Override
    public void removeItemDoc(ItemDocumentDto itemDto, Long id) {
        Validate.notNull(itemDto);
        Validate.notNull(id);

        GroupDocuments entityGroup = new GroupDocuments();
        entityGroup = groupDocumentsDao.getById(id);
        ItemDocument entity = new ItemDocument();
        entity = itemDocumentDao.getById(itemDto.getId());
        entityGroup.getItemDocument().remove(entity);

        groupDocumentsDao.update(entityGroup);

        entity.setGroupDocuments(null);
        itemDocumentDao.delete(entity);
    }

    /**
     * This method is used to add icon to document item.
     *
     * @param listItemDoc This parameter is list of document item. 
     * @return List This returns the list of document item with new icon.
     */ 
    @Override
    public List<ItemDocumentDto> addIconToItems(List<ItemDocumentDto> listItemDoc) {
        if (listItemDoc != null) {
            for (Iterator<ItemDocumentDto> it = listItemDoc.iterator(); it.hasNext();) {
                ItemDocumentDto itemDocumentDto = it.next();
                String text = itemDocumentDto.getLink();
                CharSequence word[] = {".doc", ".html", ".xls", ".pdf", ".jpg", ".rar", ".zip", ".ppt", ".mp3", ".youtube", ".mov"};
                boolean isNone = false;
                for (int i = 0; i < word.length; i++) {
                    if (text.contains(word[i])) {
                        itemDocumentDto.setNameIcon("/images/" + String.valueOf(word[i]).substring(1) + ".png");
                        isNone = true;
                    }
                }
                if (isNone == false) {
                    itemDocumentDto.setNameIcon("/images/none.png");
                }
            }
        }
        return listItemDoc;
    }

      /**
     * This method is used to change visible and invisible group of documents in the portal.
     *
     * @param item This parameter is boolean value, if it is true, group of documents is
     * visible in the portal. If the value is false, group of documents is invisible in the
     * portal.
     * @param id This parameter is ID´s group of documents.
     */
    @Override
    public void changeActivate(Boolean item, Long id) {
        Validate.notNull(id);
        Validate.notNull(item);

        GroupDocuments entity = groupDocumentsDao.getById(id);
        entity.setVisible(item);
    }

      /**
     * This method is used to set size of group documents.
     * 
     * @param groupDoc This parameter is list of group of documents.
     * @return List This returns the list of group of documents where param itemDocuemnts have new value.
     * 
     */
    @Override
    public List<GroupDocumentsDto> setSizeItemDocuments(List<GroupDocumentsDto> groupDoc) {
        for (GroupDocumentsDto groupDocumentsDto : groupDoc) {
            if (groupDocumentsDto.getItemDocumentDto() == null) {
                groupDocumentsDto.setSizeItemDocument(0);
            } else {
                groupDocumentsDto.setSizeItemDocument(groupDocumentsDto.getItemDocumentDto().size());
            }
        }
        return groupDoc;
    }

    /**
     * This method is used to search in table of group of documents.
     *
     * @param searchWord This parameter is word which we want to search.
     * @param list This parameter is list of group of documents.
     * @return List This returns the list of group of documents where the word is being
     * searched.
     */
    @Override
    public List<GroupDocumentsDto> searchInList(String searchWord, List<GroupDocumentsDto> list) {
        String search = searchWord.toLowerCase();
        List<GroupDocumentsDto> listNew = new ArrayList<GroupDocumentsDto>();
        for (Iterator<GroupDocumentsDto> it = list.iterator(); it.hasNext();) {
            GroupDocumentsDto group = it.next();
            String name = group.getName().toLowerCase();
            if (name.contains(search)) {
                listNew.add(group);
            }
        }
        return listNew;
    }

       /**
     * This method is used to change position of bank of tests
     *
     * @param upOrDown This parameter is boolean value, if it is true, value of test
     * is incremented by one. If the value is false, value of test is reduced by one.
     * @param groupDocuments This parameter is group of documents which we want to change position.
     */
    @Override
    public void changePosition(boolean upOrDown, GroupDocumentsDto groupDocuments) {
        GroupDocuments entity = groupDocumentsDao.getById(groupDocuments.getId());
        Course course = courseDao.getById(entity.getCourse().getId());
        int number = entity.getSerialNumber();
        List<GroupDocuments> list = course.getGroupDocuments();
        if (upOrDown == true) {
            if (entity.getSerialNumber() != 1 || entity.getSerialNumber() != 0) {
                for (GroupDocuments doc : list) {
                    if (doc.getSerialNumber() == number - 1) {
                        GroupDocuments l = groupDocumentsDao.getById(doc.getId());
                        l.setSerialNumber(number);
                        entity.setSerialNumber(number - 1);
                        break;
                    }
                }
            }
        } else {
            for (GroupDocuments lesson1 : list) {
                if (lesson1.getSerialNumber() == number + 1) {
                    GroupDocuments l = groupDocumentsDao.getById(lesson1.getId());
                    l.setSerialNumber(number);
                    entity.setSerialNumber(number + 1);
                    break;
                }
            }
        }
    }

}
