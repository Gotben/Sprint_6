package pageobject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

import java.time.Duration;
import java.util.NoSuchElementException;

public class MainPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ===== КНОПКИ «ЗАКАЗАТЬ» =====

    // Верхняя кнопка «Заказать»
    private By orderButtonTop =
            By.xpath("(//button[contains(@class,'Button_Button__ra12g')])[1]");

    // Нижняя кнопка «Заказать»
    private By orderButtonBottom =
            By.xpath("//div[@class='Home_FinishButton__1_cWm']//button");

    // ===== МЕТОДЫ =====

    // Клик по верхней кнопке «Заказать»
    public void clickOrderButtonTop() {
        wait.until(ExpectedConditions.elementToBeClickable(orderButtonTop)).click();
    }

    // Клик по нижней кнопке «Заказать»
    public void clickOrderButtonBottom() {
        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(orderButtonBottom)
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", button);

        button.click();
    }

    // Клик по FAQ-вопросу по тексту
    public void clickFaqQuestionByText(String questionText) {
        WebElement question = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@class='accordion__button' and contains(text(),'" + questionText + "')]")
                )
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", question);

        //question.click();
        wait.until(ExpectedConditions.elementToBeClickable(question)).click();
    }



    // Получение текста ВИДИМОГО ответа FAQ
    public String getVisibleFaqAnswerText() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(@class,'accordion__panel') and not(@hidden)]")
                )
        ).getText();
    }
}
