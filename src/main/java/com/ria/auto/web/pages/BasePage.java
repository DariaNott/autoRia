package com.ria.auto.web.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    protected static final Logger LOGGER = Logger.getLogger(BasePage.class.getName());
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected void waitUntilClickable(WebElement element) {
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void click(By by) {
        waitUntilClickable(driver.findElement(by));
        driver.findElement(by).click();
    }

    protected void inputValueByXpath(String xpath, String value) {
        driver.findElement(By.xpath(xpath)).sendKeys(value);
    }

    protected void scrollInView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    protected void scrollInView(By by) {
        scrollInView(driver.findElement(by));
    }
}
