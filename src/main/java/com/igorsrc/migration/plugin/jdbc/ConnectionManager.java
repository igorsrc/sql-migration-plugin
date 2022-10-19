package com.igorsrc.migration.plugin.jdbc;

import com.igorsrc.migration.plugin.exception.MigrationException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {

    public static Connection getConnection(Properties properties, String url) {
        try {
            return DriverManager.getConnection(url, properties);
        } catch (SQLException e) {
            throw new MigrationException("Error establishing db connection", e);
        }
    }
}
