package com.igorsrc.migration.plugin.task;

import com.igorsrc.migration.plugin.extension.PluginProperties;
import com.igorsrc.migration.plugin.extension.PluginPropertiesValidator;
import com.igorsrc.migration.plugin.jdbc.ConnectionManager;
import com.igorsrc.migration.plugin.jdbc.QueryExecutor;
import com.igorsrc.migration.plugin.jdbc.driver.DriverInitializer;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.sql.Connection;
import java.util.Optional;
import java.util.Properties;

import static com.igorsrc.migration.plugin.utils.DriverClassNameUtils.getDriverClassName;

public abstract class MigrationMainTask extends DefaultTask {

    @TaskAction
    public void run() {
        PluginProperties properties = getProject().getExtensions().findByType(PluginProperties.class);
        PluginPropertiesValidator.validate(properties);
        String driverClassName = Optional.ofNullable(properties.getDriverClassName()).orElse(getDriverClassName(properties.getUrl()));
        DriverInitializer.initDriver(getProject(), driverClassName);

        Properties connectionProperties = new Properties();
        connectionProperties.put("user", properties.getUsername());
        connectionProperties.put("password", properties.getPassword());
        Connection connection = ConnectionManager.getConnection(connectionProperties, properties.getUrl());

        QueryExecutor.execute(connection, "select 1");
    }
}