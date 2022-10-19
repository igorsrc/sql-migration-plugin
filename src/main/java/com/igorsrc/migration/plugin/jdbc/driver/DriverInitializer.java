package com.igorsrc.migration.plugin.jdbc.driver;

import com.igorsrc.migration.plugin.exception.MigrationException;
import org.gradle.api.Project;

import java.lang.reflect.InvocationTargetException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverInitializer {

    public static void initDriver(Project project, String driverClassName) {
        registerDriver(
                getDriverInstance(
                        driverClassName, DriverClassLoader.createClassLoader(project)
                )
        );
    }

    private static Driver getDriverInstance(String driverClassName, ClassLoader driverClassLoader) {
        try {
            Class<?> driverClass = Class.forName(driverClassName, true, driverClassLoader);
            return (Driver) driverClass.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new MigrationException("Failed to initialize driver instance for class: " + driverClassName, e);
        }
    }

    private static void registerDriver(Driver driver) {
        try {
            DriverManager.registerDriver(new DriverDelegate(driver));
        } catch (SQLException e) {
            throw new MigrationException("Failed to register driver for class " + driver.getClass().getSimpleName(), e);
        }
    }
}
