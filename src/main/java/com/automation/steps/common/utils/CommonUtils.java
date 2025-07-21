package com.automation.steps.common.utils;

import io.cucumber.java.it.Ma;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.*;

public class CommonUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);
    public static final Map<String,String> variableProperty = readVariableProperties();
    private static Properties systemProperties;

    public CommonUtils() {
    }

    public static Map<String,String> readVariableProperties() {
        String envFileViaMvn = getSystemProperty("envFile");
        InputStream inputStreamForEnv;
        if (envFileViaMvn != null && !envFileViaMvn.isEmpty() && !envFileViaMvn.equals("${test.envFile}")) {
            File envFilePath = new File(envFileViaMvn);
            try {
                inputStreamForEnv = new FileInputStream(envFilePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return new HashMap<>();
            }
        } else {
            String envViaMvn = getSystemProperty("env");
            if (envViaMvn != null && !envViaMvn.isEmpty() && !envViaMvn.equals("${test.env}")) {
                String evn = "environment." + envViaMvn + ".properties";
                inputStreamForEnv = Thread.currentThread().getContextClassLoader().getResourceAsStream(evn);
            } else {
                LOGGER.warn("CommonUtils: test.evn is not specific");
                inputStreamForEnv = null;
            }
        }

        InputStream inputStreamForVar = getVariablePropertiesFilesAsInputStream();
        InputStream inputStream;
        if (inputStreamForEnv != null) {
            inputStream = new SequenceInputStream(inputStreamForEnv, new ByteArrayInputStream("\n".getBytes()));
            inputStream = new SequenceInputStream(inputStream, inputStreamForVar);
        } else {
            inputStream = inputStreamForVar;
        }

        try {
            return loadPropertiesMap(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            ExceptionHandlerUtils.printStackTrace("Read variable properties", e);
            return new HashMap<>();
        }
    }

    public static String getSystemProperty(String key) {
        if (systemProperties == null) {
            systemProperties = System.getProperties();
            InputStream demoClientInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("democlient_resources.properties");
            Properties demoClientProperty = new Properties();
            try {
                if (demoClientInputStream != null) {
                    demoClientProperty.load(demoClientInputStream);
                    for (String keyInPom : demoClientProperty.stringPropertyNames()) {
                        if (!systemProperties.contains(keyInPom)) {
                            systemProperties.setProperty(keyInPom, demoClientProperty.getProperty(keyInPom));
                        }
                    }
                } else {
                    LOGGER.warn("democlient_resources.properties is not found");
                }
            } catch (Exception e) {
                e.printStackTrace();
                ExceptionHandlerUtils.printStackTrace(" Get system property " + key, e);
                return null;
            }
        }

        return systemProperties.getProperty(key);
    }

    public static InputStream getVariablePropertiesFilesAsInputStream() {
        List<InputStream> inputStreams = new ArrayList<>();

        try {
            URL resource = Thread.currentThread().getContextClassLoader().getResource("data/variables");
            if (resource != null) {
                File directory = new File(resource.toURI());
                File[] files = directory.listFiles((dir, name) -> name.startsWith("variable") && name.endsWith(".properties"));
                if (files != null) {
                    for (File file : files) {
                        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("data/variables/" + file.getName());
                        if (inputStream != null) {
                            inputStreams.add(inputStream);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionHandlerUtils.printStackTrace("Read variable properties file inf ", e);
        }
        return new SequenceInputStream(Collections.enumeration(inputStreams));
    }

    public static Map<String, String> loadPropertiesMap(InputStream inputStream) throws IOException {
        final Map<String, String> ordered = new LinkedHashMap();
        (new Properties() {
            public synchronized Object put(Object key, Object value) {
                if (!ordered.containsKey(key)) {
                    ordered.put((String)key, (String)value);
                }

                return super.put(key, value);
            }
        }).load(inputStream);
        Map<String, String> resolved = new LinkedHashMap(ordered);
        StringSubstitutor sub = new StringSubstitutor((keyx) -> {
            String value = (String)resolved.get(keyx);
            return value == null ? System.getProperty(keyx) : value;
        });

        for(Map.Entry<String, String> entry : ordered.entrySet()) {
            String key = (String)entry.getKey();
            String value = sub.replace((String)entry.getValue());
            String systemKey = getSystemProperty(key);
            resolved.put(key, systemKey != null && !systemKey.isEmpty() && !systemKey.equals("${test." + key + "}") ? systemKey : value);
        }

        return resolved;
    }
}
