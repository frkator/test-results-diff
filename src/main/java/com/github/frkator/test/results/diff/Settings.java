package com.github.frkator.test.results.diff;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Settings {

    private final Properties properties;

    public Settings(Properties properties) {
        this.properties = properties;
    }

    public Settings() {
        try {
            properties = new Properties();
            properties.load(getClass().getResourceAsStream("default.properties"));
            if (Files.exists(Paths.get("./default.properties"))) {
                properties.load(new FileInputStream("./default.properties"));
            }
        } catch (Exception e) {
            throw new RuntimeException("error loading properties", e);
        }
    }

    private Boolean load(String key) {
        if (System.getProperties().containsKey(key)) {
            return Boolean.getBoolean(key);
        } else {
            return Boolean.parseBoolean(properties.getProperty(key));
        }
    }

    public boolean isShowCommon() {
        return load("test.diff.output.show.common");
    }

    public boolean isShowLeft() {
        return load("test.diff.output.show.left");
    }

    public boolean isShowRight() {
        return load("test.diff.output.show.right");
    }

    private boolean isUndefined(String key) {
        return !System.getProperties().containsKey(key) && !properties.containsKey(key);
    }

    public boolean isStatusFilteringLeft() {
        return !isUndefined("test.diff.output.filter.left");
    }

    public boolean isStatusFilteringRight() {
        return !isUndefined("test.diff.output.filter.right");
    }

    public boolean isStatusFilteringCommon() {
        return !isUndefined("test.diff.output.filter.common");
    }

    public boolean getLeftStatusFilter() {
        return load("test.diff.output.filter.left");
    }

    public boolean getRightStatusFilter() {
        return load("test.diff.output.filter.right");
    }

    public boolean getCommonStatusFilter() {
        return load("test.diff.output.filter.common");
    }
}
