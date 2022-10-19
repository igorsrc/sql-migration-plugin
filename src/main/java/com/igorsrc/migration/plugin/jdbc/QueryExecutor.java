package com.igorsrc.migration.plugin.jdbc;

import com.igorsrc.migration.plugin.exception.MigrationException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryExecutor {

    public static void execute(Connection connection, String query) {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException e) {
            throw new MigrationException("Error executing query", e);
        }
    }
}
