package com.igorsrc.migration.plugin.jdbc.driver;

import com.igorsrc.migration.plugin.exception.MigrationException;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ResolvedArtifact;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class DriverClassLoader {

    /**
     * Since JDBC driver is not available in plugins runtime we have to create our own class loader.
     * <p>
     * We do it by collecting projects artifacts urls from classpath and pass them to new class loader instance
     * constructor.
     */
    public static ClassLoader createClassLoader(Project project) {
        Configuration configuration = project.getConfigurations().getByName("runtimeClasspath");
        URL[] artifactsUrls = getArtifactsUrls(configuration);
        return new URLClassLoader(artifactsUrls, DriverClassLoader.class.getClassLoader());
    }

    /**
     * Collect project resolved artifacts URLs
     */
    private static URL[] getArtifactsUrls(Configuration configuration) {
        return configuration
                .getResolvedConfiguration()
                .getResolvedArtifacts()
                .stream()
                .map(DriverClassLoader::getArtifactUrl)
                .toArray(URL[]::new);
    }

    private static URL getArtifactUrl(ResolvedArtifact artifact) {
        try {
            return artifact.getFile().toURI().toURL();
        } catch (MalformedURLException e) {
            throw new MigrationException("Failed to get artifact URL: " + e.getMessage(), e);
        }
    }
}
