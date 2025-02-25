package org.example.tests;

import com.microsoft.playwright.*;
import lombok.SneakyThrows;
import org.example.pages.CartPage;
import org.example.pages.InventoryPage;
import org.example.pages.LoginPage;
import org.junit.jupiter.api.*;

import java.util.*;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CartTest extends BaseTest {
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private static final Logger logger = Logger.getLogger(CartTest.class.getName());


    @BeforeEach
    void initTest() {
        page.navigate("https://www.saucedemo.com/");
        LoginPage loginPage = new LoginPage(page);
        inventoryPage = new InventoryPage(page);
        cartPage = new CartPage(page);
        loginPage.login("standard_user", "secret_sauce");
        assertTrue(loginPage.isLoggedIn(), "Login should be successful.");

        List<String> allItems = inventoryPage.getAllInventoryItems();
        assertTrue(allItems.size() > 1, "At least 2 items should be available.");

        inventoryPage.addItemToCart(allItems.get(0));
        inventoryPage.addItemToCart(allItems.get(1));

        inventoryPage.goToCart();
    }

    @Test
    @DisplayName("Verify Items in Cart")
    void verifyItemsInCartTest() {
        initTest();
        List<String> cartItems = cartPage.getCartItems();
        assertTrue(cartItems.size() >= 1, "Cart should have at least 1 item.");
        logger.info("Items in cart: " + cartItems);
    }

    @Test
    @DisplayName("Remove an Item from Cart")
    void removeItemFromCartTest() {
        initTest();
        List<String> cartItems = cartPage.getCartItems();
        String itemToRemove = cartItems.get(0);
        cartPage.removeItemByName(itemToRemove);
        List<String> updatedCartItems = cartPage.getCartItems();
        assertFalse(updatedCartItems.contains(itemToRemove), "Removed item should not be in cart.");
        logger.info("Removed item: " + itemToRemove);
    }

    @Test
    @DisplayName("Continue Shopping and Add Another Item")
    void continueShoppingTest() {
        cartPage.clickContinueShopping();
        List<String> allItems = inventoryPage.getAllInventoryItems();
        String newItem = allItems.get(new Random().nextInt(allItems.size()));
        inventoryPage.addItemToCart(newItem);
        inventoryPage.goToCart();
        List<String> cartItems = cartPage.getCartItems();
        assertTrue(cartItems.contains(newItem), "New item should be in cart.");
        logger.info("Added new item to cart: " + newItem);
    }

    @Test
    @SneakyThrows
    @DisplayName("Verify Item Prices in Cart")
    void verifyItemPricesTest() {
        List<String> itemPrices = cartPage.getItemPrices();
        assertTrue(itemPrices.size() >= 1, "Cart should have at least 1 item to check prices.");
        for (String price : itemPrices) {
            assertTrue(price.startsWith("$"), "Price should start with '$'. Found: " + price);
            String numericPrice = price.replace("$", "").trim();
            Double.parseDouble(numericPrice);
            logger.info("Verified price: " + price);
        }
    }

    @Test
    @DisplayName("Compare Random Item Prices Between Inventory and Cart")
    void compareInventoryAndCartPricesTest() {
        List<String> cartItems = cartPage.getCartItems();
        List<String> cartPrices = cartPage.getItemPrices();
        Map<String, String> inventoryPrices = new HashMap<>();

        assertTrue(cartItems.size() >= 1, "Cart should have at least 1 item.");
        cartPage.clickContinueShopping();

        for (String cartItem : cartItems) {
            String inventoryPrice = inventoryPage.getItemPrice(cartItem);
            inventoryPrices.put(cartItem, inventoryPrice);
            logger.info("Fetched price from Inventory for item: " + cartItem + " -> " + inventoryPrice);
        }

        assertEquals(inventoryPrices.size(), cartItems.size(), "Number of items in cart should match selected items.");

        for (int i = 0; i < cartItems.size(); i++) {
            String cartItemName = cartItems.get(i);
            String cartItemPrice = cartPrices.get(i);
            String inventoryPrice = inventoryPrices.get(cartItemName);

            assertNotNull(inventoryPrice, "Item " + cartItemName + " should exist in Inventory prices.");
            assertEquals(inventoryPrice, cartItemPrice, "Price mismatch for item: " + cartItemName);
            logger.info("Verified price for item: " + cartItemName + " Inventory: " + inventoryPrice + " Cart: " + cartItemPrice);
        }
    }


    @Test
    @DisplayName("Proceed to Checkout")
    void checkoutTest() {
        cartPage.clickCheckout();
        assertTrue(page.url().contains("checkout-step-one"), "Should navigate to checkout page.");
        logger.info("Navigated to checkout page.");
    }
}
