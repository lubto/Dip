package eu.lms.core.daoImpl;

import eu.lms.core.entity.BankQuestions;
import eu.lms.iface.dao.BankQuestionsDao;
import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lubomir Lacika 
 * Base generic class providing access to the data layer.
 */
@Repository
public class BankQuestionsDaoImpl extends GenericDaoImpl<BankQuestions> implements BankQuestionsDao {
 /**
   * This method is used to find all tests by user ID. "ID" is user´s ID who created the test.
   * @param userId This parameter is user´s ID. 
   * @return BankQuestions This returns the list of tests.
   */
    @Override
    public List<BankQuestions> getAllBankQuestionsByUserId(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId is null");
        }
        List<BankQuestions> bankQuestions = null;
        TypedQuery<BankQuestions> query = null;
        query = em.createQuery("SELECT u FROM BankQuestions u WHERE u.userId = :userId", BankQuestions.class).setParameter("userId", userId);
        bankQuestions = query.getResultList();

        return bankQuestions;
    }
}
