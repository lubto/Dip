package eu.lms.iface.service;

import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import eu.lms.iface.dto.ItemDocumentDto;
import java.util.List;

/**
 *
 * @author Lubomir Lacika
 * Service providing access to the data-access-layer.
 */
public interface ItemDocumentService {

    List<ItemDocumentDto> getAllItems();

    void save(ItemDocumentDto itemDocumentDto);

    void create(ItemDocumentDto itemDocumentDto);

    ItemDocumentDto getById(Long id);

    ItemDocumentDto deleteById(Long id);

    ItemDocumentDto createO(ItemDocumentDto itemDocumentDto);
    
    DLFolder createFolder(String name,DLFolder folder, long parentFolderId , ThemeDisplay themeDisplay, long userId, Long companyId);
}
