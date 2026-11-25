import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WildberriesCartTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get("https://www.wildberries.ru/");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void addedProductsShouldBeDisplayedCorrectlyInCart() {
        HomePage homePage = new HomePage(driver, wait);
        List<ProductData> expectedProducts = homePage.addFirstProducts(2);

        assertEquals(2, expectedProducts.size(),
                "Не удалось добавить два товара");

        CartPage cartPage = homePage.openCart();

       
        BigDecimal expectedTotal = BigDecimal.ZERO;

        for (ProductData expected : expectedProducts) {
            CartItem cartItem = cartPage.findItemByName(expected.getName());

            assertNotNull(cartItem,
                    "В корзине не найден товар: " + expected.getName());

            assertEquals(1, cartItem.getQuantity(),
                    "Неверное количество товара: " + expected.getName());

            assertEquals(0, expected.getPrice().compareTo(cartItem.getPrice()),
                    "Цена в корзине отличается от цены в карточке товара: "
                            + expected.getName());

            expectedTotal = expectedTotal.add(expected.getPrice());
        }

        assertEquals(expectedProducts.size(), cartPage.getItems().size(),
                "В корзине неожиданное количество товаров");

        assertEquals(0, expectedTotal.compareTo(cartPage.getTotal()),
                "Общая сумма корзины не равна сумме цен товаров");
    }

   
    private static class Locators {
        
        private static final By PRODUCT_CARDS =
                By.cssSelector("article.product-card");

        private static final By PRODUCT_NAME =
                By.cssSelector(".product-card__name");

        private static final By PRODUCT_PRICE =
                By.cssSelector(".price__lower");

        private static final By ADD_TO_CART_BUTTON =
                By.cssSelector(".product-card__add-basket");

        
        private static final By CART_LINK =
                By.cssSelector("a[href*='/lk/basket']");

        
        private static final By CART_ITEMS =
                By.cssSelector(".basket-item");

        private static final By CART_ITEM_NAME =
                By.cssSelector(".good-name");

        private static final By CART_ITEM_PRICE =
                By.cssSelector(".price");

        private static final By CART_ITEM_QUANTITY =
                By.cssSelector("input[type='number']");

      
        private static final By CART_TOTAL =
                By.cssSelector("[data-testid='basket-total']");
    }

    
    private static class HomePage {
        private final WebDriver driver;
        private final WebDriverWait wait;

        private HomePage(WebDriver driver, WebDriverWait wait) {
            this.driver = driver;
            this.wait = wait;
        }

        private List<ProductData> addFirstProducts(int count) {
            wait.until(d -> d.findElements(Locators.PRODUCT_CARDS).size() >= count);

            List<ProductData> addedProducts = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                
                List<WebElement> cards = driver.findElements(Locators.PRODUCT_CARDS);
                WebElement card = cards.get(i);

                ProductCard productCard = new ProductCard(card, wait);
                ProductData product = productCard.getData();

                productCard.addToCart();
                addedProducts.add(product);
            }

            return addedProducts;
        }

        private CartPage openCart() {
            wait.until(ExpectedConditions.elementToBeClickable(Locators.CART_LINK))
                    .click();

            wait.until(ExpectedConditions.presenceOfElementLocated(
                    Locators.CART_ITEMS
            ));

            return new CartPage(driver, wait);
        }
    }

    
    private static class ProductCard {
        private final WebElement element;
        private final WebDriverWait wait;

        private ProductCard(WebElement element, WebDriverWait wait) {
            this.element = element;
            this.wait = wait;
        }

        private ProductData getData() {
            String name = element.findElement(Locators.PRODUCT_NAME)
                    .getText()
                    .trim();

            BigDecimal price = parsePrice(
                    element.findElement(Locators.PRODUCT_PRICE).getText()
            );

            return new ProductData(name, price);
        }

        private void addToCart() {
            WebElement button = element.findElement(Locators.ADD_TO_CART_BUTTON);
            wait.until(ExpectedConditions.elementToBeClickable(button)).click();
        }
    }

    
    private static class CartPage {
        private final WebDriver driver;
        private final WebDriverWait wait;

        private CartPage(WebDriver driver, WebDriverWait wait) {
            this.driver = driver;
            this.wait = wait;
        }

        private List<CartItem> getItems() {
            wait.until(d -> !d.findElements(Locators.CART_ITEMS).isEmpty());

            List<CartItem> result = new ArrayList<>();
            for (WebElement element : driver.findElements(Locators.CART_ITEMS)) {
                result.add(new CartItem(element));
            }
            return result;
        }

        private CartItem findItemByName(String expectedName) {
            for (CartItem item : getItems()) {
                if (item.getName().equals(expectedName)) {
                    return item;
                }
            }
            return null;
        }

        private BigDecimal getTotal() {
            String totalText = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(Locators.CART_TOTAL)
            ).getText();

            return parsePrice(totalText);
        }
    }

    
    private static class CartItem {
        private final WebElement element;

        private CartItem(WebElement element) {
            this.element = element;
        }

        private String getName() {
            return element.findElement(Locators.CART_ITEM_NAME)
                    .getText()
                    .trim();
        }

        private BigDecimal getPrice() {
            return parsePrice(
                    element.findElement(Locators.CART_ITEM_PRICE).getText()
            );
        }

        private int getQuantity() {
            WebElement quantityInput =
                    element.findElement(Locators.CART_ITEM_QUANTITY);

            return Integer.parseInt(quantityInput.getAttribute("value"));
        }
    }

    private static class ProductData {
        private final String name;
        private final BigDecimal price;

        private ProductData(String name, BigDecimal price) {
            this.name = name;
            this.price = price;
        }

        private String getName() {
            return name;
        }

        private BigDecimal getPrice() {
            return price;
        }
    }

    private static BigDecimal parsePrice(String text) {
        
        String normalized = text
                .replace("\u00A0", "")
                .replace(" ", "")
                .replace(",", ".")
                .replaceAll("[^0-9.]", "");

        if (normalized.isEmpty()) {
            throw new IllegalArgumentException(
                    "Не удалось прочитать цену из текста: " + text
            );
        }

        return new BigDecimal(normalized);
    }
}