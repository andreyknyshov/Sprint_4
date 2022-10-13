package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderPageObject {
    // Драйвер для доступа к странице
    WebDriver driver;
    // Кнопка заказа в блоке навигации
    private By orderButtonNav = By.xpath("//*[@id=\"root\"]/div/div/div[1]/div[2]/button[1]");
    // Кнопка заказа в секции заказа
    private By orderButtonSection = By.xpath("//*[@id=\"root\"]/div/div/div[4]/div[2]/div[5]/button");
    // Поле ввода имени в форме
    private By nameInput = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[1]/input");
    // Поле ввода фамилии в форме
    private By surnameInput = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[2]/input");
    // Поле ввода адреса в форме
    private By addressInput = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[3]/input");
    // Поле ввода станции в форме
    private By stationInput = By.xpath("/html/body/div/div/div[2]/div[2]/div[4]/div/div/input");
    // Поле ввода теелефона в форме
    private By phoneInput = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[5]/input");
    // Поле ввода даты в форме
    private By dateInput = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[1]/div[1]/div/input");
    // Поле с дропдауном для ввода количества дней аренды
    private By rentDropDown = By.className("Dropdown-root");
    // Кнопка дропдауна для аренды на 1 день
    private By rentOneDayButton = By.xpath("//*[@class='Dropdown-menu']//*[text()='сутки']");
    // Кнопка дропдауна для аренды на 2 дня
    private By rentTwoDaysButton = By.xpath("//*[@class='Dropdown-menu']//*[text()='двое суток']");
    // Кнопка дропдауна для аренды на 3 дня
    private By rentThreeDaysButton = By.xpath("//*[@class='Dropdown-menu']//*[text()='трое суток']");
    // Кнопка дропдауна для аренды на 4 дня
    private By rentFourDaysButton = By.xpath("//*[@class='Dropdown-menu']//*[text()='четверо суток']");
    // Кнопка дропдауна для аренды на 5 дней
    private By rentFiveDaysButton = By.xpath("//*[@class='Dropdown-menu']//*[text()='пятеро суток']");
    // Кнопка дропдауна для аренды на 6 дней
    private By rentSixDaysButton = By.xpath("//*[@class='Dropdown-menu']//*[text()='шестеро суток']");
    // Кнопка дропдауна для аренды на 7 дней
    private By rentSevenDaysButton = By.xpath("//*[@class='Dropdown-menu']//*[text()='семеро суток']");
    // Чекебокс для выбора черного цвета
    private By blackColorCheckbox = By.id("black");
    // Чекебокс для выбора серого цвета
    private By grayColorCheckbox = By.id("grey");
    // Поле ввода комментария
    private By commentInput = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[4]/input");
    // Кнопка перехода ко второму шагу в форме
    private By nextButton = By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/button");
    // Кнопка подтврждения 2 шага в форме
    private By submitButton = By.xpath("//*[@id=\"root\"]/div/div[2]/div[3]/button[2]");
    //Заголовок формы
    private By formTitle = By.xpath("//*[@id=\"root\"]/div/div[2]/div[1]");
    // Кнопка подтверждения заказа
    private By checkoutButton = By.xpath("//*[@id=\"root\"]/div/div[2]/div[5]/div[2]/button[2]");
    // Кнопка просмотра статуса заказа после подтверждения формы
    private By checkStatusButton = By.xpath("//*[@id='root']//button[text()='Посмотреть статус']");

    public OrderPageObject(WebDriver driver) {
        this.driver = driver;
    }

    private void scrollToElement(WebElement element) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView()", element);
    }

    private void setInputValue(By locator, String text) {
        // Ищем элемент
        WebElement input = driver.findElement(locator);
        // Скроллим до него
        scrollToElement(input);
        // И вводим текст
        input.sendKeys(text);
    }

    private void clickButton(By locator) {
        // Ищем элемент
        WebElement button = driver.findElement(locator);
        // Скроллим до него
        scrollToElement(button);
        // И кликаем
        button.click();
    }

    public void setAddress(String adress) {
        setInputValue(addressInput, adress);
    }

    public void setSurname(String surname) {
        setInputValue(surnameInput, surname);
    }

    public void setName(String name) {
        setInputValue(nameInput, name);
    }

    public void setStation(String station) {
        // Выбираем нужный пункт
        clickButton(stationInput);
        // Ищем элемент с нужной станцией
        By stationElementXPath = By.xpath("//*[@class='select-search__options']//*[text()='" + station + "']");
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(stationElementXPath));
        WebElement stationText = driver.findElement(stationElementXPath);
        // Ищем кнопку для этого элемента
        WebElement stationButton = (WebElement) ((JavascriptExecutor) driver).executeScript("return arguments[0].parentNode;", stationText);
        // Скроллим до этой кнопки и ккликаем по ней
        scrollToElement(stationButton);
        stationButton.click();
    }

    public void setPhone(String phone) {
        setInputValue(phoneInput, phone);
    }

    public void clickOrderButtonNav() {
        clickButton(orderButtonNav);
    }

    public void clickOrderButtonSection() {
        clickButton(orderButtonSection);
    }

    public void clickNextButton() {
        clickButton(nextButton);
    }

    public void clickSubmitButton() {
        clickButton(submitButton);
    }

    public void setDate(String date) {
        setInputValue(dateInput, date);
    }

    public void setRentDropDown(DaysCount days) {
        // Скидываем фокус
        clickButton(formTitle);
        // Открываем дропдаун
        clickButton(rentDropDown);
        // Выбираем нужный пункт
        switch (days) {
            case ONE:
                clickButton(rentOneDayButton);
                break;
            case TWO:
                clickButton(rentTwoDaysButton);
                break;
            case THREE:
                clickButton(rentThreeDaysButton);
                break;
            case FOUR:
                clickButton(rentFourDaysButton);
                break;
            case FIVE:
                clickButton(rentFiveDaysButton);
                break;
            case SIX:
                clickButton(rentSixDaysButton);
                break;
            case SEVEN:
                clickButton(rentSevenDaysButton);
                break;
        }
    }

    public void setColor(Color color) {
        // Выбираем нужный чекбокс
        switch (color) {
            case GRAY:
                clickButton(grayColorCheckbox);
                break;
            case BLACK:
                clickButton(blackColorCheckbox);
                break;
        }
    }

    public void setComment(String comment) {
        setInputValue(commentInput, comment);
    }

    public String getFormTitleString() {
        return driver.findElement(formTitle).getText();
    }

    public void clickCheckoutButton() {
        clickButton(checkoutButton);
    }

    public void waitForCheckStatusButton() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(checkoutButton));
    }

    public void waitForSecondStepToLoad() {
        new WebDriverWait(driver, 10).until((driver) -> getFormTitleString().equals("Про аренду"));
    }

    public void clickCheckStatusButton() {
        clickButton(checkStatusButton);
    }

    public enum DaysCount {
        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN
    }

    public enum Color {
        BLACK, GRAY
    }
}
