import it.unicam.cs.ids.backend.controller.DBMSController;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DBMSControllerTest {

    @BeforeClass
    public static void setUp() {
        DBMSController.init();
    }

    @AfterClass
    public static void tearDown() {
        // Perform any necessary cleanup after the tests
        // For example, closing the database connection
    }

    @Test
    public void testInsertQuery() {
        // Prepare test data
        String query = "INSERT INTO table_name (column1, column2) VALUES ('value1', 'value2')";

        // Execute the insert query
        DBMSController.insertQuery(query);

        // Assert that the query was successful by checking the number of affected rows
        int affectedRows = DBMSController.getNumberRows("SELECT COUNT(*) FROM table_name");
        assertEquals(1, affectedRows);
    }

    @Test
    public void testRemoveQuery() {
        // Prepare test data
        String query = "DELETE FROM table_name WHERE condition = 'value'";

        // Execute the remove query
        DBMSController.removeQuery(query);

        // Assert that the query was successful by checking the number of affected rows
        int affectedRows = DBMSController.getNumberRows("SELECT COUNT(*) FROM table_name WHERE condition = 'value'");
        assertEquals(0, affectedRows);
    }

    @Test
    public void testSelectAllFromTable() {
        // Prepare test data
        String table = "table_name";

        // Execute the select query
        ResultSet resultSet = DBMSController.selectAllFromTable(table);

        // Assert that the result set is not null
        assertNotNull(resultSet);
        // Additional assertions on the result set can be performed if needed
    }

    @Test
    public void testGetNumberRows() {
        // Prepare test data
        String query = "SELECT * FROM table_name";

        // Execute the select query and get the number of rows
        int rowCount = DBMSController.getNumberRows(query);

        // Assert that the row count is as expected
        assertEquals(5, rowCount);
    }
}