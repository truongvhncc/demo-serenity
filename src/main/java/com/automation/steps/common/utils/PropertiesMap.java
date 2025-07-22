package com.automation.steps.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class PropertiesMap extends HashMap<String, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesMap.class);

    public PropertiesMap() {
    }

    public String get(Object key) {
        String value = (String)super.get(key);
        if (value == null) {
            LOGGER.info("Property not found: {}. Check xpath.properties or labels_[language].properties file and try again!", key);
        }

        return value;
    }
}
