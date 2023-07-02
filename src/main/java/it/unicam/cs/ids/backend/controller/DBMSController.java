package it.unicam.cs.ids.backend.controller;

import java.sql.*;

public class DBMSController {

    private static String url = "jdbc:postgresql://localhost:5432/LoyaltyPlatform";
    private static String user = "postgres";
    private static String password = "password";
    private static Connection connection;


    public static void init() {
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected!");

        } catch (SQLException e) {
            throw new IllegalStateException("Not connected", e);
        }
    }
    public static void insertQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    public static void removeQuery(String query) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeUpdate();
    }

    public static ResultSet selectAllFromTable(String table) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table);
        return statement.executeQuery();
    }

    public static int getNumberRows(String query) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(query);
            if (resultset.last()) {
                return resultset.getRow();
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error getting row count");
            e.printStackTrace();
        }
        return 0;
    }
}