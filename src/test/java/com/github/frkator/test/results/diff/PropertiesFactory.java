package com.github.frkator.test.results.diff;

import java.util.Optional;
import java.util.Properties;

public class PropertiesFactory {

    public Properties create() {
        Properties properties = new Properties();
        properties.setProperty("test.diff.verbose", "true");
        properties.setProperty("test.diff.output.show.common", "true");
        properties.setProperty("test.diff.output.show.left", "true");
        properties.setProperty("test.diff.output.show.right", "true");        
        return properties;
    }

    public Properties create(Boolean verbose,Boolean showCommon,Boolean showLeft,Boolean showRight,Boolean filterCommon,Boolean filterLeft,Boolean filterRight) {
        Properties properties = new Properties();
        Optional.ofNullable(verbose).ifPresent(v -> properties.setProperty("test.diff.verbose", v.toString()));
        Optional.ofNullable(showCommon).ifPresent(v -> properties.setProperty("test.diff.output.show.common", v.toString()));
        Optional.ofNullable(showLeft).ifPresent(v -> properties.setProperty("test.diff.output.show.left", v.toString()));
        Optional.ofNullable(showRight).ifPresent(v -> properties.setProperty("test.diff.output.show.right", v.toString()));
        Optional.ofNullable(filterCommon).ifPresent(v -> properties.setProperty("test.diff.output.filter.common", v.toString()));
        Optional.ofNullable(filterLeft).ifPresent(v -> properties.setProperty("test.diff.output.filter.left", v.toString()));
        Optional.ofNullable(filterRight).ifPresent(v -> properties.setProperty("test.diff.output.filter.right", v.toString()));
        return properties;
    }
}
