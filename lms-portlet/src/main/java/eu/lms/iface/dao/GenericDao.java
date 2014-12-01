package eu.lms.iface.dao;

import java.util.List;

/**
 *
 * @author TO-Lubomir
 * Base generic interface providing access to the data layer.
 */
public interface GenericDao <T> {
    
    void create(T t);
    void delete(T t);
    void update(T t);
    T getById(long id);
    List<T> getAll();
    
    T createO(T t); 
    
}
