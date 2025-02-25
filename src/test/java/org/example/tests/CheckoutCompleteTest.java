package org.example.tests;

import org.example.pages.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CheckoutCompleteTest extends BaseTest {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutStepOnePage checkoutStepOnePage;
    private CheckoutStepTwoPage checkoutStepTwoPage;
    private CheckoutCompletePage checkoutCompletePage;

    @BeforeAll
    void setUp() {
        loginPage = new LoginPage(page);
        inventoryPage = new InventoryPage(page);
        cartPage = new CartPage(page);
        checkoutStepOnePage = new CheckoutStepOnePage(page);
        checkoutStepTwoPage = new CheckoutStepTwoPage(page);
        checkoutCompletePage = new CheckoutCompletePage(page);
    }

    @BeforeEach
    void init() {
        page.navigate("https://www.saucedemo.com/");
        loginPage.login("standard_user", "secret_sauce");
        assertTrue(loginPage.isLoggedIn(), "Login failed!");

        inventoryPage.addItemToCart("Sauce Labs Backpack");
        inventoryPage.goToCart();
        cartPage.clickCheckout();

        checkoutStepOnePage.enterFirstName("Max");
        checkoutStepOnePage.enterLastName("Mustermann");
        checkoutStepOnePage.enterPostalCode("12345");
        checkoutStepOnePage.clickContinue();

        checkoutStepTwoPage.clickFinish();
        assertTrue(checkoutCompletePage.isOnCheckoutCompletePage(), "Not on Checkout Complete page");
    }

    @Test
    @Order(1)
    @DisplayName("Verify Checkout Complete Page Elements")
    void verifyCheckoutCompleteElements() {
        String header = checkoutCompletePage.getCompleteHeader();
        String completeText = checkoutCompletePage.getCompleteText();
        boolean isImageVisible = checkoutCompletePage.isPonyExpressImageVisible();

        assertEquals("Thank you for your order!", header, "Header text mismatch");
        assertEquals("Your order has been dispatched, and will arrive just as fast as the pony can get there!", completeText, "Complete text mismatch");
        assertTrue(isImageVisible, "Pony Express image is not visible");
    }

    @Test
    @Order(2)
    @DisplayName("Return to Inventory from Checkout Complete")
    void returnToInventory() {
        checkoutCompletePage.clickBackHome();
        assertTrue(page.url().contains("inventory.html"), "Did not navigate back to inventory");
    }
    @Test
    @Order(3)
    @DisplayName("Test Back Home Button Functionality")
    void testBackHomeButton() {
        checkoutCompletePage.clickBackHome();
        assertTrue(page.url().contains("inventory.html"), "Did not navigate back to inventory page");
    }

}
