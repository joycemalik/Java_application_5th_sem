// com/rental/util/DBUtil.java
package com.rental.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database utility class for managing JDBC connections
 * Supports both local (MySQL/PostgreSQL) and cloud (Supabase) databases
 * Uses environment variables for flexible deployment
 */
public class DBUtil {
    // Database connection from environment variables
    // This allows the same code to work locally and on Railway/Supabase
    private static final String URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    // Static block to load PostgreSQL JDBC driver
    static {
        try {
            // Load PostgreSQL JDBC Driver (works with Supabase)
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found. Make sure postgresql.jar is in classpath.");
            throw new RuntimeException("PostgreSQL JDBC Driver not found", e);
        }
    }

    /**
     * Get a database connection
     * @return Connection object
     * @throws SQLException if connection fails
     * @throws IllegalStateException if DB_URL environment variable is not set
     */
    public static Connection getConnection() throws SQLException {
        if (URL == null || URL.isEmpty()) {
            throw new IllegalStateException(
                "DB_URL environment variable is not set. " +
                "Please set DB_URL, DB_USER, and DB_PASSWORD environment variables."
            );
        }
        
        if (USER == null || PASSWORD == null) {
            throw new IllegalStateException(
                "DB_USER or DB_PASSWORD environment variable is not set. " +
                "Please set all required environment variables: DB_URL, DB_USER, DB_PASSWORD"
            );
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Test database connection
     * @return true if connection successful, false otherwise
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Database connection successful!");
                System.out.println("Connected to: " + conn.getMetaData().getURL());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.err.println("Configuration error: " + e.getMessage());
        }
        return false;
    }
}
