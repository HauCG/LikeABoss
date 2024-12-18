package com.example.product_manager.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection connection;

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Final_Ex_Three", "ace", "A123456");
                System.out.println("Kết nối thành công!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Không tìm thấy DriverManager: " + e.getMessage());
        }
        return connection;
    }
}
