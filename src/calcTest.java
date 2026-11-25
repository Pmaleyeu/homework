import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.net.URL;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Мобильное приложение")
@Feature("Калькулятор Google")
class GoogleCalculatorTest {

    private static final String APP_PACKAGE = "com.google.android.calculator";
    private static final String RESOURCE_PREFIX = APP_PACKAGE + ":id/";

    private AndroidDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("Android Emulator")
                .setAutomationName("UiAutomator2")
                .setAppPackage(APP_PACKAGE)
                .setAppActivity("com.android.calculator2.Calculator");

        driver = new AndroidDriver(
                new URL("http://127.0.0.1:4723/"),
                options
        );

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @ParameterizedTest(name = "{0} {1} {2} = {3}")
    @CsvSource({
            "7, op_add, 5, 12",
            "9, op_sub, 4, 5",
            "6, op_mul, 7, 42",
            "8, op_div, 2, 4"
    })
    @Story("Проверка арифметических операций")
    void calculatorShouldShowExpectedResult(
            String firstNumber,
            String operationButton,
            String secondNumber,
            String expectedResult
    ) {
        Allure.step("Ввести первое число: " + firstNumber, () ->
                enterNumber(firstNumber));

        Allure.step("Нажать кнопку операции: " + operationButton, () ->
                clickButton(operationButton));

        Allure.step("Ввести второе число: " + secondNumber, () ->
                enterNumber(secondNumber));

        Allure.step("Нажать кнопку равно", () ->
                clickButton("eq"));

        String actualResult = Allure.step(
                "Считать результат с экрана калькулятора",
                this::readResult
        );

        Allure.addAttachment("Ожидаемый результат", expectedResult);
        Allure.addAttachment("Фактический результат", actualResult);

        Allure.step("Сравнить фактический и ожидаемый результаты", () ->
                assertEquals(
                        expectedResult,
                        actualResult,
                        "Калькулятор показал неверный результат"
                ));
    }

    private void enterNumber(String number) {
        for (char digit : number.toCharArray()) {
            clickButton("digit_" + digit);
        }
    }

    private void clickButton(String buttonId) {
        driver.findElement(
                AppiumBy.id(RESOURCE_PREFIX + buttonId)
        ).click();
    }

    private String readResult() {
        WebElement result = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        AppiumBy.id(RESOURCE_PREFIX + "result")
                )
        );

        return result.getText().trim();
    }
}