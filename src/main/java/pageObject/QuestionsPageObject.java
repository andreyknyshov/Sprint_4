package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class QuestionsPageObject {
    // Префикс для поиска кнопок аккордеона внутри блока с вопросами
    final String accordionXpathPrefix = "//*[@id='root']/div/div/div[5]/";
    // Драйвер для доступа к странице
    WebDriver driver;

    public QuestionsPageObject(WebDriver driver) {
        this.driver = driver;
    }

    public void clickAccordionButton(String buttonTitle) {
        // Выбираем нужный заголовок аккордеона
        String xpath = String.format("%s/div[@class='accordion__button'][text()='%s']", accordionXpathPrefix, buttonTitle);
        By locator = By.xpath(xpath);
        WebElement accordionButton = driver.findElement(locator);
        // Скроллим до него
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView()", accordionButton);
        // Ждём, пока можно будет кликнуть и кликаем
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(accordionButton));
        accordionButton.click();
    }

    public void waitForAccordionItem(String text) {
        // Ищем нужный блок с текстом
        String xpath = String.format("%s/div[@class='accordion__panel']/p[text()='%s']", accordionXpathPrefix, text);
        By locator = By.xpath(xpath);
        // Ожидаем его появления
        new WebDriverWait(driver, 10).until(driver -> text.equals(driver.findElement(locator).getText()));
    }
}