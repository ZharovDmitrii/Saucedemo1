package tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import user.User;

import static org.testng.Assert.*;
import static user.UserFactory.withAdminPermission;

public class AddGoodsToCartTest extends BaseTest {
    @Epic("Модуль логина интернет магазина")
    @Feature("Юридические лица")
    @Story("STG")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Zharov Dmitry dima_zharov@mail.ru")
    @TmsLink("Saucedemo1")
    @Issue("2")
    @Test(description = "проверяем, что товары добавлены в корзину")
    @Flaky
    public void checkGoodsInCart() {
        new TestFlow()
                .openLoginPage()
                .loginAs(withAdminPermission())
                .verifyProductsPageOpened()
                .addProductsToCart(0, 2, 3)
                .openCart()
                .verifyCartContains("Sauce Labs Backpack")
                .verifyCartItemsCount(3)
                .verifyCartIsNotEmpty();
    }

    private class TestFlow {
        public TestFlow openLoginPage() {
            loginPage.open();
            return this;
        }

        public TestFlow loginAs(User user) {
            loginPage.login(user);
            return this;
        }

        public TestFlow verifyProductsPageOpened() {
            assertTrue(productsPage.isOpen(), "Страница товаров должна быть открыта");
            return this;
        }

        public TestFlow addProductsToCart(int... indices) {
            for (int index : indices) {
                productsPage.addToCart(index);
            }
            return this;
        }

        public TestFlow openCart() {
            productsPage.openCart();
            return this;
        }

        public TestFlow verifyCartContains(String productName) {
            assertTrue(cartPage.getProductsNames().contains(productName));
            return this;
        }

        public TestFlow verifyCartItemsCount(int expectedCount) {
            assertEquals(cartPage.getProductsNames().size(), expectedCount);
            return this;
        }

        public TestFlow verifyCartIsNotEmpty() {
            assertFalse(cartPage.getProductsNames().isEmpty());
            return this;
        }
    }
}