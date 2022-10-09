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
    String name, surname, address, station, phone, date, comment;
    OrderPageObject.Color color;
    OrderPageObject.DaysCount daysCount;

    private OrderPageObject orderPageObject;
    private WebDriver chromeDriver;

    public OrderFormTest(String name, String surname, String address, String station, String phone, String date, String comment, OrderPageObject.Color color, OrderPageObject.DaysCount daysCount) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.station = station;
        this.phone = phone;
        this.date = date;
        this.comment = comment;
        this.color = color;
        this.daysCount = daysCount;
    }

    @BeforeClass
    public static void setupDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][]{
                {"Пётр", "Авсенов", "Ул. Покрышкина, 17", "Черкизовская", "89885540982", "24.08.2022", "", OrderPageObject.Color.BLACK, OrderPageObject.DaysCount.ONE},
                {"Алексей", "Кузнецов", "Ул. Покрышкина, 19", "Черкизовская", "89885540982", "25.09.2021", "Комментарий", OrderPageObject.Color.GRAY, OrderPageObject.DaysCount.TWO},
                {"Николай", "Петров", "Ул. Покрышкина", "Тверская", "89885540982", "26.10.2020", "", OrderPageObject.Color.BLACK, OrderPageObject.DaysCount.THREE},
                {"Владимир", "АИЙИЛЬЦИКЛИКИРМИЦИБАЙРАКТАЗИЙАНКАГРАМАНОГЛУ", "Ул. Покрышкина, 17", "Сокольники", "89885540982", "27.11.2019", "[][]%%&]", OrderPageObject.Color.GRAY, OrderPageObject.DaysCount.FOUR},
                {"Максим", "Михайлов", "Индекс 679000", "Черкизовская", "89885540982", "28.12.2018", "", OrderPageObject.Color.BLACK, OrderPageObject.DaysCount.FIVE},
                {"Данил", "Авсенов", "Кемерово", "Черкизовская", "89885540982", "29.01.2017", "123", OrderPageObject.Color.GRAY, OrderPageObject.DaysCount.SIX},
                {"Кирилл", "Иванов", "Благовещенск", "Лубянка", "89885540982", "30.03.2016", "", OrderPageObject.Color.BLACK, OrderPageObject.DaysCount.SEVEN},
        };
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
    public void canInputFirstFormStep() {
        orderPageObject.clickOrderButtonNav();
        fillFirstFormStep();
        orderPageObject.clickNextButton();
        new WebDriverWait(chromeDriver, 10).until((driver) -> orderPageObject.getFormTitleString().equals("Про аренду"));
    }

    @Test
    public void canReturnToFirstStep() {
        orderPageObject.clickOrderButtonNav();
        fillFirstFormStep();
        orderPageObject.clickNextButton();
        new WebDriverWait(chromeDriver, 10).until((driver) -> orderPageObject.getFormTitleString().equals("Про аренду"));
        orderPageObject.clickGoBackButton();
        new WebDriverWait(chromeDriver, 10).until((driver) -> orderPageObject.getFormTitleString().equals("Для кого самокат"));
    }

    @Test
    public void modalShowsAfterSubmittingForm() {
        orderPageObject.clickOrderButtonNav();
        fillFirstFormStep();
        orderPageObject.clickNextButton();
        new WebDriverWait(chromeDriver, 10).until((driver) -> orderPageObject.getFormTitleString().equals("Про аренду"));
        fillSecondFormStep();
        orderPageObject.clickSubmitButton();
        orderPageObject.waitForCheckoutButton();
    }

    @Test
    public void canCheckoutForm() {
        orderPageObject.clickOrderButtonNav();
        fillFirstFormStep();
        orderPageObject.clickNextButton();
        new WebDriverWait(chromeDriver, 10).until((driver) -> orderPageObject.getFormTitleString().equals("Про аренду"));
        fillSecondFormStep();
        orderPageObject.clickSubmitButton();
        orderPageObject.clickCheckoutButton();
        orderPageObject.waitForCheckStatusButton();
    }

    @Test
    public void canSeeOrderStatusAfterFormSubmitting() {
        orderPageObject.clickOrderButtonNav();
        fillFirstFormStep();
        orderPageObject.clickNextButton();
        new WebDriverWait(chromeDriver, 10).until((driver) -> orderPageObject.getFormTitleString().equals("Про аренду"));
        fillSecondFormStep();
        orderPageObject.clickSubmitButton();
        orderPageObject.clickCheckoutButton();
        orderPageObject.clickCheckStatusButton();
        new WebDriverWait(chromeDriver, 10).until(driver -> driver.getCurrentUrl().contains("https://qa-scooter.praktikum-services.ru/track?t"));
    }

    @After
    public void teardown() {
        chromeDriver.quit();
    }
}
