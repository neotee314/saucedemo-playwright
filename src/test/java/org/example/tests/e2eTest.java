package org.example.tests;

import org.example.pages.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class e2eTest extends BaseTest {

    @Test
    public void testCompleteCheckoutAndBackHome() {
        page.navigate("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(page);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(page);
        inventoryPage.addItemToCart("Sauce Labs Backpack");
        inventoryPage.goToCart();
        CartPage cartPage = new CartPage(page);
        cartPage.clickCheckout();

        CheckoutStepOnePage stepOne = new CheckoutStepOnePage(page);
        stepOne.fillCheckoutInfo("John", "Doe", "12345");
        stepOne.clickContinue();

        CheckoutStepTwoPage stepTwo = new CheckoutStepTwoPage(page);
        stepTwo.clickFinish();

        CheckoutCompletePage completePage = new CheckoutCompletePage(page);
        Assertions.assertTrue(completePage.isOnCheckoutCompletePage());

        completePage.clickBackHome();
        Assertions.assertTrue(page.url().contains("inventory.html"));
    }

    @Test
    public void testCancelAtCheckoutStepTwo() {
        page.navigate("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(page);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(page);
        inventoryPage.addItemToCart("Sauce Labs Backpack");
        inventoryPage.goToCart();

        CartPage cartPage = new CartPage(page);
        cartPage.clickCheckout();

        CheckoutStepOnePage stepOne = new CheckoutStepOnePage(page);
        stepOne.fillCheckoutInfo("John", "Doe", "12345");
        stepOne.clickContinue();

        CheckoutStepTwoPage stepTwo = new CheckoutStepTwoPage(page);
        stepTwo.clickCancel();

        Assertions.assertTrue(page.url().contains("inventory.html"));
    }

    @Test
    public void testCancelAtCheckoutStepOne() {
        page.navigate("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(page);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(page);
        inventoryPage.addItemToCart("Sauce Labs Backpack");
        inventoryPage.goToCart();

        CartPage cartPage = new CartPage(page);
        cartPage.clickCheckout();

        CheckoutStepOnePage stepOne = new CheckoutStepOnePage(page);
        stepOne.clickCancel();

        Assertions.assertTrue(page.url().contains("cart.html"));
    }

    @Test
    public void testContinueShoppingFromCart() {
        page.navigate("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(page);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(page);
        inventoryPage.addItemToCart("Sauce Labs Backpack");
        inventoryPage.goToCart();

        CartPage cartPage = new CartPage(page);
        cartPage.clickContinueShopping();

        Assertions.assertTrue(page.url().contains("inventory.html"));
    }
}
