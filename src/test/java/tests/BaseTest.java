package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobject.MainPage;

public class BaseTest {

    protected WebDriver driver;
    protected MainPage mainPage;

    public static final String MAIN_URL = "https://qa-scooter.praktikum-services.ru/";

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(MAIN_URL);
        mainPage = new MainPage(driver);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}
