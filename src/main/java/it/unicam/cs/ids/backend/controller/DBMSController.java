package it.unicam.cs.ids.backend.controller;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The DBMSController class handles the connection and operations with the database.
 */
public class DBMSController {

    private static final String URL = "jdbc:postgresql://tyke.db.elephantsql.com:5432/wriuymww";
    private static final String USER = "wriuymww";
    private static final String PASSWORD = "OM8NJJV8KVuOu6rSnEqkdqXhu_RDzpUF";
    private static Connection connection;
    private static final Logger logger = Logger.getLogger(DBMSController.class.getName());

    private DBMSController() {
    }

    /**
     * Initializes the database connection.
     */
    public static void init() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            logger.info("Connected!");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Not connected", e);
        }
    }

    /**
     * Executes an INSERT query.
     *
     * @param query the INSERT query to execute
     */
    public static void insertQuery(String query) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing INSERT query", e);
        }
    }

    /**
     * Executes a REMOVE query.
     *
     * @param query the REMOVE query to execute
     */
    public static void removeQuery(String query) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing REMOVE query", e);
        }
    }

    /**
     * Executes a SELECT query and returns the result set.
     *
     * @param table the table to select from
     * @return the result set of the SELECT query
     */
    public static ResultSet selectAllFromTable(String table) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table)) {
            return statement.executeQuery();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing SELECT query", e);
        }
        return null;
    }

    /**
     * Retrieves the number of rows returned by a query.
     *
     * @param query the query to execute
     * @return the number of rows returned by the query
     */
    public static int getNumberRows(String query) {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.last()) {
                return resultSet.getRow();
            } else {
                return 0;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error getting row count", e);
        }
        return 0;
    }
}
