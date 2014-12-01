package eu.lms.core.serviceImpl;

import eu.lms.core.daoImpl.BankQuestionsDaoImpl;
import eu.lms.core.daoImpl.CourseDaoImpl;
import eu.lms.core.daoImpl.MultipleChoiceDaoImpl;
import eu.lms.core.entity.BankQuestions;
import eu.lms.core.entity.Course;
import eu.lms.core.entity.MultipleChoice;
import eu.lms.core.mapper.Mapper;
import eu.lms.iface.dto.BankQuestionsDto;
import eu.lms.iface.dto.ItemMultipleChoiceDto;
import eu.lms.iface.dto.MultipleChoiceDto;
import eu.lms.iface.service.BankQuestionsService;
import java.awt.event.ItemListener;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service providing access to the data-access-layer.
 *
 * @author Lubomir Lacika
 */
@Service
@Transactional
public class BankQuestionsServiceImpl implements BankQuestionsService {

    protected final Logger LOG = Logger.getLogger(BankQuestionsServiceImpl.class);

    @Autowired
    private BankQuestionsDaoImpl bankQuestionsDao;

    @Autowired
    private MultipleChoiceDaoImpl multipleCHDao;

    @Autowired
    private CourseDaoImpl courseDao;

    /**
     * This method is used to get all tests.
     *
     * @return List This returns the list of tests.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BankQuestionsDto> getAll() {
        List<BankQuestions> entities = bankQuestionsDao.getAll();
        List<BankQuestionsDto> dtos = new ArrayList<BankQuestionsDto>(entities.size());

        for (BankQuestions entity : entities) {
            dtos.add(Mapper.toDto(entity));
        }
        return dtos;
    }

   /**
     * This method is used to save test.
     *
     * @param bankQuestionsDto This parameter is entity which we want to
     * save/update.
     */
    @Override
    public void save(BankQuestionsDto bankQuestionsDto) {
        Validate.notNull(bankQuestionsDto);
        Validate.notNull(bankQuestionsDto.getId());

        BankQuestions entity = bankQuestionsDao.getById(bankQuestionsDto.getId());
        bankQuestionsDao.update(Mapper.toEntity(entity, bankQuestionsDto));
    }

   /**
     * This method is used to create test.
     *
     * @param bankQuestionsDto This parameter is entity which we want to create.
     */
    @Override
    public void create(BankQuestionsDto bankQuestionsDto) {
        Validate.notNull(bankQuestionsDto);
        Validate.notNull(bankQuestionsDto.getId() == null);

        bankQuestionsDao.create(Mapper.toEntity(null, bankQuestionsDto));
    }

    /**
     * This method is used to get test by ID.
     *
     * @param id This parameter is ID of test.
     * @return BankQuestionsDto This returns the test.
     */
    @Override
    @Transactional(readOnly = true)
    public BankQuestionsDto getById(Long id) {
        Validate.notNull(id);
        BankQuestions entity = bankQuestionsDao.getById(id);

        return Mapper.toDto(entity);
    }

     /**
     * This method is used to delete test by ID.
     *
     * @param id This parameter is ID of test.
     * @return AccountUsserDto This returns the test.
     */
    @Override
    public BankQuestionsDto deleteById(Long id) {
        Validate.notNull(id);
        BankQuestions entity = bankQuestionsDao.getById(id);
        bankQuestionsDao.delete(entity);

        return Mapper.toDto(entity);
    }

     /**
     * This method is used to add question to test.
     *
     * @param idQuestion This parameter is ID of question.
     * @param idBank This parameter is ID of test. 
     */
    @Override
    public void addQ(Long idQuestion, Long idBank) {
        Validate.notNull(idQuestion);
        Validate.notNull(idBank);

        BankQuestions entityBank = new BankQuestions();
        entityBank = bankQuestionsDao.getById(idBank);

        MultipleChoice question = new MultipleChoice();
        question = multipleCHDao.getById(idQuestion);
        question.setBankQuestions(entityBank);

        entityBank.getMultipleChoice().add(question);
        int i = entityBank.getNumberAllQ();
        entityBank.setNumberAllQ((i + 1));

        bankQuestionsDao.update(entityBank);
    }

      /**
     * This method is used to remove question from test.
     *
     * @param idQuestion This parameter is ID of question.
     * @param idBank This parameter is ID of test.
     */
    @Override
    public void removeQ(Long idQuestion, Long idBank) {
        Validate.notNull(idQuestion);
        Validate.notNull(idBank);

        BankQuestions entityBank = new BankQuestions();
        entityBank = bankQuestionsDao.getById(idBank);

        MultipleChoice question = new MultipleChoice();
        question = multipleCHDao.getById(idQuestion);

        entityBank.getMultipleChoice().remove(question);
        int i = entityBank.getNumberAllQ();
        entityBank.setNumberAllQ((i - 1));

        bankQuestionsDao.update(entityBank);
        question.setBankQuestions(null);

    }

     /**
     * This method is used to generate test full of questions. In this method is matching questions.
     * 
     * @param bankQ This parameter is test.
     * @return List This parameter return list of questions.
     */
    @Override
    public List<MultipleChoiceDto> generateTestQuestions(BankQuestionsDto bankQ) {
        List<MultipleChoiceDto> listM = new ArrayList<MultipleChoiceDto>();
        List<MultipleChoiceDto> listOld = bankQ.getMultipleChoiceDto();

        if (bankQ.isAllQBank() == true) {
            if (bankQ.isRandomQ() == true) {
                listM = randomQuestion(bankQ.getMultipleChoiceDto().size(), listOld);
            } else {
                listM = bankQ.getMultipleChoiceDto();
            }
        } else {
            listM = randomQuestion(bankQ.getNumberQForTest(), listOld);
        }
        if (bankQ.isRandomSubQ() == true) {
            listM = randomQuestionSub(listM);
        }
        return listM;
    }
    
  /**
     * This method is used to shuffle the order of answers.
     * 
     * @param listM This parameter is list of questions.
     * @return List This parameter return list of questions.
     */ 
    private List<MultipleChoiceDto> randomQuestionSub(List<MultipleChoiceDto> listM) {
        List<MultipleChoiceDto> listI = new ArrayList<MultipleChoiceDto>();
        for (Iterator<MultipleChoiceDto> it = listM.iterator(); it.hasNext();) {
            MultipleChoiceDto item = it.next();
            listI.add(mixSubQuestions(item));
        }
        return listI;
    }

     /**
     * This method is used to shuffle the order of answers.
     * 
     * @param listM This parameter is list of questions.
     * @return MultipleChoiceDto This parameter return question with new order.
     */
    private MultipleChoiceDto mixSubQuestions(MultipleChoiceDto item) {
        MultipleChoiceDto itemOld = item;
        MultipleChoiceDto itemNew = item;
        List<ItemMultipleChoiceDto> listMultiple = new ArrayList<ItemMultipleChoiceDto>();
        int max = item.getItems().size();
        Random rand = new Random();
        while (max > 0) {
            int index = rand.nextInt(itemOld.getItems().size());
            itemNew.getItems().add(itemOld.getItems().get(index));
            itemOld.getItems().remove(index);
            max--;
        }
        return itemNew;
    }

     /**
     * This method is used to shuffle the order of questions.
     * @param max This parameter is number of question for test.
     * @param listM This parameter is list of questions.
     * @return List This parameter return list of questions by the value of max.
     */ 
    private List<MultipleChoiceDto> randomQuestion(int max, List<MultipleChoiceDto> listOld) {
        List<MultipleChoiceDto> listM = new ArrayList<MultipleChoiceDto>();
        int maxi = max;
        Random rand = new Random();
        while (maxi > 0) {
            int index = rand.nextInt(listOld.size());
            listM.add(listOld.get(index));
            listOld.remove(index);
            maxi--;
        }
        return listM;
    }

      /**
     * This method is used to get all tests by ID of user.
     *
     * @param userId This parameter is Id´ user.
     * @return List This value returns the list of test.
     */
    @Override
    public List<BankQuestionsDto> getAllBankQuestionsByUserId(String userId) {
        Validate.notNull(userId);
        List<BankQuestionsDto> listDto = new ArrayList<BankQuestionsDto>();
        List<BankQuestions> list = bankQuestionsDao.getAllBankQuestionsByUserId(userId);
        for (Iterator<BankQuestions> it = list.iterator(); it.hasNext();) {
            BankQuestions bankQuestions = it.next();
            listDto.add(Mapper.toDto(bankQuestions));
        }
        return listDto;
    }

     /**
     * This method is used to search in table of test.
     *
     * @param searchWord This parameter is word which we want to search.
     * @param list This parameter is list of tests.
     * @return List This returns the list of test where the word is
     * being searched.
     */
    @Override
    public List<BankQuestionsDto> searchInList(String searchWord, List<BankQuestionsDto> list) {
        String search = searchWord.toLowerCase();
        List<BankQuestionsDto> listNew = new ArrayList<BankQuestionsDto>();
        for (Iterator<BankQuestionsDto> it = list.iterator(); it.hasNext();) {
            BankQuestionsDto bank = it.next();
            String name = bank.getName().toLowerCase();
            if (name.contains(search)) {
                listNew.add(bank);
            }
        }
        return listNew;
    }

     /**
     * This method is used to change position of tests
     *
     * @param upOrDown This parameter is boolean value, if it is true, value of test
     * is incremented by one. If the value is false, value of test is reduced by one.
     * @param bank This parameter is test which we want to change position.
     */
    @Override
    public void changePosition(boolean upOrDown, BankQuestionsDto bank) {
        BankQuestions entity = bankQuestionsDao.getById(bank.getId());
        Course course = courseDao.getById(entity.getCourse().getId());
        int number = entity.getSerialNumber();
        List<BankQuestions> list = course.getBankQuestions();
        if (upOrDown == true) {
            if (entity.getSerialNumber() != 1 || entity.getSerialNumber() != 0) {
                for (BankQuestions bank1 : list) {
                    if (bank1.getSerialNumber() == number - 1) {
                        BankQuestions l = bankQuestionsDao.getById(bank1.getId());
                        l.setSerialNumber(number);
                        entity.setSerialNumber(number - 1);
                        break;
                    }
                }
            }
        } else {
            for (BankQuestions lesson1 : list) {
                if (lesson1.getSerialNumber() == number + 1) {
                    BankQuestions l = bankQuestionsDao.getById(lesson1.getId());
                    l.setSerialNumber(number);
                    entity.setSerialNumber(number + 1);
                    break;
                }
            }
        }
    }

}
