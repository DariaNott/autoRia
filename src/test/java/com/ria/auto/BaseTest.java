package com.ria.auto;

import com.ria.auto.config.EnvConfig;
import com.ria.auto.web.drivers.WebDriverFactory;
import com.ria.auto.web.pages.SearchPage;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    private static final Logger LOGGER = Logger.getLogger(BaseTest.class.getName());
    protected WebDriver driver;
    protected SearchPage searchPage;

    protected String typeLightCar = "Легкові";
    protected String brandToyota = "Toyota";

    @BeforeClass
    void setupChrome() {
        driver = WebDriverFactory.getDriver(System.getProperty("browser", "chrome"));
        driver.manage().timeouts().pageLoadTimeout(EnvConfig.getEnvironment().getTimeoutPageLoad(), TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(EnvConfig.getEnvironment().getImplicitlyWait(), TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @AfterClass
    void cleanUp() {
        if (driver != null) {
            driver.quit();
        }
        LOGGER.info("Driver closed.");
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }
}
