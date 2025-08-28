package com.solvd.taxi.connection;

import com.solvd.taxi.util.DatabaseConfig;
import com.solvd.taxi.util.ExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static ConnectionPool instance;
    private BlockingQueue<Connection> connections;
    private final int poolSize;
    private final long timeoutMillis = 5000;

    private ConnectionPool() {
        try {
            logger.debug("Initializing ConnectionPool...");

            // Get settings from DatabaseConfig
            this.poolSize = DatabaseConfig.getPoolSize();
            logger.debug("Pool size configured: {}", poolSize);

            // Check for the presence of the database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            logger.debug("MySQL JDBC driver loaded successfully");

            // Initialize the connection pool
            connections = new LinkedBlockingQueue<>(poolSize);

            // Fill the pool with connections
            initializePool();

            logger.info("Connection pool initialized successfully with {} connections", poolSize);

        } catch (ClassNotFoundException e) {
            logger.error("MySQL JDBC Driver not found. Please add it to classpath.", e);
            ExceptionHandler.handleException(e, "JDBC driver not found");
            throw new RuntimeException("MySQL JDBC Driver not found. Please add it to classpath.", e);
        } catch (SQLException e) {
            logger.error("Failed to initialize connection pool", e);
            ExceptionHandler.handleSQLException(e, "connection pool initialization");
            throw new RuntimeException("Failed to initialize connection pool", e);
        } catch (Exception e) {
            logger.error("Unexpected error during connection pool initialization", e);
            ExceptionHandler.handleException(e, "connection pool creation");
            throw new RuntimeException("Unexpected error during connection pool initialization", e);
        }
    }

    // Initializes the connection pool
    private void initializePool() throws SQLException {
        logger.debug("Initializing pool with {} connections", poolSize);

        int successfulConnections = 0;
        for (int i = 0; i < poolSize; i++) {
            try {
                Connection connection = createConnection();
                connections.offer(connection);
                successfulConnections++;
            } catch (SQLException e) {
                logger.warn("Failed to create connection {}: {}", i + 1, e.getMessage());
            }
        }

        if (successfulConnections == 0) {
            throw new SQLException("Failed to create any database connections");
        }

        logger.info("Successfully created {} out of {} connections", successfulConnections, poolSize);
    }

    // Creates a new database connection
    private Connection createConnection() throws SQLException {
        try {
            logger.debug("Creating new database connection...");

            Connection connection = DriverManager.getConnection(
                    DatabaseConfig.getUrl(),
                    DatabaseConfig.getUsername(),
                    DatabaseConfig.getPassword()
            );

            // Check that the connection is really working
            if (connection.isValid(2)) {
                logger.debug("Database connection created and validated successfully");
                return connection;
            } else {
                logger.error("Created invalid database connection");
                throw new SQLException("Created invalid connection");
            }

        } catch (SQLException e) {
            logger.error("Failed to create database connection: {}", e.getMessage());
            ExceptionHandler.handleSQLException(e, "create connection");
            throw e;
        }
    }

    // Returns a single instance of ConnectionPool (Singleton pattern)
    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            logger.debug("Creating new ConnectionPool instance");
            instance = new ConnectionPool();
        } else {
            logger.debug("Returning existing ConnectionPool instance");
        }
        return instance;
    }

    // Gets a connection from the pool
    public Connection getConnection() throws InterruptedException {
        logger.debug("Requesting connection from pool. Available: {}", getAvailableConnectionsCount());

        try {
            Connection connection = connections.poll(timeoutMillis, TimeUnit.MILLISECONDS);

            if (connection == null) {
                logger.warn("Timeout waiting for connection from pool after {} ms", timeoutMillis);
                throw new RuntimeException("Timeout waiting for connection from pool");
            }

            // Check if the connection is still valid
            if (connection.isClosed() || !isConnectionValid(connection)) {
                logger.warn("Connection from pool was invalid, creating new one");
                connection = createConnection();
            }

            logger.debug("Connection obtained successfully. Remaining available: {}", getAvailableConnectionsCount());
            return connection;

        } catch (SQLException e) {
            logger.error("Failed to get valid connection from pool: {}", e.getMessage());
            ExceptionHandler.handleSQLException(e, "get connection from pool");
            throw new RuntimeException("Failed to get valid connection from pool", e);
        }
    }

    private boolean isConnectionValid(Connection connection) {
        try {
            return connection != null && connection.isValid(2);
        } catch (SQLException e) {
            logger.warn("Connection validation failed: {}", e.getMessage());
            return false;
        }
    }

    // Returns the connection back to the pool
    public void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                // Check if the connection is still valid before returning to the pool
                if (!connection.isClosed() && connection.isValid(2)) {
                    // Reset auto-commit in case it has been changed
                    connection.setAutoCommit(true);
                    connections.offer(connection);
                    logger.debug("Connection released back to pool. Available: {}", getAvailableConnectionsCount());
                } else {
                    logger.warn("Connection was invalid, creating replacement");
                    // If the connection is invalid, create a new one and add it to the pool
                    connections.offer(createConnection());
                }
            } catch (SQLException e) {
                logger.error("Error while releasing connection: {}", e.getMessage());
                ExceptionHandler.handleSQLException(e, "release connection");
                // In case of error, create a new connection to maintain the pool size
                try {
                    connections.offer(createConnection());
                    logger.info("Created replacement connection after release error");
                } catch (SQLException ex) {
                    logger.error("Failed to create replacement connection: {}", ex.getMessage());
                    ExceptionHandler.handleSQLException(ex, "create replacement connection");
                }
            }
        } else {
            logger.warn("Attempted to release null connection");
        }
    }

    // Returns the current pool size (number of available connections)
    public int getAvailableConnectionsCount() {
        int count = connections.size();
        logger.trace("Available connections count: {}", count);
        return count;
    }

    // Closes all connections in the pool
    public void closeAllConnections() {
        logger.info("Closing all connections in pool...");

        int closedCount = 0;
        Connection connection;
        while ((connection = connections.poll()) != null) {
            if (closeConnectionSilently(connection)) {
                closedCount++;
            }
        }

        logger.info("Closed {} connections. Pool is now empty.", closedCount);
    }

    // Safely closes connections without throwing exceptions
    private boolean closeConnectionSilently(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    logger.debug("Connection closed successfully");
                    return true;
                }
            } catch (SQLException e) {
                logger.warn("Error closing connection: {}", e.getMessage());
                ExceptionHandler.handleSQLException(e, "close connection silently");
            }
        }
        return false;
    }

    // Checks the connection pool status
    public boolean isPoolHealthy() {
        try (Connection testConn = createConnection()) {
            return testConn.isValid(2);
        } catch (SQLException e) {
            logger.warn("Pool health check failed: {}", e.getMessage());
            return false;
        }
    }

    // Reloads the connection pool (useful when changing the configuration)
    public synchronized void reloadPool() {
        logger.info("Reloading connection pool...");

        // Close old connections
        closeAllConnections();

        // Reset instance to create a new pool
        instance = null;

        // Create a new instance
        getInstance();

        logger.info("Connection pool reloaded successfully");
    }

    /**
     * Method to explicitly close the pool when the application terminates
     * Call in shutdown hook or on shutdown
     */
    public static void shutdown() {
        logger.info("Shutting down connection pool...");

        if (instance != null) {
            instance.closeAllConnections();
            instance = null;
            logger.info("Connection pool shutdown completed");
        } else {
            logger.info("Connection pool was already shut down");
        }
    }

    // Additional method for monitoring pool health
    public String getPoolStatus() {
        return String.format("Pool status: %d/%d connections available",
                getAvailableConnectionsCount(), poolSize);
    }
}