package eu.lms.core.daoImpl;

/**
 *
 * @author Lubomir Lacika
 * Base generic class providing access to the data layer.
 */
import eu.lms.iface.dao.GenericDao;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public class GenericDaoImpl<T> implements GenericDao<T> {

    @PersistenceContext
    protected EntityManager em;

    private final Class<T> entityClass;

    public GenericDaoImpl() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        entityClass = (Class<T>) type.getActualTypeArguments()[0];
    }

    @Override
    public void create(T t) {
        em.persist(t);
    }

    @Override
    public void delete(T t) {
        em.remove(t);
    }

    @Override
    public void update(T t) {
        em.merge(t);
    }

    @Override
    public T getById(long id) {
        T result = em.find(entityClass, id);
        if (result == null) {
            throw new EntityNotFoundException();
        }
        return result;
    }

    @Override
    public List<T> getAll() {
        TypedQuery<T> q = em.createQuery("SELECT e FROM "
                + entityClass.getSimpleName() + " e", entityClass);
        return q.getResultList();
    }

    @Override
    public T createO(T t) {
        em.persist(t);
        return t;
    }
 
}
