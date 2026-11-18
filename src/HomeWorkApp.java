import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class MtsByTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private final By blockTitle = By.xpath(
            "//*[contains(normalize-space(.), 'Онлайн пополнение без комиссии')]"
    );

    @BeforeEach
    void setUp() {
        System.setProperty(
                "webdriver.chrome.driver",
                "src/test/resources/chromedriver"
        );

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get("https://www.mts.by");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldDisplayOnlineTopUpTitle() {
        WebElement title = wait.until(
                ExpectedConditions.visibilityOfElementLocated(blockTitle)
        );

        assertTrue(title.getText().contains("Онлайн пополнение без комиссии"));
    }

    @Test
    void shouldDisplayPaymentSystemLogos() {
        // Проверьте в DevTools, что у логотипов есть alt с названием платёжной системы.
        By paymentLogos = By.xpath(
                "//img[contains(translate(@alt,"
                        + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                        + "'abcdefghijklmnopqrstuvwxyz'), 'visa')"
                        + " or contains(translate(@alt,"
                        + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                        + "'abcdefghijklmnopqrstuvwxyz'), 'mastercard')"
                        + " or contains(translate(@alt,"
                        + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                        + "'abcdefghijklmnopqrstuvwxyz'), 'belkart')]"
        );

        List<WebElement> logos = driver.findElements(paymentLogos);

        assertFalse(logos.isEmpty(), "Логотипы платёжных систем не найдены");
        assertTrue(logos.stream().anyMatch(WebElement::isDisplayed),
                "Ни один найденный логотип не отображается");
    }

    @Test
    void shouldOpenServiceDetails() {
        String currentUrl = driver.getCurrentUrl();

        By detailsLink = By.xpath(
                "//a[contains(normalize-space(.), 'Подробнее о сервисе')]"
        );

        wait.until(ExpectedConditions.elementToBeClickable(detailsLink)).click();

        wait.until(ExpectedConditions.not(
                ExpectedConditions.urlToBe(currentUrl)
        ));

        assertNotEquals(currentUrl, driver.getCurrentUrl());
    }

    @Test
    void shouldContinueWithCommunicationServices() {
        By communicationServices = By.xpath(
                "//*[self::button or self::a or @role='tab']"
                        + "[contains(normalize-space(.), 'Услуги связи')]"
        );

        List<WebElement> options = driver.findElements(communicationServices);
        if (!options.isEmpty() && options.get(0).isDisplayed()) {
            options.get(0).click();
        }

        By phoneInput = By.cssSelector("input[type='tel']");
        WebElement phone = wait.until(
                ExpectedConditions.visibilityOfElementLocated(phoneInput)
        );
        phone.clear();
        phone.sendKeys("297777777");

        By continueButton = By.xpath(
                "//button[contains(normalize-space(.), 'Продолжить')]"
                        + " | //input[@type='submit' and @value='Продолжить']"
        );

        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
 
        By nextStep = By.xpath(
                "//*[contains(normalize-space(.), 'Сумма')]"
                        + " | //*[contains(normalize-space(.), 'Оплата')]"
        );

        assertTrue(
                wait.until(ExpectedConditions.visibilityOfElementLocated(nextStep))
                        .isDisplayed(),
                "После нажатия «Продолжить» следующий шаг формы не появился"
        );
    }
}