package org.example.tests;

import org.example.pages.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CheckoutStepOneTest extends BaseTest {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutStepOnePage checkoutStepOnePage;

    @BeforeAll
    void setUp() {
        loginPage = new LoginPage(page);
        inventoryPage = new InventoryPage(page);
        cartPage = new CartPage(page);
        checkoutStepOnePage = new CheckoutStepOnePage(page);
    }

    @BeforeEach
    void init() {
        page.navigate("https://www.saucedemo.com/");
        loginPage.login("standard_user", "secret_sauce");
        assertTrue(loginPage.isLoggedIn(), "Login failed!");

        inventoryPage.addItemToCart("Sauce Labs Backpack");
        inventoryPage.goToCart();
        cartPage.clickCheckout();
        assertTrue(checkoutStepOnePage.isOnCheckoutStepOnePage(), "Not on Checkout Step One page");
    }

    @Test
    @DisplayName("Complete Checkout Step One Successfully")
    void completeCheckoutStepOneSuccessfully() {
        checkoutStepOnePage.enterFirstName("John");
        checkoutStepOnePage.enterLastName("Doe");
        checkoutStepOnePage.enterPostalCode("12345");
        checkoutStepOnePage.clickContinue();

        assertTrue(page.url().contains("checkout-step-two.html"), "Did not navigate to Checkout Step Two");
    }

    @Test
    @DisplayName("Verify Error Message When Fields Are Empty")
    void verifyErrorMessageForEmptyFields() {
        checkoutStepOnePage.clickContinue();
        String error = checkoutStepOnePage.getErrorMessage();
        assertEquals("Error: First Name is required", error);
    }

    @Test
    @DisplayName("Verify Error Message When Last Name Is Missing")
    void verifyErrorMessageForMissingLastName() {
        checkoutStepOnePage.enterFirstName("John");
        checkoutStepOnePage.enterPostalCode("12345");
        checkoutStepOnePage.clickContinue();
        String error = checkoutStepOnePage.getErrorMessage();
        assertEquals("Error: Last Name is required", error);
    }

    @Test
    @DisplayName("Verify Error Message When Postal Code Is Missing")
    void verifyErrorMessageForMissingPostalCode() {
        checkoutStepOnePage.enterFirstName("John");
        checkoutStepOnePage.enterLastName("Doe");
        checkoutStepOnePage.clickContinue();
        String error = checkoutStepOnePage.getErrorMessage();
        assertEquals("Error: Postal Code is required", error);
    }

    @Test
    @DisplayName("Cancel Checkout and Return to Cart")
    void cancelCheckout() {
        checkoutStepOnePage.clickCancel();
        assertTrue(page.url().contains("cart.html"), "Did not return to cart page");
    }
}
