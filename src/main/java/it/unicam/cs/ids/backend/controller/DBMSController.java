package it.unicam.cs.ids.backend.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBMSController {

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/LoyaltyPlatform";
        String user = "pogres";
        String password = "password";
        return DriverManager.getConnection(url, user, password);
    }
}
