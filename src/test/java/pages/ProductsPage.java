package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductsPage extends BasePage {
    private final By title = By.cssSelector("[class='title']");
    private final By title2 = By.xpath("//*[text()='Products']");
    private static final String ADD_TO_CART_BUTTON_PATTERN = "//div[text()='%s']//ancestor::div[@class='inventory_item']//button";
    private static final By ADD_TO_CART_BUTTONS = By.xpath("//*[text()='Add to cart']");
    private static final By CART_LINK = By.xpath("//*[@data-test='shopping-cart-link']");
    private static final By PRODUCT_ITEMS = By.xpath("//*[text()='Add to cart']");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Проверяем название товара")
    public String getTitle() {
        return driver.findElement(title).getText();
    }

    @Step("Проверяем, что отображен заголовок страницы")
    public boolean titleIsDisplayed() {
        return driver.findElement(title2).isDisplayed();
    }

    @Step("Добавляем товар в корзину")
    public void addToCart(String goodsName) {
        By addToCartLocator = By.xpath(String.format(ADD_TO_CART_BUTTON_PATTERN, goodsName));
        driver.findElement(addToCartLocator).click();
    }

    @Step("Добавление товара в корзину по индексу")
    public void addToCart(int index) {
        driver.findElements(ADD_TO_CART_BUTTONS).get(index).click();
    }

    @Step("Ожидаем прогрузки карточек товаров")
    public boolean isOpen() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_ITEMS));
        return driver.findElement(title2).isDisplayed();
    }

    @Step("Открытие корзины")
    public void openCart() {
        driver.findElement(CART_LINK).click();
    }
}