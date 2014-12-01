package eu.lms.core.test;

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.LogMF;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * JDBC util allows to execute an SQL script file using JdbcTemplate.
 *
 * @author Lubomir Lacika
 */
public class JdbcTestUtils {

    private static final Logger LOG = Logger.getLogger(JdbcTestUtils.class);

    public static final String ENCODING = "UTF-8";

    /**
     * Executes the given SQL script.
     * Statements should be delimited by a semicolon or a new line (one statement per line).
     * 
     * @param jdbcTemplate the JdbcTemplate to be used for performing JDBC operations
     * @param resource the resource to load the SQL script from
     * @param continueOnError whether to continue without throwing an exception in the event of an error
     * 
     * @throws DataAccessException if there is an error executing a statement and continueOnError was <code>false</code>
     */
    public static void executeSqlScript(JdbcTemplate jdbcTemplate, Resource resource,
            boolean continueOnError) throws DataAccessException {

        executeSqlScript(jdbcTemplate, new EncodedResource(resource, ENCODING), continueOnError);
    }

    /**
     * Executes the given SQL script.
     * Statements should be delimited by a semicolon or a new line (one statement per line).
     * 
     * @param jdbcTemplate the JdbcTemplate to be used for performing JDBC operations
     * @param resource the resource to load the SQL script from
     * @param continueOnError whether to continue without throwing an exception in the event of an error
     * 
     * @throws DataAccessException if there is an error executing a statement and continueOnError was <code>false</code>
     */
    public static void executeSqlScript(JdbcTemplate jdbcTemplate, EncodedResource resource,
            boolean continueOnError) throws DataAccessException {

        LogMF.info(LOG, "Executing SQL script from {0}", resource);

        long startTime = System.currentTimeMillis();
        List<String> statements = new LinkedList<String>();
        LineNumberReader reader = null;

        try {
            reader = new LineNumberReader(resource.getReader());
            String script = org.springframework.test.jdbc.JdbcTestUtils.readScript(reader);
            char delimiter = ';';
            if (!org.springframework.test.jdbc.JdbcTestUtils.containsSqlScriptDelimiters(script, delimiter)) {
                delimiter = '\n';
            }

            org.springframework.test.jdbc.JdbcTestUtils.splitSqlScript(script, delimiter, statements);
            for (String statement : statements) {
                try {
                    int rowsAffected = jdbcTemplate.update(statement);
                    LogMF.debug(LOG, "{0} rows affected by SQL: {1}", rowsAffected, statement);
                } catch (DataAccessException ex) {
                    if (continueOnError) {
                        LogMF.warn(LOG, ex, "SQL: {0} failed", new String[]{statement});
                    } else {
                        throw ex;
                    }
                }
            }

            long elapsedTime = System.currentTimeMillis() - startTime;

            LogMF.info(LOG, "Done executing SQL scriptBuilder from {0} in {1} ms.", resource, elapsedTime);
        } catch (IOException ex) {
            throw new DataAccessResourceFailureException("Failed to open SQL script from " + resource, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                // ignore
            }

        }
    }
}
