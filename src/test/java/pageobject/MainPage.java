package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ===== Элементы главной страницы =====

    // Верхняя кнопка «Заказать»
    private By orderButtonTop = By.xpath("(//button[contains(@class,'Button_Button__ra12g')])[1]");

    // Нижняя кнопка «Заказать»
    private By orderButtonBottom = By.xpath("(//button[contains(@class,'Button_Button__ra12g')])[3]");

    // Вопросы в FAQ
    private By faqQuestions = By.className("accordion__button");

    // Ответы в FAQ
    private By faqAnswers = By.className("accordion__panel");

    // ===== Методы =====

    // Клик по верхней кнопке «Заказать»
    public void clickOrderButtonTop() {
        wait.until(ExpectedConditions.elementToBeClickable(orderButtonTop)).click();
    }

    // Клик по нижней кнопке «Заказать»
    public void clickOrderButtonBottom() {
        List<WebElement> buttons = driver.findElements(orderButtonBottom);
        WebElement bottomButton = buttons.get(1);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", bottomButton);

        wait.until(ExpectedConditions.elementToBeClickable(bottomButton)).click();
    }

    // Клик по вопросу FAQ по индексу
    public void clickFaqQuestion(int index) {
        List<WebElement> questions = driver.findElements(faqQuestions);
        WebElement question = questions.get(index);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", question);

        wait.until(ExpectedConditions.elementToBeClickable(question)).click();
    }

    // Получение текста ответа FAQ по индексу
    public String getFaqAnswerText(int index) {
        List<WebElement> answers = driver.findElements(faqAnswers);
        return answers.get(index).getText();
    }
}
