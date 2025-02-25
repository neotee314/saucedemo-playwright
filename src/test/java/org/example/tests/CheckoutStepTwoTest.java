package org.example.tests;

import org.example.pages.*;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CheckoutStepTwoTest extends BaseTest {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutStepOnePage checkoutStepOnePage;
    private CheckoutStepTwoPage checkoutStepTwoPage;

    @BeforeAll
    void setUp() {
        loginPage = new LoginPage(page);
        inventoryPage = new InventoryPage(page);
        cartPage = new CartPage(page);
        checkoutStepOnePage = new CheckoutStepOnePage(page);
        checkoutStepTwoPage = new CheckoutStepTwoPage(page);
    }

    @BeforeEach
    void init() {
        page.navigate("https://www.saucedemo.com/");
        loginPage.login("standard_user", "secret_sauce");
        assertTrue(loginPage.isLoggedIn(), "Login failed!");

        inventoryPage.addItemToCart("Sauce Labs Backpack");
        inventoryPage.addItemToCart("Sauce Labs Bike Light");
        inventoryPage.goToCart();
        cartPage.clickCheckout();

        checkoutStepOnePage.enterFirstName("John");
        checkoutStepOnePage.enterLastName("Doe");
        checkoutStepOnePage.enterPostalCode("12345");
        checkoutStepOnePage.clickContinue();

        assertTrue(checkoutStepTwoPage.isOnCheckoutStepTwoPage(), "Not on Checkout Step Two page");
    }


    @Test
    @Order(1)
    @DisplayName("Verify Cart Items in Checkout Step Two")
    void verifyCartItems() {
        List<String> cartItems = checkoutStepTwoPage.getCartItems();
        assertTrue(cartItems.contains("Sauce Labs Backpack"));
        assertTrue(cartItems.contains("Sauce Labs Bike Light"));
        assertEquals(2, cartItems.size(), "Cart items count mismatch");
    }

    @Test
    @DisplayName("Verify Subtotal, Tax, and Total")
    void verifySubtotalTaxTotal() {
        List<String> itemPrices = checkoutStepTwoPage.getItemPrices();
        double subtotal = itemPrices.stream()
                .map(price -> Double.parseDouble(price.replace("$", "")))
                .mapToDouble(Double::doubleValue)
                .sum();

        double displayedSubtotal = checkoutStepTwoPage.getSubtotal();
        assertEquals(subtotal, displayedSubtotal, 0.01, "Subtotal mismatch");

        double tax = checkoutStepTwoPage.getTax();
        double expectedTotal = subtotal + tax;

        double displayedTotal = checkoutStepTwoPage.getTotal();
        assertEquals(expectedTotal, displayedTotal, 0.01, "Total amount mismatch");
    }

    @Test
    @DisplayName("Finish Checkout Successfully")
    void finishCheckout() {
        checkoutStepTwoPage.clickFinish();
        assertTrue(page.url().contains("checkout-complete.html"), "Checkout did not complete successfully");
    }

    @Test
    @DisplayName("Cancel Checkout and Return to Inventory")
    void cancelCheckout() {
        checkoutStepTwoPage.clickCancel();
        assertTrue(page.url().contains("inventory.html"), "Did not return to inventory after canceling checkout");
    }
}
