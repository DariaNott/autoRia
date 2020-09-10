package com.ria.auto.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The class {@code EnvConfig} is a singleton. It takes *.properties file
 * and provides environment values for the project;
 *
 * @author Daria Ivanova
 */
public class EnvConfig {

    private static EnvConfig environment;

    private String webUrl;
    private int timeoutPageLoad;
    private int implicitlyWait;

    private EnvConfig(String envName) {
        String path = System.getProperty("user.dir") + "/src/main/resources/config/"
                + envName + ".properties";
        Properties properties = loadProperties(path);
        webUrl = properties.getProperty("webUrl");
        timeoutPageLoad = Integer.parseInt(properties.getProperty("timeout.PageLoad"));
        implicitlyWait = Integer.parseInt(properties.getProperty("timeout.implicitlyWait"));
    }

    public static EnvConfig getEnvironment() {
        if (environment == null) {
            environment = new EnvConfig(System.getProperty("environment", "prod"));
        }
        return environment;
    }

    /**
     * Reads and loads .properties doc by the path
     */
    private Properties loadProperties(String path) {
        try (InputStream input = new FileInputStream(path)) {
            Properties prop = new Properties();
            prop.load(input);
            return prop;
        } catch (IOException ex) {
            throw new IllegalArgumentException("Cannot read property file by specified path!", ex);
        }
    }

    public String getWebUrl() {
        return webUrl;
    }

    public int getTimeoutPageLoad() {
        return timeoutPageLoad;
    }

    public int getImplicitlyWait() {
        return implicitlyWait;
    }
}
