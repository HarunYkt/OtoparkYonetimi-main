package org.otopark.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {


    private static DatabaseConnection instance;
    private final Connection connection;

    //sigleton pattern
    private DatabaseConnection() throws SQLException {

        try {
            String url = "jdbc:mysql://localhost:3306/otopark_db";
            String username = "root";
            String password = "localhost";
            connection = java.sql.DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            throw new SQLException("Veritabani baglantisi yapilamadi", ex);
        }
    }

    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
