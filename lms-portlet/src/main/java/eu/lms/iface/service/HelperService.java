package eu.lms.iface.service;

import eu.lms.iface.dto.MultipleChoiceDto;
import java.util.List;

/**
 *Service providing access to the data-access-layer.
 * @author Lubomir Lacika
 * 
 */
public interface HelperService {
     /**
     * This method is used to remove brackets because they create by sending parameters through request.
     * 
     * @param list This parameter is list of strings with IDs and brackets.
     * @return List This returns the list of IDs without brackets.
     * searched.
     */
    List<String> removeBrackets(List<String> list);

     /**
     * This method is used to change two stings´s IDs from list of questions and list of answers to list of object MulitpleChoiceDto.
     * @param itemMultipleChoices This parameter is list of strings with IDs of answers.
     * @param multipleChoice This parameter is list of strings with IDs of questions.
     * @return List This returns the list of MultipleChoiceDto.
     * searched.
     */
    List<MultipleChoiceDto> changeTwoStringToMultiple(List<String> multipleChoice, List<String> itemMultipleChoices);
    
      /**
     * This method is used to view log of sending data (questions and answers) for checking.
     * @param multiple This parameter is list of questions.  
     */
    void writeLogQuestion(List<MultipleChoiceDto> multipleChoiceDto); 
    
      /**
     * This method is used to reducing the size entered value for table-
     * @param size This parameter is length of value  
     * @param text This parameter is text which we want to reduce.   
     */
    String cutString(int size, String text);
}
