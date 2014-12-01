package eu.lms.core.test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "classpath:test-spring-context/core-test-context.xml",
    "classpath:test-spring-context/core-test-database.xml",
    "classpath:spring-context/core-services.xml",
    "classpath:spring-context/core-jpa.xml"
})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public abstract class AbstractTest {

    /**
     * Pro přímou kontrolu dat v DB.
     */
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    /**
     * Přímý přístup k spring aplikačnímu kontextu.
     */
    @Autowired
    protected ApplicationContext applicationContext;

    /**
     * Přímý přístup k JPA entity manageru.
     */
    @PersistenceContext
    protected EntityManager em;

    /**
     * Simuluje novou transakci pomocí vylití cache v Entity Manageru.
     */
    public void simulateNewTransaction() {
        em.flush();
        em.clear();
    }
}
