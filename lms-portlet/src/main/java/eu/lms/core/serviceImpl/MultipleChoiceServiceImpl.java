package eu.lms.core.serviceImpl;

import eu.lms.core.daoImpl.ItemMultipleChoiceDaoImpl;
import eu.lms.core.daoImpl.MultipleChoiceDaoImpl;
import eu.lms.core.entity.ItemMultipleChoice;
import eu.lms.core.entity.MultipleChoice;
import eu.lms.core.mapper.Mapper;
import static eu.lms.core.mapper.Mapper.toEntity;
import static eu.lms.core.mapper.Mapper.toEntity;
import eu.lms.iface.dto.ItemMultipleChoiceDto;
import eu.lms.iface.dto.MultipleChoiceDto;
import eu.lms.iface.service.MultipleChoiceService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Lubomir Lacika Service providing access to the data-access-layer.
 */
@Service
@Transactional
public class MultipleChoiceServiceImpl implements MultipleChoiceService {

    protected final Logger LOG = Logger.getLogger(MultipleChoiceServiceImpl.class);

    @Autowired
    private MultipleChoiceDaoImpl multipleDao;

    @Autowired
    private ItemMultipleChoiceDaoImpl itemDao;

    @Override
    @Transactional(readOnly = true)
    public List<MultipleChoiceDto> getAllMultipleChoice() {
        List<MultipleChoice> entities = multipleDao.getAll();
        List<MultipleChoiceDto> dtos = new ArrayList<MultipleChoiceDto>(entities.size());

        for (MultipleChoice entity : entities) {
            dtos.add(Mapper.toDto(entity));
        }
        return dtos;
    }

    @Override
    public void save(MultipleChoiceDto questionMChoiceDto) {
        Validate.notNull(questionMChoiceDto);
        Validate.notNull(questionMChoiceDto.getId());

        MultipleChoice entity = multipleDao.getById(questionMChoiceDto.getId());
        multipleDao.update(Mapper.toEntity(entity, questionMChoiceDto));
    }

    @Override
    public void create(MultipleChoiceDto questionMChoiceDto) {
        Validate.notNull(questionMChoiceDto);
        Validate.isTrue(questionMChoiceDto.getId() == null);

        multipleDao.create(Mapper.toEntity(null, questionMChoiceDto));
    }

    @Override
    @Transactional(readOnly = true)
    public MultipleChoiceDto getById(Long id) {
        Validate.notNull(id);

        MultipleChoice entity = multipleDao.getById(id);
        return Mapper.toDto(entity);
    }

    @Override
    public MultipleChoiceDto deleteById(Long id) {
        Validate.notNull(id);

        MultipleChoice entity = multipleDao.getById(id);
        multipleDao.delete(entity);

        return Mapper.toDto(entity);
    }

    @Override
    public MultipleChoiceDto createO(MultipleChoiceDto questionMChoiceDto) {
        Validate.notNull(questionMChoiceDto);
        Validate.isTrue(questionMChoiceDto.getId() == null);

        MultipleChoice savedEntity = multipleDao.createO(Mapper.toEntity(null, questionMChoiceDto));

        return Mapper.toDto(savedEntity);
    }

    @Override
    public void addItemToMultipleChoice(List<ItemMultipleChoiceDto> itemDto, MultipleChoiceDto multipleChoiceDto) {
        Validate.notNull(multipleChoiceDto);
        Validate.notEmpty(itemDto);

        MultipleChoice entity = new MultipleChoice();
        entity = multipleDao.getById(multipleChoiceDto.getId());

        List<ItemMultipleChoice> listItemM = new ArrayList<ItemMultipleChoice>();
        for (int i = 0; i < itemDto.size(); i++) {
            listItemM.add(toEntity(null, itemDto.get(i)));

        }
        List<ItemMultipleChoice> listSaved = new ArrayList<ItemMultipleChoice>();
        for (Iterator<ItemMultipleChoice> it = listItemM.iterator(); it.hasNext();) {
            ItemMultipleChoice item = it.next();

            item.setMultipleChoice(entity);
            ItemMultipleChoice saved = itemDao.createO(item);
            listSaved.add(saved);
        }

        entity.getItemMultiplechoice().addAll(listSaved);
        multipleDao.update(entity);
//        for (Iterator<ItemMultipleChoice> it = listSaved.iterator(); it.hasNext();) {
//            ItemMultipleChoice item = it.next(); 
//            item.setMultipleChoice(Mapper.toEntity(null, multipleChoiceDto));
//             itemDao.update(item);  
//             LOG.info("OK update ok ");
//        }
        LOG.info("OK hotovo");
    }

    @Override
    public void deleteAllItemsBy(MultipleChoiceDto multipleChoiceDto) {
        Validate.notNull(multipleChoiceDto);
        Validate.isTrue(multipleChoiceDto.getId() != null);

        List<ItemMultipleChoice> list = itemDao.getAll();

        MultipleChoice entity = multipleDao.getById(multipleChoiceDto.getId());
        entity.getItemMultiplechoice().clear();

        for (Iterator<ItemMultipleChoice> it = list.iterator(); it.hasNext();) {
            ItemMultipleChoice itemMultipleChoice = it.next();
            if (itemMultipleChoice.getMultipleChoice().getId() == multipleChoiceDto.getId()) {
                itemMultipleChoice.setMultipleChoice(null);
                itemDao.update(itemMultipleChoice);
                itemDao.delete(itemMultipleChoice);
            }
        }
    }

    @Override
    public void removeItemM(List<ItemMultipleChoiceDto> itemDto, MultipleChoiceDto multipleDto) {
        Validate.notNull(multipleDto);
        Validate.notEmpty(itemDto);

        MultipleChoice entity = new MultipleChoice();
        entity = multipleDao.getById(multipleDto.getId());
        LOG.info("entity id " + entity.getId());

        List<ItemMultipleChoice> listItemM = new ArrayList<ItemMultipleChoice>(entity.getItemMultiplechoice());
        for (Iterator<ItemMultipleChoice> it = listItemM.iterator(); it.hasNext();) {
            ItemMultipleChoice ii = it.next();
            entity.getItemMultiplechoice().remove(ii);
            multipleDao.update(entity);
            ii.setMultipleChoice(null);
            itemDao.delete(ii);
        }
        LOG.info("OK hotovo");
    }

    @Override
    public List<MultipleChoiceDto> searchInList(String searchWord, List<MultipleChoiceDto> list) {
        String search = searchWord.toLowerCase();
        List<MultipleChoiceDto> listNew = new ArrayList<MultipleChoiceDto>();
        for (Iterator<MultipleChoiceDto> it = list.iterator(); it.hasNext();) {
            MultipleChoiceDto question = it.next();
            String name = question.getQuestion().toLowerCase();
            if (name.contains(search)) {
                listNew.add(question);
            }
        }
        return listNew;
    }

    @Override
    public List<MultipleChoiceDto> notSetOurseList(List<MultipleChoiceDto> listMultiple) {
        for (Iterator<MultipleChoiceDto> it = listMultiple.iterator(); it.hasNext();) {
            MultipleChoiceDto multipleChoiceDto = it.next();
            if (multipleChoiceDto.getBankQuestions().getId() != null) {
                it.remove();
            }
        }
        return listMultiple;
    }

    @Override
    public List<MultipleChoiceDto> getAllQuestionsByUser(List<MultipleChoiceDto> multipleChoice, String userId) {
        for (Iterator<MultipleChoiceDto> it = multipleChoice.iterator(); it.hasNext();) {
            MultipleChoiceDto multipleChoiceDto = it.next();
            String userID = String.valueOf(userId);
            if (!multipleChoiceDto.getUserId().equals(userID)) {
                it.remove();
            }
        }
        return multipleChoice;
    }

}
