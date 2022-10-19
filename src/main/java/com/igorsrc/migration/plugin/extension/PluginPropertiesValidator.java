package com.igorsrc.migration.plugin.extension;

import com.igorsrc.migration.plugin.exception.MigrationException;
import com.igorsrc.migration.plugin.exception.PropertyValidationException;

public class PluginPropertiesValidator {

    private PluginPropertiesValidator() {
    }

    public static void validate(PluginProperties properties) {

        if (properties == null) {
            throw new MigrationException("missing plugin extension");
        }

        if (properties.getUsername() == null || properties.getUsername().isBlank()) {
            throw new PropertyValidationException("username", properties.getUsername());
        }

        if (properties.getPassword() == null || properties.getPassword().isBlank()) {
            throw new PropertyValidationException("password", properties.getPassword());
        }

        if (properties.getUrl() == null
                ||  properties.getUrl().isBlank()
                || !properties.getUrl().matches("^(jdbc):([a-zA-Z0-9]*):(//{1,2}|@)[a-zA-Z-]{1,253}.*")) {
            throw new PropertyValidationException("url", properties.getUrl());
        }

        // driver class name is optional
        if (properties.getDriverClassName() != null && properties.getDriverClassName().isBlank()) {
            throw new PropertyValidationException("driverClassName", properties.getDriverClassName());
        }
    }
}
