package eu.lms.core.serviceImpl;

import eu.lms.core.daoImpl.ItemMultipleChoiceDaoImpl;
import eu.lms.core.daoImpl.MultipleChoiceDaoImpl;
import eu.lms.core.entity.ItemMultipleChoice;
import eu.lms.core.entity.MultipleChoice;
import eu.lms.core.mapper.Mapper;
import eu.lms.iface.dto.ItemMultipleChoiceDto;
import eu.lms.iface.dto.MultipleChoiceDto;
import eu.lms.iface.service.ItemMultipleChoiceService;
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
 * @author Lubomir Lacika
 * Service providing access to the data-access-layer.
 */
@Service
@Transactional
public class ItemMultipleChoiceServiceImpl implements ItemMultipleChoiceService {
protected final Logger LOG = Logger.getLogger(ItemMultipleChoiceServiceImpl.class);
    @Autowired
    private ItemMultipleChoiceDaoImpl questionItemMCHDao;
    
    @Autowired
    private MultipleChoiceDaoImpl questionMChDao;

    @Override
    @Transactional(readOnly = true)
    public List<ItemMultipleChoiceDto> getAllItemMultipleChoice() {
        List<ItemMultipleChoice> entities = questionItemMCHDao.getAll();
        List<ItemMultipleChoiceDto> dtos = new ArrayList<ItemMultipleChoiceDto>(entities.size());

        for (ItemMultipleChoice entity : entities) {
            dtos.add(Mapper.toDto(entity));
        }
        return dtos;
    }

    @Override
    public void save(ItemMultipleChoiceDto questionItemMCHDto) {
        Validate.notNull(questionItemMCHDto);
        Validate.notNull(questionItemMCHDto.getId());

        ItemMultipleChoice entity = questionItemMCHDao.getById(questionItemMCHDto.getId());
        questionItemMCHDao.update(Mapper.toEntity(entity, questionItemMCHDto));
    }

    @Override
    public void create(ItemMultipleChoiceDto questionItemMCHDto) {
        Validate.notNull(questionItemMCHDto);
        Validate.isTrue(questionItemMCHDto.getId() == null);

        questionItemMCHDao.create(Mapper.toEntity(null, questionItemMCHDto));

    }

    @Override
    @Transactional(readOnly = true)
    public ItemMultipleChoiceDto getById(Long id) {
        Validate.notNull(id);

        ItemMultipleChoice entity = questionItemMCHDao.getById(id);
        return Mapper.toDto(entity);
    }

    @Override
    public ItemMultipleChoiceDto deleteById(Long id) {
        Validate.notNull(id);

        ItemMultipleChoice entity = questionItemMCHDao.getById(id);
        questionItemMCHDao.delete(entity);

        return Mapper.toDto(entity);
    }

    @Override
    public ItemMultipleChoiceDto createO(ItemMultipleChoiceDto questionItemMCHDto) {
        Validate.notNull(questionItemMCHDto);
        Validate.isTrue(questionItemMCHDto.getId() == null); 
        
        ItemMultipleChoice savedEntity = questionItemMCHDao.createO(Mapper.toEntity(null, questionItemMCHDto)); 
        
        return Mapper.toDto(savedEntity);
    } 

}
