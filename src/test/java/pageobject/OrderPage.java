package pageobject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class OrderPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ===== Форма «Для кого самокат» =====

    // Поле «Имя»
    private By firstNameInput = By.xpath("//input[@placeholder='* Имя']");

    // Поле «Фамилия»
    private By lastNameInput = By.xpath("//input[@placeholder='* Фамилия']");

    // Поле «Адрес»
    private By addressInput = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");

    // Поле «Станция метро»
    private By metroInput = By.className("select-search__input");
    //private By metroInput = By.xpath("//input[@placeholder='* Станция метро']");

    // Поле «Телефон»
    private By phoneInput = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");

    // Кнопка «Далее»
    private By nextButton = By.xpath("//button[text()='Далее']");

    // ===== Форма «Про аренду» =====

    // Поле «Дата»
    private By dateInput = By.xpath("//input[@placeholder='* Когда привезти самокат']");

    private By rentDropdown = By.className("Dropdown-root");

    // Выпадающий список «Срок аренды»
    //private By rentOptions = By.className("Dropdown-option");
    private By rentOptions = By.xpath("//div[@class='Dropdown-option']");

    // Чекбокс «чёрный»
    private By blackColorCheckbox = By.id("black");

    // Чекбокс «серый»
    private By greyColorCheckbox = By.id("grey");

    // Поле «Комментарий»
    private By commentInput = By.xpath("//input[@placeholder='Комментарий для курьера']");

    // Кнопка «Заказать»
    private By orderButton = By.xpath("//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");

    // Кнопка подтверждения «Да»
    private By confirmButton = By.xpath("//button[text()='Да']");

    // Заголовок успешного заказа
    private By successMessage = By.className("Order_ModalHeader__3FDaJ");

    // ===== Методы =====

    // Заполнение формы «Для кого самокат»
    public void fillPersonalInfo(String firstName, String lastName,
                                 String address, String metro, String phone) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput)).sendKeys(firstName);
        driver.findElement(lastNameInput).sendKeys(lastName);
        driver.findElement(addressInput).sendKeys(address);

        WebElement metroField = driver.findElement(metroInput);
        metroField.sendKeys(metro);
        metroField.sendKeys(Keys.ARROW_DOWN, Keys.ENTER);

        driver.findElement(phoneInput).sendKeys(phone);

        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateInput));
    }

    // Заполнение формы «Про аренду»
    public void fillRentInfo(String date, String rentDays, String color, String comment) {

        // Ввод даты
        WebElement dateField = wait.until(ExpectedConditions.visibilityOfElementLocated(dateInput));
        dateField.sendKeys(date);
        dateField.sendKeys(Keys.ENTER);

        // Открытие выпадающего списка
        By rentDropdown = By.className("Dropdown-root");
        wait.until(ExpectedConditions.elementToBeClickable(rentDropdown)).click();

        // Маппинг числа дней в текст UI
        Map<String, String> rentMapping = Map.of(
                "1", "сутки",
                "2", "двое суток",
                "3", "трое суток",
                "4", "четверо суток",
                "5", "пятеро суток",
                "6", "шестеро суток",
                "7", "семеро суток"
        );

        String rentText = rentMapping.get(rentDays);
        if (rentText == null) {
            throw new RuntimeException("Срок аренды не поддерживается: " + rentDays);
        }

        // Дождаться появления опций
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(rentOptions));

        // Выбрать нужный срок аренды
        boolean selected = false;
        for (WebElement option : options) {
            if (option.getText().equalsIgnoreCase(rentText)) {
                option.click();
                selected = true;
                break;
            }
        }

        if (!selected) {
            throw new RuntimeException("Срок аренды не найден на странице: " + rentText);
        }

        // Выбор цвета
        if (color.equalsIgnoreCase("black")) {
            driver.findElement(blackColorCheckbox).click();
        } else if (color.equalsIgnoreCase("grey")) {
            driver.findElement(greyColorCheckbox).click();
        }

        // Комментарий
        driver.findElement(commentInput).sendKeys(comment);
    }

    // Подтверждение заказа
    public void confirmOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(orderButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
    }

    // Получение текста успешного заказа
    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).getText();
    }
}
