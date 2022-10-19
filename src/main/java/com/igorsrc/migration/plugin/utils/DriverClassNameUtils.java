package com.igorsrc.migration.plugin.utils;

import com.igorsrc.migration.plugin.exception.MigrationException;
import com.igorsrc.migration.plugin.exception.UnknownDriverException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DriverClassNameUtils {

    private static String DRIVER_PROPERTIES_PATH = "./driver.properties";

    public static String getDriverClassName(String url) {

        try (InputStream input = new FileInputStream(DRIVER_PROPERTIES_PATH)) {
            Properties prop = new Properties();
            prop.load(input);

            String driverClassName = (String) prop.get(url.split(":")[2]);
            if (driverClassName == null) {
                throw new UnknownDriverException("Missing driverClassName for url: " + url);
            }
            return driverClassName;
        }
        catch (IOException e) {
            throw new MigrationException("Error reading from " + DRIVER_PROPERTIES_PATH, e);
        }
    }
}
