package eu.lms.core.serviceImpl;

import eu.lms.iface.dto.ItemMultipleChoiceDto;
import eu.lms.iface.dto.MultipleChoiceDto;
import eu.lms.iface.service.HelperService;
import eu.lms.iface.service.ItemMultipleChoiceService;
import eu.lms.iface.service.MultipleChoiceService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
public class HelperServiceImpl implements HelperService {

    protected final Logger LOG = Logger.getLogger(HelperServiceImpl.class);

    @Autowired
    private MultipleChoiceService serviceMultipleChoice;

    @Autowired
    private ItemMultipleChoiceService serviceItemMultipleChoice;

     /**
     * This method is used to remove brackets because they create by sending parameters through request.
     * 
     * @param list This parameter is list of strings with IDs and brackets.
     * @return List This returns the list of IDs without brackets.
     * searched.
     */
    @Override
    public List<String> removeBrackets(List<String> list) {
        List<String> multiple = new ArrayList<String>();

        for (Iterator<String> it = list.iterator(); it.hasNext();) {
            String string = it.next();
            // LOG.info("id : " + string); 
            //we have to remove [, ] ,[] from string what is send from Action chceckTest
            String firstLetter = String.valueOf(string.charAt(0));
            String lastLetter = String.valueOf(string.charAt(string.length() - 1));
            //LOG.info("last " + lastLetter);
            if (firstLetter.equals("[") && lastLetter.equals("]")) {
                // LOG.info("zmena na: " + string.substring(1, string.length() - 1));
                multiple.add(string.substring(1, string.length() - 1));
            } else if (firstLetter.equals("[")) {
                multiple.add(string.substring(1));
                // LOG.info("zmena na: " + string.substring(1));
            } else if (lastLetter.equals("]")) {
                //  LOG.info("zmena na: " + string.substring(0, string.length() - 1));
                multiple.add(string.substring(0, string.length() - 1));
            } else {
                multiple.add(string);
                //LOG.info("ciste : " + string);
            }
        }
        return multiple;
    }

     /**
     * This method is used to change two stings´s IDs from list of questions and list of answers to list of object MulitpleChoiceDto.
     * @param itemMultipleChoices This parameter is list of strings with IDs of answers.
     * @param multipleChoice This parameter is list of strings with IDs of questions.
     * @return List This returns the list of MultipleChoiceDto.
     * searched.
     */
    @Override
    public List<MultipleChoiceDto> changeTwoStringToMultiple(List<String> multipleChoice, List<String> itemMultipleChoices) {

        List<MultipleChoiceDto> multiple = new ArrayList<MultipleChoiceDto>();

        List<String> listMultipleId = removeBrackets(multipleChoice);
        int number = 0;
        List<String> listItemMultipleId = removeBrackets(itemMultipleChoices);
        for (Iterator<String> it = listMultipleId.iterator(); it.hasNext();) {
            String string = it.next();

            MultipleChoiceDto dto = serviceMultipleChoice.getById(Long.valueOf(string));
            int size = dto.getItems().size();
            dto.getItems().clear();

            List<ItemMultipleChoiceDto> itemM = new ArrayList<ItemMultipleChoiceDto>();
            while (size > 0) {

                if (!listItemMultipleId.get(number).equals("0")) {
                    itemM.add(serviceItemMultipleChoice.getById(Long.valueOf(listItemMultipleId.get(number))));
                    size--;
                    number++;
                } else {
                    break;
                }
            }
            number++;
            dto.setItems(itemM);
            multiple.add(dto);
        }
        return multiple;
    }

     /**
     * This method is used to view log of sending data (questions and answers) for checking.
     * @param multiple This parameter is list of questions.  
     */
    @Override
    public void writeLogQuestion(List<MultipleChoiceDto> multiple) {
        for (Iterator<MultipleChoiceDto> it = multiple.iterator(); it.hasNext();) {
            MultipleChoiceDto multipleChoiceDto = it.next();
            LOG.info("log Q " + multipleChoiceDto.getQuestion());

            List<ItemMultipleChoiceDto> listMQ = multipleChoiceDto.getItems();
            for (Iterator<ItemMultipleChoiceDto> it1 = listMQ.iterator(); it1.hasNext();) {
                ItemMultipleChoiceDto itemMultipleChoiceDto = it1.next();
                LOG.info("log A " + itemMultipleChoiceDto.getText());
                LOG.info("log boolean " + itemMultipleChoiceDto.isAnswer());
            }
        }
    }

      /**
     * This method is used to reducing the size entered value for table-
     * @param size This parameter is length of value  
     * @param text This parameter is text which we want to reduce.   
     */
    @Override
    public String cutString(int size, String text) {
        if (size < text.length()) {
            text = text.substring(0, size);
            LOG.info("size: " + size);
            LOG.info("string: " + text);
            LOG.info("size string: " + text.length());
        }
        LOG.info("je menej");
        return text;
    }

}
