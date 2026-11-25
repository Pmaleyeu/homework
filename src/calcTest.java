import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GoogleCalculatorTest {

    private static final String PACKAGE = "com.google.android.calculator";
    private static final String RESOURCE_PREFIX = PACKAGE + ":id/";

    @ParameterizedTest
    @CsvSource({
            "7, op_add, 5, 12",
            "9, op_sub, 4, 5",
            "6, op_mul, 7, 42",
            "8, op_div, 2, 4"
    })
    void calculatorShouldReturnExpectedResult(
            String firstNumber,
            String operation,
            String secondNumber,
            String expectedResult
    ) throws Exception {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("Android Emulator")
                .setAutomationName("UiAutomator2")
                .setAppPackage(PACKAGE)
                .setAppActivity("com.android.calculator2.Calculator");

        AndroidDriver driver = new AndroidDriver(
                new URL("http://127.0.0.1:4723/"),
                options
        );

        try {
            WebDriverWait wait = new WebDriverWait(
                    driver,
                    Duration.ofSeconds(10)
            );

            clickNumber(driver, firstNumber);
            clickButton(driver, operation);
            clickNumber(driver, secondNumber);
            clickButton(driver, "eq");

            WebElement resultElement = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            AppiumBy.id(RESOURCE_PREFIX + "result")
                    )
            );

            String actualResult = resultElement.getText().trim();

            assertEquals(
                    expectedResult,
                    actualResult,
                    "Калькулятор показал неверный результат"
            );
        } finally {
            driver.quit();
        }
    }

    private void clickNumber(AndroidDriver driver, String number) {
        for (char digit : number.toCharArray()) {
            clickButton(driver, "digit_" + digit);
        }
    }

    private void clickButton(AndroidDriver driver, String buttonId) {
        driver.findElement(
                AppiumBy.id(RESOURCE_PREFIX + buttonId)
        ).click();
    }
}