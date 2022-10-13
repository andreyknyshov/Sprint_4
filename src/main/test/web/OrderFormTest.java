package web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObject.OrderPageObject;

@RunWith(Parameterized.class)
public class OrderFormTest {
    final String url = "https://qa-scooter.praktikum-services.ru/";
    final String orderStatusURL = "https://qa-scooter.praktikum-services.ru/track?t";
    String name, surname, address, station, phone, date, comment;
    OpenOrderPageType openOrderPageType;
    OrderPageObject.Color color;
    OrderPageObject.DaysCount daysCount;

    private OrderPageObject orderPageObject;
    private WebDriver chromeDriver;

    public OrderFormTest(String name, String surname, String address, String station, String phone, String date, String comment, OrderPageObject.Color color, OrderPageObject.DaysCount daysCount, OpenOrderPageType openOrderPageType) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.station = station;
        this.phone = phone;
        this.date = date;
        this.comment = comment;
        this.color = color;
        this.daysCount = daysCount;
        this.openOrderPageType = openOrderPageType;
    }

    @BeforeClass
    public static void setupDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][]{{"Пётр", "Авсенов", "Ул. Покрышкина, 17", "Черкизовская", "89885540982", "24.08.2022", "", OrderPageObject.Color.BLACK, OrderPageObject.DaysCount.ONE, OpenOrderPageType.FROM_NAV}, {"Алексей", "Кузнецов", "Ул. Покрышкина, 19", "Черкизовская", "89885540982", "25.09.2021", "Комментарий", OrderPageObject.Color.GRAY, OrderPageObject.DaysCount.TWO, OpenOrderPageType.FROM_SECTION},};
    }

    @Before
    public void setup() {
        chromeDriver = new ChromeDriver();
        orderPageObject = new OrderPageObject(chromeDriver);
        chromeDriver.get(url);
    }

    private void fillFirstFormStep() {
        orderPageObject.setName(name);
        orderPageObject.setSurname(surname);
        orderPageObject.setAddress(address);
        orderPageObject.setStation(station);
        orderPageObject.setPhone(phone);
    }

    private void fillSecondFormStep() {
        orderPageObject.setDate(date);
        orderPageObject.setRentDropDown(daysCount);
        orderPageObject.setColor(color);
        orderPageObject.setComment(comment);
    }

    @Test
    public void canCheckoutForm() {
        // Открываем страницу с формой заказа
        switch (openOrderPageType) {
            case FROM_SECTION:
                orderPageObject.clickOrderButtonSection();
                break;
            case FROM_NAV:
            default:
                orderPageObject.clickOrderButtonNav();
        }
        // Вводим данные в первый шаг формы
        fillFirstFormStep();
        // Подтверждаем первый шаг формы, переходим на второй
        orderPageObject.clickNextButton();
        // Ждём, пока откроется второй шаг формы
        orderPageObject.waitForSecondStepToLoad();
        // Вводим данные во второй шаг формы
        fillSecondFormStep();
        // Кликаем на кнопку оправки формы
        orderPageObject.clickSubmitButton();
        // Кликаем на кнопку подтверждения отправки формы
        orderPageObject.clickCheckoutButton();
        // Ждём открытия попапа с кнопкой проверки заказа
        orderPageObject.waitForCheckStatusButton();
        // Кликаем на кнопку проверки статуса заказа
        orderPageObject.clickCheckStatusButton();
        // Ждём перехода на страницу с данными о заказе
        new WebDriverWait(chromeDriver, 10).until(driver -> driver.getCurrentUrl().contains(orderStatusURL));
    }

    @After
    public void teardown() {
        chromeDriver.quit();
    }

    private enum OpenOrderPageType {
        FROM_NAV, FROM_SECTION
    }
}
