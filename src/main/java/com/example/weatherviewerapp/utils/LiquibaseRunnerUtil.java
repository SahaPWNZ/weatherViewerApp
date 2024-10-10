package com.example.weatherviewerapp.utils;

import liquibase.Scope;
import liquibase.command.CommandScope;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LiquibaseRunnerUtil {
    public void runLiquibase() {
        log.info("Running Liquibase...");
        try {
            Scope.child(Scope.Attr.resourceAccessor, new ClassLoaderResourceAccessor(), () -> {
                CommandScope update = new CommandScope("update");
                update.addArgumentValue("changelogFile", "db/changelog.xml");
                update.addArgumentValue("url", "jdbc:postgresql://postgres:5432/weather_app_db");
//                update.addArgumentValue("url", "jdbc:postgresql://localhost:5432/weather_app_db");
                update.addArgumentValue("username", "saha");
                update.addArgumentValue("password", "saha");
                update.execute();
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        log.info("Running Liquibase...DONE");
    }
}
