package eu.lms.iface.service;

import eu.lms.iface.dto.BankQuestionsDto;
import eu.lms.iface.dto.MultipleChoiceDto;
import java.util.List;

/**
 * Service providing access to the data-access-layer.
 *
 * @author Lubomir Lacika
 */
public interface BankQuestionsService {

    /**
     * This method is used to get all tests.
     *
     * @return List This returns the list of tests.
     */
    List<BankQuestionsDto> getAll();

    /**
     * This method is used to save test.
     *
     * @param bankQuestionsDto This parameter is entity which we want to
     * save/update.
     */
    void save(BankQuestionsDto bankQuestionsDto);

    /**
     * This method is used to create test.
     *
     * @param bankQuestionsDto This parameter is entity which we want to create.
     */
    void create(BankQuestionsDto bankQuestionsDto);

    /**
     * This method is used to get test by ID.
     *
     * @param id This parameter is ID of test.
     * @return BankQuestionsDto This returns the test.
     */
    BankQuestionsDto getById(Long id);

    /**
     * This method is used to delete test by ID.
     *
     * @param id This parameter is ID of test.
     * @return AccountUsserDto This returns the test.
     */
    BankQuestionsDto deleteById(Long id);

    /**
     * This method is used to add question to test.
     *
     * @param idQuestion This parameter is ID of question.
     * @param idBank This parameter is ID of test.
     */
    void addQ(Long idQuestion, Long idBank);

    /**
     * This method is used to remove question from test.
     *
     * @param idQuestion This parameter is ID of question.
     * @param idBank This parameter is ID of test.
     */
    void removeQ(Long idQuestion, Long idBank);

    /**
     * This method is used to generate test full of questions. In this method is
     * matching questions.
     *
     * @param bankQ This parameter is test.
     * @return List This parameter return list of questions.
     */
    List<MultipleChoiceDto> generateTestQuestions(BankQuestionsDto bankQuestionsDto);

    /**
     * This method is used to get all tests by ID of user.
     *
     * @param userId This parameter is Id´ user.
     * @return List This value returns the list of test.
     */
    List<BankQuestionsDto> getAllBankQuestionsByUserId(String userId);

    /**
     * This method is used to search in table of test.
     *
     * @param searchWord This parameter is word which we want to search.
     * @param list This parameter is list of tests.
     * @return List This returns the list of test where the word is being
     * searched.
     */
    List<BankQuestionsDto> searchInList(String searchWord, List<BankQuestionsDto> list);

    /**
     * This method is used to change position of tests
     *
     * @param upOrDown This parameter is boolean value, if it is true, value of
     * test is incremented by one. If the value is false, value of test is
     * reduced by one.
     * @param bank This parameter is test which we want to change position.
     */
    void changePosition(boolean upOrDown, BankQuestionsDto bank);
}
