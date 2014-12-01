package eu.lms.core.daoImpl;

import eu.lms.core.entity.AccountUser;
import eu.lms.core.entity.CourseItem;
import eu.lms.iface.dao.AccountUserDao;
import eu.lms.iface.dto.CourseItemDto;
import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lubomir Lacika
 * Base generic class providing access to the data layer.
 */
@Repository
public class AccountUserDaoImpl extends GenericDaoImpl<AccountUser> implements AccountUserDao {
 /**
   * This method is used to find account user by user ID.
   * @param userId This parameter is ID of user. 
   * @return AccountUser This returns the account of user.
   */
    @Override
    public AccountUser getAccountUserByUserId(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId is null");
        }
        AccountUser accountUser = null;
        TypedQuery query = null; 
        query = em.createQuery("SELECT u FROM AccountUser u WHERE u.userId = :userId", AccountUser.class).setParameter("userId", userId);
        accountUser = (AccountUser) query.getSingleResult();

        return accountUser;
    }
/**
   * This method is used to get all users IDs from users accounts. 
   * @return List This returns the list of ID users.
   */
    @Override
    public List<String> getAllUserIdFromAccountUser() {
        
        List<String> idUser = null;
        TypedQuery <String> query = null;
        query = em.createQuery("SELECT u.userId FROM AccountUser  AS u", String.class);
        idUser =  query.getResultList();

        return idUser;
    }
}
