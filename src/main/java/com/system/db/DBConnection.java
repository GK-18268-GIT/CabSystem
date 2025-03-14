package com.system.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/megacitycabservice";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Gk18268@h*421";  

    private static DBConnection instance;
    private Connection conn;

    
    private DBConnection() {
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Database connected successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database!");
            e.printStackTrace();
        }
    }

    
    public static DBConnection getInstance() {
        if (instance == null) {
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }

   
    public Connection getConnection() {
        return conn;
    }

   
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error while closing the database connection!");
            e.printStackTrace();
        }
    }
}

