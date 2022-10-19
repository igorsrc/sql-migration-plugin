package com.igorsrc.migration.plugin;

import com.igorsrc.migration.plugin.extension.PluginProperties;
import com.igorsrc.migration.plugin.task.MigrationMainTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class PluginMain implements Plugin<Project> {

    @Override
    public void apply(Project project) {

        project
                .getExtensions()
                .add("sqlMigration", PluginProperties.class);

        project
                .getTasks()
                .register("runMigration", MigrationMainTask.class);
    }
}