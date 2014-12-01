package eu.lms.iface.dao;

import eu.lms.core.entity.ItemAttempts;
import eu.lms.core.entity.UserBankQ;
import java.util.List;

/**
 *
 * @author Lubomir Lacika
 * Base generic interface providing access to the data layer.
 */
public interface UserBankQDao {
    /**
   * This method is used to find all UserbankQ by ID of test.
   * @param bankQuestionsId This parameter is ID of test. 
   * @return List This returns the list of UserBankQ.
   */ 
    public List<UserBankQ> getAllUserBankQByBankQ(String bankQuestionsId); 
   /**
   * This method is used to find all ItemAttempts by UserBankQ.
   * @param userBankQ This parameter is UserBankQ. 
   * @return List This returns the list of ItemAttempts.
   */  
    public List<ItemAttempts> getAllItemAttemptsByUserBankQ(UserBankQ userBankQ); 
}
