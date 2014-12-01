package eu.lms.core.service;

import eu.lms.core.test.AbstractTest;
import eu.lms.core.test.JdbcTestUtils;
import eu.lms.iface.dto.CategoryDto;
import eu.lms.iface.service.CategoryService;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author test
 */
public class CategoryServiceImplTest extends AbstractTest {

    @Autowired
    private CategoryService categoryService;

    @BeforeClass
    public static void setUpClass() throws Exception {

    }

    @AfterClass
    public static void tearDownClass() throws Exception {

    }

    @Before
    public void setUp() {
        /* database = new EmbeddedDatabaseBuilder().addDefaultScripts().build();
         assertThat(database, is(notNullValue()));*/
        JdbcTestUtils.executeSqlScript(jdbcTemplate,
                new ClassPathResource("sql/category.sql"), false);
    }

    @After
    public void tearDown() {
        /*database.shutdown();*/
    }

    @Test
    public void testCreateCategory() {
        final String name = "name";
        final String description = "description";

        CategoryDto categoryDto = new CategoryDto(null, name, description, false, 1, false);

        categoryService.createCategory(categoryDto);

// skontrolovat ci bola vytvorena entita
        assertTrue(name == categoryDto.getName());

    }

}
