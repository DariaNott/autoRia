package com.ria.auto.web.pages;

import com.ria.auto.entity.CarItem;
import com.ria.auto.entity.Currency;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class SearchPage extends BasePage {

    @FindBy(css = "#scrollUpLink")
    private WebElement scrollUpButton;

    @FindBy(xpath = "//label[@for='indexName_bu']")
    private WebElement usedRadioButton;

    @FindBy(xpath = "//label[@for='verifiedVIN']")
    private WebElement verifiedVinCheckbox;

    @FindBy(css = "#category")
    private WebElement categoryTypeDropdown;

    @FindBy(css = "#brandTooltipBrandAutocompleteInput-0")
    private WebElement categoryBandDropdown;

    @FindBy(xpath = "//select[contains(@name,'price.currency')]/..")
    private WebElement priceCurrencyDropdown;


    @FindBy(xpath = "//div[@id='priceBlockOptions']//div")
    private WebElement priceAuctionCheckboxbutton;

    @FindBy(xpath = "//div[@id='priceBlockOptions']//input")
    private WebElement priceAuctionCheckbox;

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click on Scroll Up button")
    public SearchPage clickOnScrollUpButton() {
        LOGGER.info("Scrolling up the page.");
        waitUntilClickable(scrollUpButton);
        scrollUpButton.click();
        return this;
    }

    @Step("Select Used filter")
    public SearchPage selectUsedFilter() {
        LOGGER.info("Selecting 'Used' filter.");
        waitUntilClickable(usedRadioButton);
        usedRadioButton.click();
        waitForSearchProgressBarToBeLoaded();
        return this;
    }

    @Step("Click on Verified VIN checkbox")
    public SearchPage clickOnVerifiedVinCheckbox() {
        LOGGER.info("Clicking on 'Verified VIN' checkbox.");
        waitUntilClickable(verifiedVinCheckbox);
        verifiedVinCheckbox.click();
        waitForSearchProgressBarToBeLoaded();
        return this;
    }

    @Step("Choose Vehicle Type")
    public SearchPage chooseVehicleType(String type) {
        LOGGER.info("Opening Vehicle Type dropdown.");
        waitUntilClickable(categoryTypeDropdown);
        categoryTypeDropdown.click();
        LOGGER.info("Selecting '" + type + "' from the dropdown.");
        click(By.xpath("//select[@id='category']/option[contains(text(),'" + type + "')]"));
        waitForSearchProgressBarToBeLoaded();
        return this;
    }

    @Step("Choose Vehicle Brand")
    public SearchPage chooseVehicleBrand(String brand) {
        LOGGER.info("Scrolling the page to the Brand filter.");
        scrollInView(categoryBandDropdown);
        LOGGER.info("Typing '" + brand + "' into the input field.");
        waitUntilClickable(categoryBandDropdown);
        categoryBandDropdown.sendKeys(brand);
        LOGGER.info("Choosing '" + brand + "' from the dropdown.");
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//li[@data-text='" + brand + "']")));
        click(By.xpath("//li[@data-text='" + brand + "']"));
        waitForSearchProgressBarToBeLoaded();
        return this;
    }

    @Step("Set price 'From' value")
    public SearchPage setPriceFromValue(String fromValue) {
        LOGGER.info("Scrolling the page to the Price filter.");
        String currency = getPriceCurrencyValue().name();
        scrollInView(By.xpath("//input[contains(@name,'price." + currency + ".gte')]"));
        LOGGER.info("Typing '" + fromValue + "' into 'From' field");
        inputValueByXpath("//input[contains(@name,'price." + currency + ".gte')]", fromValue);
        clickOnScrollUpButton();
        waitForSearchProgressBarToBeLoaded();
        return this;
    }

    @Step("Get price 'From' value")
    public String getPriceFromValue() {
        LOGGER.info("Getting 'From' field value.");
        String currency = getPriceCurrencyValue().name();
        String fromValue = driver.findElement(By.xpath("//input[contains(@name,'price." + currency + ".gte')]")).getAttribute("value");
        return fromValue;
    }

    @Step("Clear price 'From' value")
    public SearchPage clearPriceFromValue() {
        LOGGER.info("Scrolling the page to the Price filter.");
        String currency = getPriceCurrencyValue().name();
        scrollInView(By.xpath("//input[contains(@name,'price." + currency + ".gte')]"));
        LOGGER.info("Clearing 'From' field.");
        driver.findElement(By.xpath("//input[contains(@name,'price." + currency + ".gte')]")).clear();
        clickOnScrollUpButton();
        waitForSearchProgressBarToBeLoaded();
        return this;
    }

    @Step("Set price 'To' value")
    public SearchPage setPriceToValue(String toValue) {
        String currency = scrollToPriceFilter();
        LOGGER.info("Typing '" + toValue + "' into 'To' field");
        inputValueByXpath("//input[contains(@name,'price." + currency + ".lte')]", toValue);
        clickOnScrollUpButton();
        waitForSearchProgressBarToBeLoaded();
        return this;
    }

    @Step("Get price 'To' value")
    public String getPriceToValue() {
        LOGGER.info("Getting 'To' field value.");
        String currency = getPriceCurrencyValue().name();
        String toValue = driver.findElement(By.xpath("//input[contains(@name,'price." + currency + ".lte')]")).getAttribute("value");
        return toValue;
    }

    @Step("Clear price 'To' value")
    public SearchPage clearPriceToValue() {
        String currency = scrollToPriceFilter();
        LOGGER.info("Clearing 'To' field.");
        driver.findElement(By.xpath("//input[contains(@name,'price." + currency + ".lte')]")).clear();
        clickOnScrollUpButton();
        waitForSearchProgressBarToBeLoaded();
        return this;
    }

    @Step("Choose price currency")
    public SearchPage choosePriceCurrency(Currency currency) {
        String currencySymbol = currency.getSymbol();
        scrollToPriceFilter();
        LOGGER.info("Opening Currency dropdown.");
        priceCurrencyDropdown.click();
        LOGGER.info("Choosing " + currencySymbol + " currency.");
        WebElement currencyCell = driver.findElement(By.xpath("//select[contains(@name,'price.currency')]/*[text()='" + currencySymbol + "']"));
        currencyCell.click();
        clickOnScrollUpButton();
        waitForSearchProgressBarToBeLoaded();
        return this;
    }

    @Step("Get price currency value")
    public Currency getPriceCurrencyValue() {
        LOGGER.info("Getting Price currency.");
        String value = driver.findElement(By.xpath("//select[contains(@name,'price.currency')]")).getAttribute("value");
        String currencySymbol = driver.findElement(By.xpath("//select[contains(@name,'price.currency')]/option[@value='" + value + "']"))
                .getText().trim();
        return Currency.getCurrency(currencySymbol);
    }

    @Step("Click on Auction checkbox")
    public SearchPage clickOnAuctionCheckbox() {
        LOGGER.info("Scrolling the page to the Price filter.");
        scrollInView(priceAuctionCheckboxbutton);
        LOGGER.info("Clicking on Auction checkbox.");
        waitUntilClickable(priceAuctionCheckboxbutton);
        priceAuctionCheckboxbutton.click();
        waitForSearchProgressBarToBeLoaded();
        return this;
    }

    @Step("Is Auction option checked")
    public boolean isAuctionOptionChecked() {
        LOGGER.info("Getting Auction checkbox status.");
        return priceAuctionCheckbox.isSelected();
    }

    @Step("Get search results")
    public List<CarItem> getCarItems() {
        LOGGER.info("Getting search results.");
        List<CarItem> carItems = new ArrayList<>();
        List<WebElement> itemBlocks = driver.findElements(By.xpath("//div[@id='searchResults']//div[@class='content-bar']"));
        for (WebElement item : itemBlocks) {
            scrollInView(item);
            String name = item.findElement(By.xpath(".//div[@class='head-ticket']//span")).getText();
            String usd = item.findElement(By.xpath(".//div[@class='price-ticket']//span[@data-currency='USD']"))
                    .getText().trim().replaceAll(" ", "");
            int priceUSD = Integer.parseInt(usd);
            String uah = item.findElement(By.xpath(".//div[@class='price-ticket']//span[@data-currency='UAH']"))
                    .getText().trim().replaceAll(" ", "");
            int priceUAH = Integer.parseInt(uah);
            CarItem carItem = new CarItem(name, priceUSD, priceUAH);
            carItems.add(carItem);
        }
        return carItems;
    }

    @Step("Get prices in USD from search results")
    public List<Integer> getCarItemPricesUSD(List<CarItem> cars) {
        List<Integer> prices = new ArrayList<>();
        for (CarItem item : cars) {
            int price = item.getPriceUSD();
            prices.add(price);
        }
        return prices;
    }

    @Step("Get prices in UAH from search results")
    public List<Integer> getCarItemPricesUAH(List<CarItem> cars) {
        List<Integer> prices = new ArrayList<>();
        for (CarItem item : cars) {
            int price = item.getPriceUAH();
            prices.add(price);
        }
        return prices;
    }

    @Step("Are prices in range from {from} to {to}")
    public boolean arePricesInRange(List<Integer> pricesList, int from, int to) {
        for (int price : pricesList) {
            if (price >= from && price <= to) {
                return true;
            }
        }
        return false;
    }

    private void waitForSearchProgressBarToBeLoaded() {
        new WebDriverWait(driver, 30)
                .ignoring(WebDriverException.class)
                .until((driver) -> driver.findElement(By.id("searchProgressBar")).getAttribute("class").contains("hide"));
    }

    private String scrollToPriceFilter() {
        LOGGER.info("Scrolling the page to the Price filter.");
        String currency = getPriceCurrencyValue().name();
        scrollInView(By.xpath("//input[contains(@name,'price." + currency + ".lte')]"));
        return currency;
    }
}
