package it.unicam.cs.ids.backend.controller;

import it.unicam.cs.ids.backend.util.ConsoleLog;

import java.sql.*;

/**
 * The DBMSController class handles the connection and operations with the database.
 */
public class DBMSController {

    private static final String URL = "jdbc:postgresql://tyke.db.elephantsql.com:5432/wriuymww";
    private static final String USER = "wriuymww";
    private static final String PASSWORD = "OM8NJJV8KVuOu6rSnEqkdqXhu_RDzpUF";
    private static Connection connection;

    private DBMSController() {
    }

    /**
     * Initializes the database connection.
     */
    public static void init() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            ConsoleLog.log("Connected!");
        } catch (SQLException e) {
            ConsoleLog.error("Not connected");
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
            ConsoleLog.error("Error executing INSERT query");
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
            ConsoleLog.error("Error executing REMOVE query");
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
            ConsoleLog.error("Error executing SELECT query");
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
            ConsoleLog.error("Error getting row count");
        }
        return 0;
    }
}
