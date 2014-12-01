package eu.lms.core.serviceImpl;

import eu.lms.core.daoImpl.CourseItemDaoImpl;
import eu.lms.core.daoImpl.ItemAttemptsDaoImpl;
import eu.lms.core.daoImpl.UserBankQDaoImpl;
import eu.lms.core.entity.CourseItem;
import eu.lms.core.entity.ItemAttempts;
import eu.lms.core.entity.UserBankQ;
import eu.lms.core.mapper.Mapper;
import static eu.lms.core.mapper.Mapper.toEntity;
import static eu.lms.core.mapper.Mapper.toEntity;
import static eu.lms.core.mapper.Mapper.toEntity;
import static eu.lms.core.mapper.Mapper.toEntity;
import static eu.lms.core.mapper.Mapper.toEntity;
import eu.lms.iface.dto.ItemAttemptsDto;
import eu.lms.iface.dto.UserBankQDto;
import eu.lms.iface.service.UserBankQService;
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
public class UserBankQServiceImpl implements UserBankQService {
    
    protected final Logger LOG = Logger.getLogger(UserBankQServiceImpl.class);
    
    @Autowired
    private UserBankQDaoImpl userBankQDao;
    
    @Autowired
    private ItemAttemptsDaoImpl itemAttemptsDao;
    
    @Autowired
    private CourseItemDaoImpl courseItemDao;
    
    @Override
    @Transactional(readOnly = true)
    public UserBankQDto getById(Long id) {
        Validate.notNull(id);
        
        UserBankQ entity = userBankQDao.getById(id);
        return Mapper.toDto(entity);
    }
    
    @Override
    public UserBankQDto deleteById(Long id) {
        Validate.notNull(id);
        
        UserBankQ entity = userBankQDao.getById(id);
        userBankQDao.delete(entity);
        return Mapper.toDto(entity);
    }
    
    @Override
    public void addItemAttempts(ItemAttemptsDto itemDto, UserBankQDto userBankQDto) {
        Validate.notNull(userBankQDto);
        
        UserBankQ entity = new UserBankQ();
        entity = userBankQDao.getById(userBankQDto.getId());
        
        ItemAttempts itemAttempts = toEntity(null, itemDto);
        itemAttempts.setUserBankQ(entity);
        
        entity.getItemAttempts().add(itemAttemptsDao.createO(itemAttempts));
        entity.setNumberOfAttempts(entity.getNumberOfAttempts() + 1);
        entity.setPoints(itemDto.getPoints());
        if (userBankQDto.isDone() == true) {
            entity.setDone(true); 
            CourseItem courseItem = courseItemDao.getById(entity.getCourseItem().getId());
            int number = 0;
            for (Iterator<UserBankQ> it = courseItem.getUserBankQ().iterator(); it.hasNext();) {
                UserBankQ userBankQ = it.next();
                if (userBankQ.isDone() == true) {
                    number++;
                }
            }
            if (number == courseItem.getUserBankQ().size()) {
                courseItem.setFinishedCourse(true);
            } 
        }
        userBankQDao.update(entity);
        LOG.info("OK hotovo");
    }
    
}
