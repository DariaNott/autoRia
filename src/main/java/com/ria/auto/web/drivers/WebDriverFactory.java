package com.ria.auto.web.drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * The class {@code WebDriverFactory} represents a factory that returns an instance of WebDriver.
 *
 * @author Daria Ivanova
 */
public class WebDriverFactory {

    private WebDriverFactory() {
    }

    public static WebDriver getDriver(String webDriverName) {
        switch (webDriverName) {
            case "firefox":
                throw new UnsupportedOperationException("Firefox driver is not supported, yet!");
            case "ie":
                throw new UnsupportedOperationException("Edge driver is not supported, yet!");
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-notifications");
                return new ChromeDriver(options);
        }
    }
}
