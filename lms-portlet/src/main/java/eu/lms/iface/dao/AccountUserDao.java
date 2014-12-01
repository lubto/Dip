package eu.lms.iface.dao;

import eu.lms.core.entity.AccountUser;
import eu.lms.core.entity.CourseItem;
import java.util.List;

/**
 *
 * @author Lubomir Lacika
 * Base generic interface providing access to the data layer.
 */
public interface AccountUserDao {
  /**
   * This method is used to find account user by user ID.
   * @param userId This parameter is ID of user. 
   * @return AccountUser This returns the account of user.
   */
    public AccountUser getAccountUserByUserId(String userId);
    /**
   * This method is used to get all users IDs from users accounts. 
   * @return List This returns the list of ID users.
   */
    public List<String> getAllUserIdFromAccountUser();
}
