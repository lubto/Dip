package eu.lms.iface.service;

import eu.lms.iface.dto.ItemMultipleChoiceDto;
import eu.lms.iface.dto.MultipleChoiceDto;
import java.util.List;

/**
 *
 * @author Lubomir Lacika
 * Service providing access to the data-access-layer.
 */
public interface ItemMultipleChoiceService {

    List<ItemMultipleChoiceDto> getAllItemMultipleChoice();
     
    void save(ItemMultipleChoiceDto questionItemMCHDto);

    void create(ItemMultipleChoiceDto questionItemMCHDto);

    ItemMultipleChoiceDto getById(Long id);

    ItemMultipleChoiceDto deleteById(Long id);

    ItemMultipleChoiceDto createO(ItemMultipleChoiceDto itemMCHDto);
}
