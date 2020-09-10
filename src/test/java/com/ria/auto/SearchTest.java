package com.ria.auto;

import com.ria.auto.config.EnvConfig;
import com.ria.auto.entity.CarItem;
import com.ria.auto.entity.Currency;
import com.ria.auto.web.pages.SearchPage;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

import static com.ria.auto.entity.Currency.*;

@Listeners({AllureTestNg.class})
public class SearchTest extends BaseTest {

    String actualFromValue;
    String actualToValue;
    Currency actualCurrency;
    boolean actualCheckboxStatus;
    List<CarItem> resultsList;
    List<Integer> resultsPricesUSD;
    List<Integer> resultsPricesUAH;

    @BeforeMethod
    public void precondition() {
        driver.get(EnvConfig.getEnvironment().getWebUrl());
        searchPage = new SearchPage(driver);
        searchPage.selectUsedFilter().clickOnVerifiedVinCheckbox().chooseVehicleType(typeLightCar)
                .chooseVehicleBrand(brandToyota);
    }

    @Test(description = "User should be able to fill and clear Price filter fields")
    public void checkPriceInputFields() {
        actualFromValue = searchPage.setPriceFromValue("600").getPriceFromValue();
        actualToValue = searchPage.getPriceToValue();
        Assert.assertEquals(actualFromValue, "600");
        Assert.assertEquals(actualToValue, "");

        actualToValue = searchPage.setPriceToValue("100000").getPriceToValue();
        actualFromValue = searchPage.getPriceFromValue();
        Assert.assertEquals(actualFromValue, "600");
        Assert.assertEquals(actualToValue, "100000");
        refreshPage();
        actualFromValue = searchPage.getPriceFromValue();
        actualToValue = searchPage.getPriceToValue();
        Assert.assertEquals(actualFromValue, "600");
        Assert.assertEquals(actualToValue, "100000");

        searchPage.clearPriceFromValue().clearPriceToValue();
        actualFromValue = searchPage.getPriceFromValue();
        actualToValue = searchPage.getPriceToValue();
        Assert.assertEquals(actualFromValue, "");
        Assert.assertEquals(actualToValue, "");
    }

    @Test(description = "User should be able to choose price currence from the dropdown")
    public void checkPriceCurrencyDropdown() {
        actualCurrency = searchPage.getPriceCurrencyValue();
        Assert.assertEquals(actualCurrency, USD);
        searchPage.choosePriceCurrency(EUR);
        actualCurrency = searchPage.getPriceCurrencyValue();
        Assert.assertEquals(actualCurrency, EUR);
        searchPage.choosePriceCurrency(UAH);
        actualCurrency = searchPage.getPriceCurrencyValue();
        Assert.assertEquals(actualCurrency, UAH);
        searchPage.choosePriceCurrency(USD);
        actualCurrency = searchPage.getPriceCurrencyValue();
        Assert.assertEquals(actualCurrency, USD);
    }

    @Test(description = "User should be able to check/uncheck Auction option")
    public void checkAuctionOptionCheckbox() {
        actualCheckboxStatus = searchPage.isAuctionOptionChecked();
        Assert.assertFalse(actualCheckboxStatus);
        searchPage.clickOnAuctionCheckbox();
        actualCheckboxStatus = searchPage.isAuctionOptionChecked();
        Assert.assertTrue(actualCheckboxStatus);
        searchPage.clickOnAuctionCheckbox();
        actualCheckboxStatus = searchPage.isAuctionOptionChecked();
        Assert.assertFalse(actualCheckboxStatus);
    }

    @Test(description = "User should see results with corresponding prices in USD when Price filter is applied")
    public void checkResultsFilteredByUsdPrice() {
        resultsList = searchPage.setPriceFromValue("700").setPriceToValue("1000").getCarItems();
        resultsPricesUSD = searchPage.getCarItemPricesUSD(resultsList);
        Assert.assertTrue(searchPage.arePricesInRange(resultsPricesUSD, 700, 1000));
    }

    @Test(description = "User should see results with corresponding prices in UAH when Price filter is applied")
    public void checkResultsFilteredByUahPrice() {
        resultsList = searchPage.choosePriceCurrency(UAH).setPriceFromValue("30000").setPriceToValue("45000").getCarItems();
        resultsPricesUAH = searchPage.getCarItemPricesUAH(resultsList);
        Assert.assertTrue(searchPage.arePricesInRange(resultsPricesUAH, 30000, 45000));
    }
}
