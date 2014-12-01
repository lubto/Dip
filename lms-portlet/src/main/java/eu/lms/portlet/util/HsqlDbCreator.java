
package eu.lms.portlet.util;

import javax.activation.DataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author TO-Lubomir
 */
public class HsqlDbCreator implements InitializingBean {

    private String schema;
    private DataSource dataSource;

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Create schema.
     *
     * @throws Exception any exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate((javax.sql.DataSource) dataSource);
        jdbcTemplate.execute("CREATE SCHEMA " + schema + " AUTHORIZATION DBA");

    }
}
