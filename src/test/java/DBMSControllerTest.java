import it.unicam.cs.ids.backend.controller.DBMSController;
import org.junit.jupiter.api.*;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class DBMSControllerTest {

    private static final String TABLE_NAME = "test_table";

    @BeforeAll
    public static void setup() {
        DBMSController.init();
        createTestTable();
    }

    @AfterAll
    public static void cleanup() {
        dropTestTable();
    }

    @BeforeEach
    public void beforeEachTest() throws SQLException {
        clearTestTable();
    }

    @Test
    public void testInsertQuery() throws SQLException {
        String query = "INSERT INTO " + TABLE_NAME + " VALUES (1, 'Test')";
        DBMSController.insertQuery(query);
        int rowCount = getRowCount(TABLE_NAME);
        assertEquals(1, rowCount);
    }

    @Test
    public void testRemoveQuery() throws SQLException {
        insertTestData();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE id = 1";
        DBMSController.removeQuery(query);
        int rowCount = getRowCount(TABLE_NAME);
        assertEquals(0, rowCount);
    }

    @Test
    public void testSelectAllFromTable() throws SQLException {
        insertTestData();
        ResultSet resultSet = DBMSController.selectAllFromTable(TABLE_NAME);
        assertNotNull(resultSet);
        assertTrue(resultSet.next());
        assertEquals(1, resultSet.getInt("id"));
        assertEquals("Test", resultSet.getString("name"));
        assertFalse(resultSet.next());
    }

    @Test
    public void testGetNumberRows() throws SQLException {
        insertTestData();
        String query = "SELECT * FROM " + TABLE_NAME;
        int rowCount = DBMSController.getNumberRows(query);
        assertEquals(1, rowCount);
    }

    private static void createTestTable() {
        String query = "CREATE TABLE " + TABLE_NAME + " (id INT, name VARCHAR(255))";
        try {
            DBMSController.insertQuery(query);
        } catch (SQLException e) {
            fail("Failed to create test table");
        }
    }

    private static void dropTestTable() {
        String query = "DROP TABLE " + TABLE_NAME;
        try {
            DBMSController.removeQuery(query);
        } catch (SQLException e) {
            fail("Failed to drop test table");
        }
    }

    private void clearTestTable() throws SQLException {
        String query = "DELETE FROM " + TABLE_NAME;
        DBMSController.removeQuery(query);
    }

    private void insertTestData() throws SQLException {
        String query = "INSERT INTO " + TABLE_NAME + " VALUES (1, 'Test')";
        DBMSController.insertQuery(query);
    }

    private int getRowCount(String table) throws SQLException {
        String query = "SELECT COUNT(*) FROM " + table;
        ResultSet resultSet = DBMSController.selectAllFromTable(table);
        resultSet.next();
        return resultSet.getInt(1);
    }
}