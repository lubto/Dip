package eu.lms.core.serviceImpl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import eu.lms.core.daoImpl.ItemDocumentDaoImpl;
import eu.lms.core.entity.ItemDocument;
import eu.lms.core.mapper.Mapper;
import eu.lms.iface.dto.ItemDocumentDto;
import eu.lms.iface.service.ItemDocumentService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Lubomir Lacika 
 * Service providing access to the data-access-layer.
 */
@Service
@Transactional
public class ItemDocumentServiceImpl implements ItemDocumentService {
    
    @Autowired
    private ItemDocumentDaoImpl itemDocumentDao;

    @Override
    public List<ItemDocumentDto> getAllItems() {
        List<ItemDocument> entities = itemDocumentDao.getAll();
        List<ItemDocumentDto> dtos = new ArrayList<ItemDocumentDto>(entities.size());

        for (ItemDocument entity : entities) {
            dtos.add(Mapper.toDto(entity));
        }
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public void save(ItemDocumentDto itemDocumentDto) {
        Validate.notNull(itemDocumentDto);
        Validate.notNull(itemDocumentDto.getId());

        ItemDocument entity = itemDocumentDao.getById(itemDocumentDto.getId());
        itemDocumentDao.update(Mapper.toEntity(entity, itemDocumentDto));
    }

    @Override
    public void create(ItemDocumentDto itemDocumentDto) {
        Validate.notNull(itemDocumentDto);
        Validate.notNull(itemDocumentDto.getId() == null);

        itemDocumentDao.create(Mapper.toEntity(null, itemDocumentDto));
    }

    @Override
    @Transactional(readOnly = true)
    public ItemDocumentDto getById(Long id) {
        Validate.notNull(id);

        ItemDocument entity = itemDocumentDao.getById(id);
        return Mapper.toDto(entity);
    }

    @Override
    public ItemDocumentDto deleteById(Long id) {
        Validate.notNull(id);

        ItemDocument entity = itemDocumentDao.getById(id);
        itemDocumentDao.delete(entity);

        return Mapper.toDto(entity);
    }

    @Override
    public ItemDocumentDto createO(ItemDocumentDto itemDocumentDto) {
        Validate.notNull(itemDocumentDto);
        Validate.isTrue(itemDocumentDto.getId() == null);

        ItemDocument savedEntity = itemDocumentDao.createO(Mapper.toEntity(null, itemDocumentDto));

        return Mapper.toDto(savedEntity);
    }

    @Override
    public DLFolder createFolder(String name, DLFolder folder, long parentFolderId, ThemeDisplay themeDisplay, long userId, Long companyId) {
        long repoId = themeDisplay.getScopeGroupId();
        long timeNow = System.currentTimeMillis();
        Date date = new Date(timeNow);
        if (folder == null) {
            try {
                long id = CounterLocalServiceUtil.increment();
                folder = DLFolderLocalServiceUtil.createDLFolder(id);
                folder.setName(name);
                folder.setParentFolderId(parentFolderId);
                folder.setDescription("This is folder for files from Learning Management System");
                folder.setGroupId(repoId);
                folder.setRepositoryId(repoId);
                folder.setCompanyId(companyId);
                folder.setUserId(userId);
                folder.setCreateDate(date);
                folder.setModifiedDate(date);
                folder.setLastPostDate(date);
                DLFolderLocalServiceUtil.addDLFolder(folder);
                
            } catch (SystemException ex) {
                Logger.getLogger(ItemDocumentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return folder;
    }

}
