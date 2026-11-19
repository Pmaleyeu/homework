<<<<<<< Updated upstream
public class HomeWorkApp {

    public static void main(String[] args) {
        printThreeWords();
        checkSumSign();
        printColor();
        compareNumbers();
=======
﻿import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MtsOnlineTopUpTest {

    private static final String URL = "https://www.mts.by/";
    private static final String PHONE = "297777777";

    private static final String EXPECTED_AMOUNT = "10";

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get(URL);

        WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(normalize-space(.), 'Онлайн пополнение без комиссии')]")
        ));

       
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true);", title
        );
>>>>>>> Stashed changes
    }

    public static void printThreeWords() {
        System.out.println("Orange");
        System.out.println("Banana");
        System.out.println("Apple");
    }

    public static void checkSumSign() {
        int a = 5;
        int b = -3;
        if (a + b >= 0) {
            System.out.println("Сумма положительная");
        } else {
            System.out.println("Сумма отрицательная");
        }
    }

<<<<<<< Updated upstream
    public static void printColor() {
        int value = 50;
        if (value <= 0) {
            System.out.println("Красный");
        } else if (value <= 100) {
            System.out.println("Желтый");
        } else {
            System.out.println("Зеленый");
        }
=======
    @Test
    void shouldShowExpectedPlaceholdersForAllPaymentTypes() {
        checkPlaceholders("Услуги связи", Arrays.asList(
                "Номер телефона"    ));

        checkPlaceholders("Домашний интернет", Arrays.asList(
                "Номер договора"
        ));

        checkPlaceholders("Рассрочка", Arrays.asList(
                "Выберите"
        ));

        checkPlaceholders("Задолженность", Arrays.asList(
                "Ввести"
        ));
    }

    @Test
    void shouldShowPaymentDetailsAfterContinue() {
        clickPaymentType("Услуги связи");

        WebElement phoneInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("input[type='tel']")
                )
        );
        phoneInput.clear();
        phoneInput.sendKeys(PHONE);


        List<WebElement> amountInputs = driver.findElements(
                By.cssSelector("input[name*='amount'], input[placeholder*='Сумма']")
        );

        if (!amountInputs.isEmpty() && amountInputs.get(0).isDisplayed()) {
            amountInputs.get(0).clear();
            amountInputs.get(0).sendKeys(EXPECTED_AMOUNT);
        }

        WebElement continueButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(normalize-space(.), 'Продолжить')]")
                )
        );
        continueButton.click();

        WebElement paymentStep = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(normalize-space(.), 'Номер карты')]")
                )
        );
        assertTrue(paymentStep.isDisplayed());


        String pageText = driver.findElement(By.tagName("body")).getText();
        assertTrue(pageText.contains(PHONE),
                "На шаге оплаты не отображается введённый номер телефона");

        assertTrue(pageText.contains(EXPECTED_AMOUNT),
                "На шаге оплаты не найдена ожидаемая сумма");

        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        boolean amountIsOnButton = buttons.stream()
                .filter(WebElement::isDisplayed)
                .anyMatch(button -> button.getText().contains(EXPECTED_AMOUNT));

        assertTrue(amountIsOnButton,
                "Ожидаемая сумма не отображается на кнопке");

        List<WebElement> cardInputs = driver.findElements(By.xpath(
                "//input[not(@type='hidden') and not(@type='submit') "
                        + "and not(@type='checkbox')]"
        ));

        List<WebElement> visibleCardInputs = cardInputs.stream()
                .filter(WebElement::isDisplayed)
                .collect(Collectors.toList());

        assertFalse(visibleCardInputs.isEmpty(),
                "Не найдены поля для ввода реквизитов карты");

        for (WebElement input : visibleCardInputs) {
            String placeholder = input.getAttribute("placeholder");
            String ariaLabel = input.getAttribute("aria-label");

            assertTrue(
                    (placeholder != null && !placeholder.trim().isEmpty())
                            || (ariaLabel != null && !ariaLabel.trim().isEmpty()),
                    "У поля ввода реквизитов карты отсутствует подпись"
            );
        }

        List<WebElement> images = driver.findElements(By.tagName("img"));

        boolean hasPaymentIcon = images.stream()
                .filter(WebElement::isDisplayed)
                .anyMatch(image -> {
                    String description = valueOrEmpty(image.getAttribute("alt"))
                            + " " + valueOrEmpty(image.getAttribute("src"));

                    String lowerCase = description.toLowerCase();

                    return lowerCase.contains("visa")
                            || lowerCase.contains("mastercard")
                            || lowerCase.contains("belkart")
                            || lowerCase.contains("белкарт");
                });

        assertTrue(hasPaymentIcon,
                "Не найдены отображаемые иконки платёжных систем");
    }

    private void checkPlaceholders(String paymentType, List<String> expected) {
        clickPaymentType(paymentType);

        List<WebElement> inputs = driver.findElements(By.xpath(
                "//input[not(@type='hidden') and not(@type='submit') "
                        + "and not(@type='checkbox')]"
        ));

        List<String> actualPlaceholders = inputs.stream()
                .filter(WebElement::isDisplayed)
                .map(input -> input.getAttribute("placeholder"))
                .filter(value -> value != null && !value.trim().isEmpty())
                .collect(Collectors.toList());

        assertEquals(expected, actualPlaceholders,
                "Неверные надписи в незаполненных полях варианта: " + paymentType);
    }

    private void clickPaymentType(String paymentType) {
        By option = By.xpath(
                "//*[self::a or self::button or @role='tab' or @role='radio']"
                        + "[normalize-space(.)='" + paymentType + "']"
        );

        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    private String valueOrEmpty(String value) {
        return value == null ? "" : value;
>>>>>>> Stashed changes
    }

    public static void compareNumbers() {
        int a = 7;
        int b = 10;
        if (a >= b) {
            System.out.println("a >= b");
        } else {
            System.out.println("a < b");
        }
    }
}
