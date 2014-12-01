package eu.lms.iface.service;

import eu.lms.iface.dto.MultipleChoiceDto;
import eu.lms.iface.dto.ItemMultipleChoiceDto;
import java.util.List;

/**
 *
 * @author Lubomir Lacika
 * Service providing access to the data-access-layer.
 */
public interface MultipleChoiceService {

    List<MultipleChoiceDto> getAllMultipleChoice();

    void save(MultipleChoiceDto questionMChoiceDto);

    void create(MultipleChoiceDto questionMChoiceDto);

    MultipleChoiceDto getById(Long id);

    MultipleChoiceDto deleteById(Long id);

    MultipleChoiceDto createO(MultipleChoiceDto questionMChoiceDto);
    
    void addItemToMultipleChoice(List<ItemMultipleChoiceDto> itemDto, MultipleChoiceDto multipleChoiceDto);
    
    void removeItemM(List<ItemMultipleChoiceDto> itemDto, MultipleChoiceDto multipleDto);
    
    void deleteAllItemsBy(MultipleChoiceDto multipleChoiceDto);
    
    List<MultipleChoiceDto> searchInList(String searchWord, List<MultipleChoiceDto> list);
    
    List<MultipleChoiceDto> notSetOurseList(List<MultipleChoiceDto> multipleChoice);
    List<MultipleChoiceDto> getAllQuestionsByUser(List<MultipleChoiceDto> multipleChoice, String UserId);
}
