package com.solvd.taxi.util;

import java.sql.SQLException;

import com.solvd.taxi.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExceptionHandler {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void handleSQLException(SQLException e, String operation) {
        logger.warn("SQL Error during " + operation + ": " + e.getMessage());
    }

    public static void handleException(Exception e, String context) {
        logger.warn("Error in " + context + ": " + e.getMessage());
        e.printStackTrace();
    }

    public static String getFriendlyErrorMessage(SQLException e) {
        switch (e.getSQLState()) {
            case "23000": // Integrity constraint violation
                return "Operation failed: data integrity constraint violated";
            case "08000": // Connection exception
                return "Database connection error. Please try again later.";
            case "22003": // Numeric value out of range
                return "Invalid numeric value provided";
            default:
                return "An unexpected database error occurred";
        }
    }
}