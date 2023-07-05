package it.unicam.cs.ids.backend.controller;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBMSController {

    private static final String URL = "jdbc:postgresql://localhost:5432/LoyaltyPlatform";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";
    private static Connection connection;

    private static final Logger logger = Logger.getLogger(DBMSController.class.getName());

    private DBMSController() {
    }

    public static void init() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            logger.info("Connected!");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Not connected", e);
        }
    }
    public static void insertQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    public static void removeQuery(String query) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }

    public static ResultSet selectAllFromTable(String table) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table)) {
            return statement.executeQuery();
        }
    }

    public static int getNumberRows(String query) {
        try (Statement statement = connection.createStatement();
             ResultSet resultset = statement.executeQuery(query)) {
            if (resultset.last()) {
                return resultset.getRow();
            } else {
                return 0;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error getting row count", e);
            e.printStackTrace();
        }
        return 0;
    }
}