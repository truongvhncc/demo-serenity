package com.automation.steps.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseUtils.class);
    private static final String DEFAULT_URL = "cannot fetch URL";

    public DatabaseUtils() {}

    public static Connection openConnectionToADatabase(String url, String username, String password) {
        Connection connection = null;
        Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);

        try {
            connection = DriverManager.getConnection(url, properties);
        } catch (Exception e) {
            ExceptionHandlerUtils.printStackTrace("Opening connection to database " + url + "-" + username + "/" + password, e);

        }
        return connection;
    }
}
