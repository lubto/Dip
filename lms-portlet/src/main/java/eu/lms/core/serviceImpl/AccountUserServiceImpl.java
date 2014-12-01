package eu.lms.core.serviceImpl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import eu.lms.core.daoImpl.AccountUserDaoImpl;
import eu.lms.core.daoImpl.CourseItemDaoImpl;
import eu.lms.core.daoImpl.UserBankQDaoImpl;
import eu.lms.core.entity.AccountUser;
import eu.lms.core.entity.CourseItem;
import eu.lms.core.entity.UserBankQ;
import eu.lms.core.mapper.Mapper;
import eu.lms.iface.dto.AccountUserDto;
import eu.lms.iface.dto.CourseItemDto;
import eu.lms.iface.dto.UserBankQDto;
import eu.lms.iface.service.AccountUserService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
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
public class AccountUserServiceImpl implements AccountUserService {

    protected final Logger LOG = Logger.getLogger(AccountUserServiceImpl.class);

    @Autowired
    private AccountUserDaoImpl accountUserDao;

    @Autowired
    private CourseItemDaoImpl courseItemDao;

    @Autowired
    private UserBankQDaoImpl userBankQDao;
    
 /**
     * This method is used to get all AccountUsers.
     *
     * @return List This returns the list of AccountUsers.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AccountUserDto> getAllAccountUser() {
        List<AccountUser> entities = accountUserDao.getAll();
        List<AccountUserDto> dtos = new ArrayList<AccountUserDto>(entities.size());

        for (AccountUser entity : entities) {
            try {
                AccountUserDto accountDto = Mapper.toDto(entity);
                User user = UserLocalServiceUtil.getUser(Long.valueOf(accountDto.getUserId()));
                accountDto.setUserName(user.getFullName());
                dtos.add(accountDto);
            } catch (PortalException ex) {
                java.util.logging.Logger.getLogger(AccountUserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SystemException ex) {
                java.util.logging.Logger.getLogger(AccountUserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dtos;
    } 
    
    /**
     * This method is used to save AccountUser.
     *
     * @param accountItemDto This parameter is entity which we want to
     * save/update.
     */ 
    @Override
    public void saveAccountUser(AccountUserDto accountItemDto) {
        Validate.notNull(accountItemDto);
        Validate.notNull(accountItemDto.getId());

        AccountUser entity = accountUserDao.getById(accountItemDto.getId());
        accountUserDao.update(Mapper.toEntity(entity, accountItemDto));
    }
    
  /**
     * This method is used to create AccountUser.
     *
     * @param accountUserDto This parameter is entity which we want to create.
     */
    @Override
    public void createAccounUserDto(AccountUserDto accountUserDto) {
        Validate.notNull(accountUserDto);
        Validate.isTrue(accountUserDto.getId() == null);

        accountUserDao.create(Mapper.toEntity(null, accountUserDto));
    }
    
/**
     * This method is used to get AccountUser by ID.
     *
     * @param id This parameter is ID of account user.
     * @return AccountUsserDto This returns the AccountUserDto.
     */
    @Override
    public AccountUserDto getAccountUserId(Long id) {
        Validate.notNull(id);
        AccountUser entity = accountUserDao.getById(id);
        AccountUserDto dto = Mapper.toDto(entity);
        try {
            User user = UserLocalServiceUtil.getUser(Long.valueOf(dto.getUserId()));
            dto.setUserName(user.getFullName());
            return dto;
        } catch (PortalException ex) {
            java.util.logging.Logger.getLogger(AccountUserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            java.util.logging.Logger.getLogger(AccountUserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dto;
    }
    
  /**
     * This method is used to delete AccountUser by ID.
     *
     * @param id This parameter is ID of account user.
     * @return AccountUsserDto This returns the AccountUserDto.
     */
    @Override
    public AccountUserDto deleteAccountUserById(Long id) {
        Validate.notNull(id);
        AccountUser entity = accountUserDao.getById(id);
        accountUserDao.delete(entity);

        return Mapper.toDto(entity);
    } 
    
    /**
     * This method is used to get AccountUser by user´s ID.
     *
     * @param userId This parameter is ID of user.
     * @return AccountUsserDto This returns the AccountUserDto.
     */
    @Override
    public AccountUserDto getAccountUserByUserId(String userId) {
        return Mapper.toDto(accountUserDao.getAccountUserByUserId(userId));
    }
    
 /**
     * This method is used to add course item to account user.
     *
     * @param courseItemDto This parameter is courseItemDto.
     * @param id This parameter is ID of account user.
     * @param List This parameter is list of user´s bank of questions.
     */
    @Override
    public void addCourseItemToAccountUser(CourseItemDto courseItemDto, Long id, List<UserBankQDto> listUserBankQ) {
        Validate.notNull(id);

        AccountUser accountUser = new AccountUser();
        accountUser = accountUserDao.getById(id);

        CourseItem courseItem = courseItemDao.createO(Mapper.toEntity(null, courseItemDto));

        List<UserBankQ> listBankQ = new ArrayList<UserBankQ>();
        for (Iterator<UserBankQDto> it = listUserBankQ.iterator(); it.hasNext();) {
            UserBankQDto userBankQDto = it.next();
            UserBankQ userBQ = userBankQDao.createO(Mapper.toEntity(null, userBankQDto));
            userBQ.setCourseItem(courseItem);
            listBankQ.add(userBQ);
        }

        courseItem.setUserBankQ(listBankQ);
        courseItemDao.update(courseItem);
        courseItem.setAccountUser(accountUser);
        accountUser.getListCourseItems().add(courseItem);
        accountUserDao.update(accountUser);
    }
    
/**
     * This method is used to remove course item from account user.
     *
     * @param courseItemDto This parameter is courseItemDto.
     * @param id This parameter is ID of account user.
     */
    @Override
    public void removeCourseItem(CourseItemDto courseItemDto, Long id) {
        Validate.notNull(id);
        Validate.notNull(courseItemDto);

        AccountUser accountUser = accountUserDao.getById(id);
        CourseItem courseItem = courseItemDao.getById(courseItemDto.getId());

        accountUser.getListCourseItems().remove(courseItem);
        courseItem.setAccountUser(null);
        courseItemDao.delete(courseItem);
    }
    
    /**
     * This method is used to get all course items.
     *
     * @param accountUserDto This parameter is accountUserDto.
     * @return List This returns the list of CourseItemDto.
     */
    @Override
    public List<CourseItemDto> getAllCourseItems(AccountUserDto accountUserDto) {
        return accountUserDto.getListCourses();
    }
    
  /**
     * This method is used to get all user´s IDs from Account users.
     *
     * @return List This returns the list of users IDs.
     */
    @Override
    public List<String> getAllUserIdFromAccounUser() {
        return accountUserDao.getAllUserIdFromAccountUser();
    }
    
   /**
     * This method is used to change visible and invisible account user in the
     * portal.
     *
     * @param item This parameter is boolean value, if it is true, account user
     * is visible in the portal. If the value is false, account user is invisible in
     * the portal.
     * @param id This parameter is ID of account user.
     */
    @Override
    public void changeActivateAccountUser(Boolean item, Long id) {
        Validate.notNull(id);
        Validate.notNull(item);

        AccountUser entity = accountUserDao.getById(id);
        entity.setActivate(item); 
    }

 /**
     * This method is used to search in table of account users.
     *
     * @param searchWord This parameter is word which we want to search.
     * @param list This parameter is list of account users.
     * @return List This returns the list of CourseItemDto where the word is being searched.
     */
    @Override
    public List<AccountUserDto> searchInList(String searchWord, List<AccountUserDto> list) {
        String search = searchWord.toLowerCase();
          List<AccountUserDto> listNew = new ArrayList<AccountUserDto>();
          for (Iterator<AccountUserDto> it = list.iterator(); it.hasNext();) {
                AccountUserDto accountUserDto = it.next();
                String name = accountUserDto.getUserName().toLowerCase(); 
                if(name.contains(search)){
                listNew.add(accountUserDto);
                }
            }
          return listNew;
    } 
}
