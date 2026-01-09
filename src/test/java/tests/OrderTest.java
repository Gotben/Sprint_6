package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pageobject.MainPage;
import pageobject.OrderPage;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest {

    private WebDriver driver;
    private MainPage mainPage;
    private OrderPage orderPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru/");

        mainPage = new MainPage(driver);
        orderPage = new OrderPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    // Данные для параметризованного теста
    static Stream<Arguments> orderData() {
        return Stream.of(
                Arguments.of("Иван", "Иванов", "ул. Ленина, 1",
                        "Сокольники", "+79991112233",
                        "05.01.2026", "2", "black", "Без комментариев"),

                Arguments.of("Пётр", "Петров", "ул. Пушкина, 10",
                        "ВДНХ", "+79994445566",
                        "06.01.2026", "3", "grey", "Позвонить заранее")
        );
    }

    @ParameterizedTest
    @MethodSource("orderData")
    public void positiveOrderFlow(String firstName, String lastName,
                                  String address, String metro,
                                  String phone, String date,
                                  String rentDays, String color,
                                  String comment) {

        // Точка входа — верхняя кнопка «Заказать»
        mainPage.clickOrderButtonTop();

        // Заполнение формы «Для кого самокат»
        orderPage.fillPersonalInfo(firstName, lastName, address, metro, phone);

        // Заполнение формы «Про аренду»
        orderPage.fillRentInfo(date, rentDays, color, comment);

        // Подтверждение заказа
        orderPage.confirmOrder();

        // Проверка успешного оформления
        assertTrue(orderPage.getSuccessMessage().startsWith("Заказ оформлен"));
    }
}
