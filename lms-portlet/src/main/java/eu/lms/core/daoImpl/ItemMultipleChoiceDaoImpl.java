package eu.lms.core.daoImpl;

import eu.lms.core.entity.ItemMultipleChoice;
import eu.lms.core.entity.MultipleChoice;
import eu.lms.iface.dao.ItemMultipleChoiceDao;
import java.util.List;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Lubomir Lacika
 * Base generic class providing access to the data layer.
 */
@Repository
public class ItemMultipleChoiceDaoImpl extends GenericDaoImpl<ItemMultipleChoice> implements ItemMultipleChoiceDao{
//nefungujeee
//    @Override
//    public List<ItemMultipleChoice> getAllItemMChbyMCh(MultipleChoice multipleChoice) {
//          if (multipleChoice == null) {
//            throw new IllegalArgumentException("MultipleChoice is null");
//        }
//        List<ItemMultipleChoice> items = null;
//        TypedQuery<ItemMultipleChoice> query = null;
//        query = em.createQuery("SELECT u FORM ItemMultipleChoice u INNER JOIN u.multipleChoice p WHERE p.id = :id", ItemMultipleChoice.class).setParameter("id", multipleChoice.getId());
//        items = query.getResultList();
//
//        return items;
//    }
    
}
