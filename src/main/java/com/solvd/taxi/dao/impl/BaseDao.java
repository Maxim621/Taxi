package com.solvd.taxi.dao.impl;

import com.solvd.taxi.connection.ConnectionPool;
import com.solvd.taxi.util.ExceptionHandler;

import java.sql.*;

public abstract class BaseDao {
    protected final ConnectionPool connectionPool;

    protected BaseDao() {
        this.connectionPool = ConnectionPool.getInstance();
    }

    protected Connection getConnection() throws SQLException {
        try {
            return connectionPool.getConnection();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SQLException("Interrupted while getting connection", e);
        }
    }

    protected void releaseConnection(Connection connection) {
        if (connection != null) {
            connectionPool.releaseConnection(connection);
        }
    }

    protected void closeResources(ResultSet resultSet, Statement statement) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "closing resources");
        }
    }

    protected void closeResources(Statement statement) {
        try {
            if (statement != null) statement.close();
        } catch (SQLException e) {
            ExceptionHandler.handleSQLException(e, "closing statement");
        }
    }

    protected void setNullableInt(PreparedStatement statement, int parameterIndex, Integer value) throws SQLException {
        if (value != null) {
            statement.setInt(parameterIndex, value);
        } else {
            statement.setNull(parameterIndex, Types.INTEGER);
        }
    }

    protected void setNullableDouble(PreparedStatement statement, int parameterIndex, Double value) throws SQLException {
        if (value != null) {
            statement.setDouble(parameterIndex, value);
        } else {
            statement.setNull(parameterIndex, Types.DOUBLE);
        }
    }

    protected void setNullableString(PreparedStatement statement, int parameterIndex, String value) throws SQLException {
        if (value != null) {
            statement.setString(parameterIndex, value);
        } else {
            statement.setNull(parameterIndex, Types.VARCHAR);
        }
    }

    protected void setNullableTimestamp(PreparedStatement statement, int parameterIndex, Timestamp value) throws SQLException {
        if (value != null) {
            statement.setTimestamp(parameterIndex, value);
        } else {
            statement.setNull(parameterIndex, Types.TIMESTAMP);
        }
    }
}