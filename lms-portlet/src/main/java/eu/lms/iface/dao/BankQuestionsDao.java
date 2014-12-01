package eu.lms.iface.dao;

import eu.lms.core.entity.BankQuestions;
import java.util.List;

/**
 *
 * @author Lubomir Lacika 
 * Base generic interface providing access to the data layer.
 */
public interface BankQuestionsDao {

    /**
     * This method is used to find all tests by user ID. "ID" is user´s ID who
     * created the test.
     *
     * @param userId This parameter is user´s ID.
     * @return BankQuestions This returns the list of tests.
     */
    public List<BankQuestions> getAllBankQuestionsByUserId(String userId);
}
