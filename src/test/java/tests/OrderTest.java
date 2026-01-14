package tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pageobject.OrderPage;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest extends BaseTest {

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
    void orderFromTopButton(String firstName, String lastName,
                            String address, String metro,
                            String phone, String date,
                            String rentDays, String color,
                            String comment) {

        OrderPage orderPage = new OrderPage(driver);

        // Клик по верхней кнопке «Заказать»
        mainPage.clickOrderButtonTop();

        completeOrder(orderPage, firstName, lastName, address,
                metro, phone, date, rentDays, color, comment);
    }

    @ParameterizedTest
    @MethodSource("orderData")
    void orderFromBottomButton(String firstName, String lastName,
                               String address, String metro,
                               String phone, String date,
                               String rentDays, String color,
                               String comment) {

        OrderPage orderPage = new OrderPage(driver);

        // Клик по нижней кнопке «Заказать»
        mainPage.clickOrderButtonBottom();

        completeOrder(orderPage, firstName, lastName, address,
                metro, phone, date, rentDays, color, comment);
    }

    // Общий метод оформления заказа (без дублирования кода)
    private void completeOrder(OrderPage orderPage,
                               String firstName, String lastName,
                               String address, String metro,
                               String phone, String date,
                               String rentDays, String color,
                               String comment) {

        orderPage.fillPersonalInfo(firstName, lastName, address, metro, phone);
        orderPage.fillRentInfo(date, rentDays, color, comment);
        orderPage.confirmOrder();

        assertTrue(orderPage.getSuccessMessage().startsWith("Заказ оформлен"));
    }
}
