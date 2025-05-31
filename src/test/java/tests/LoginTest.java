package tests;

import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import user.UserFactory;

import static enums.DepartmentNaming.PRODUCTS;
import static org.testng.Assert.*;

public class LoginTest extends BaseTest {

    private TestFlow testFlow() {
        return new TestFlow();
    }

    private class TestFlow {
        public TestFlow openLoginPage() {
            loginPage.open();
            return this;
        }

        public TestFlow loginAsAdmin() {
            loginPage.login(UserFactory.withAdminPermission());
            return this;
        }

        public TestFlow verifyProductsPage() {
            assertTrue(productsPage.titleIsDisplayed(), "Products page should be visible");
            assertEquals(productsPage.getTitle(), PRODUCTS.getDisplayName(),
                    "Page title should match expected");
            return this;
        }

        public TestFlow addProductsToCart(String productName, int... indexes) {
            productsPage.addToCart(productName);
            for (int index : indexes) {
                productsPage.addToCart(index);
            }
            return this;
        }

        public TestFlow openCartAndVerifyContents(String expectedProduct, int expectedCount) {
            productsPage.openCart();
            assertTrue(cartPage.getProductsNames().contains(expectedProduct),
                    "Cart should contain: " + expectedProduct);
            assertEquals(cartPage.getProductsNames().size(), expectedCount,
                    "Cart should contain " + expectedCount + " items");
            return this;
        }

        public TestFlow attemptLogin(String username, String password) {
            loginPage.fillLoginInput(username);
            loginPage.fillPasswordInput(password);
            loginPage.clickSubmitBtn();
            return this;
        }

        public void verifyLoginError(String expectedError) {
            assertEquals(loginPage.getErrorMsg(), expectedError,
                    "Error message should match expected");
        }
    }

    @Epic("Модуль логина интернет магазина")
    @Feature("Юридические лица")
    @Story("STG")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Zharov Dmitry dima_zharov@mail.ru")
    @TmsLink("Saucedemo1")
    @Issue("1")
    @Flaky
    @Test(description = "Проверка авторизации")
    public void correctLogin() {
        testFlow()
                .openLoginPage()
                .loginAsAdmin()
                .verifyProductsPage()
                .addProductsToCart("Sauce Labs Backpack", 0, 1, 2)
                .openCartAndVerifyContents("Sauce Labs Backpack", 3);
    }

    @DataProvider(name = "incorrectLoginData")
    public Object[][] loginData() {
        return new Object[][]{
                {"locked_out_user", "secret_sauce", "Epic sadface: Sorry, this user has been locked out"},
                {"", "secret_sauce", "Epic sadface: Username is required"},
                {"standard_user", "", "Epic sadface: Password is required"}
        };
    }

    @Test(dataProvider = "incorrectLoginData")
    public void incorrectLogin(String user, String pass, String errorMsg) {
        testFlow()
                .openLoginPage()
                .attemptLogin(user, pass)
                .verifyLoginError(errorMsg);
    }
}