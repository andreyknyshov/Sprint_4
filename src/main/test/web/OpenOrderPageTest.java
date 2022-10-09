package web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObject.OrderPageObject;

public class OpenOrderPageTest {
    private OrderPageObject orderPageObject;
    private WebDriver chromeDriver;
    final String mainPageUrl = "https://qa-scooter.praktikum-services.ru/";
    final String orderPageUrl = "https://qa-scooter.praktikum-services.ru/order";

    @BeforeClass
    public static void setupDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void setup() {
        chromeDriver = new ChromeDriver();
        orderPageObject = new OrderPageObject(chromeDriver);
        chromeDriver.get(mainPageUrl);
    }

    private void waitForOrderPageToBeOpen() {
        new WebDriverWait(chromeDriver, 10).until(ExpectedConditions.urlToBe(orderPageUrl));
    }

    @Test
    public void canOpenOrderPageWithNavButton() {
        orderPageObject.clickOrderButtonNav();
        waitForOrderPageToBeOpen();
    }

    @Test
    public void canOpenOrderPageWithSectionButton() {
        orderPageObject.clickOrderButtonSection();
        waitForOrderPageToBeOpen();
    }

    @After
    public void teardown() {
        chromeDriver.quit();
    }
}
