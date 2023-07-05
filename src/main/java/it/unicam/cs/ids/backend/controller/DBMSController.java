package it.unicam.cs.ids.backend.controller;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBMSController {

    //username db: matteo.morganti99@gmail.com password db: loyaltyplatform2023!
    private static final String URL = "jdbc:postgresql://tyke.db.elephantsql.com:5432/wriuymww";
    private static final String USER = "wriuymww";
    private static final String PASSWORD = "OM8NJJV8KVuOu6rSnEqkdqXhu_RDzpUF";
    private static Connection connection;
    private static final Logger logger = Logger.getLogger(DBMSController.class.getName());

    private DBMSController() {
    }

    public static void init() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            logger.info("Connected!");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Not connected", e);
        }
    }

    public static void insertQuery(String query) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing INSERT query", e);
        }
    }

    public static void removeQuery(String query) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing REMOVE query", e);
        }
    }

    public static ResultSet selectAllFromTable(String table) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table)) {
            return statement.executeQuery();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error executing SELECT query", e);
        }
        return null;
    }

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
