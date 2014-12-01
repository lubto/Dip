package eu.lms.core.daoImpl;

import eu.lms.core.entity.ItemAttempts;
import eu.lms.core.entity.UserBankQ;
import eu.lms.iface.dao.UserBankQDao;
import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lubomir Lacika
 * Base generic class providing access to the data layer.
 */
@Repository
public class UserBankQDaoImpl extends GenericDaoImpl<UserBankQ> implements UserBankQDao {
/**
   * This method is used to find all UserbankQ by ID of test.
   * @param bankQuestionsId This parameter is ID of test. 
   * @return List This returns the list of UserBankQ.
   */ 
    @Override
    public List<UserBankQ> getAllUserBankQByBankQ(String bankQuestionsId) {
         if (bankQuestionsId == null) {
            throw new IllegalArgumentException("BankQuestionsId is null");
        }
        List<UserBankQ> bank = null;
        TypedQuery<UserBankQ> query = null;
        query = em.createQuery("SELECT u FROM UserBankQ u WHERE u.bankQuestions = :bankQuestions", UserBankQ.class).setParameter("bankQuestions", bankQuestionsId);
        bank = query.getResultList();

        return bank;
    }
/**
   * This method is used to find all ItemAttempts by UserBankQ.
   * @param userBankQ This parameter is UserBankQ. 
   * @return List This returns the list of ItemAttempts.
   */ 
    @Override
    public List<ItemAttempts> getAllItemAttemptsByUserBankQ(UserBankQ userBankQ) {
       if (userBankQ == null) {
            throw new IllegalArgumentException("UserBankQ is null");
        }
        
        List<ItemAttempts> bank = null;
        TypedQuery<ItemAttempts> query = null;
        query = em.createQuery("SELECT u FROM ItemAttempts u WHERE u.userBankQ = :userBankQ", ItemAttempts.class).setParameter("userBankQ", userBankQ);
        bank = query.getResultList();

        return bank;
    }
    
}
