package org.example.tests;

import org.example.pages.*;
import org.junit.jupiter.api.*;


import java.lang.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest extends BaseTest {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;

    @BeforeEach
    void init() {
        page.navigate("https://www.saucedemo.com/");
        loginPage = new LoginPage(page);
        inventoryPage = new InventoryPage(page);

        loginPage.login("standard_user", "secret_sauce");
        assertTrue(loginPage.isLoggedIn(), "Login failed!");
    }

    @Test
    @DisplayName("Add and Remove All Items")
    void testAddAndRemoveAllItemsDynamically() {
        List<String> items = inventoryPage.getAllInventoryItems();
        System.out.println("Items Found: " + items);
        assertFalse(items.isEmpty(), "No items found on the inventory page");
        int i = 1;
        for (String item : items) {
            inventoryPage.addItemToCart(item);
            assertEquals(i, inventoryPage.getCartItemCount());
            i++;
        }

        assertEquals(items.size(), inventoryPage.getCartItemCount(), "Cart count mismatch after adding items");

        for (String item : items) {
            inventoryPage.removeItemFromCart(item);
            assertEquals(i - 2, inventoryPage.getCartItemCount());
            i--;
        }
        assertEquals(0, inventoryPage.getCartItemCount(), "Cart is not empty after removing all items");
    }


    @Test
    @DisplayName("Test All Items Navigation")
    void testAllItemsNavigation() {
        inventoryPage.openMenu();
        inventoryPage.clickAllItems();
        assertTrue(page.url().contains("inventory.html"), "All Items navigation failed");
    }

    @Test
    @DisplayName("Test About Page Navigation")
    void testAboutPageNavigation() {
        inventoryPage.openMenu();
        inventoryPage.clickAbout();
        assertTrue(page.url().contains("saucelabs.com"), "About page navigation failed");
        page.goBack();
    }

    @Test
    @DisplayName("Test Logout Functionality")
    void testLogoutFunctionality() {
        inventoryPage.openMenu();
        inventoryPage.clickLogout();
        assertTrue(page.url().contains("saucedemo.com"), "Logout failed");
    }



    @Test
    @DisplayName("Test Reset App State")
    void testResetAppState() {
        inventoryPage.openMenu();
        inventoryPage.clickResetAppState();
        assertEquals(0, inventoryPage.getCartItemCount(), "Cart not cleared after reset");
    }
    @Test
    @DisplayName("Sort Items A to Z")
    void testSortItemsAtoZ() {
        inventoryPage.sortItemsAtoZ();
        List<String> sortedItems = inventoryPage.getAllInventoryItems();
        List<String> expectedItems = sortedItems.stream().sorted().toList();
        assertEquals(expectedItems, sortedItems, "Items are not sorted A to Z");
    }

    @Test
    @DisplayName("Sort Items Z to A")
    void testSortItemsZtoA() {
        inventoryPage.sortItemsZtoA();
        List<String> sortedItems = inventoryPage.getAllInventoryItems();
        List<String> expectedItems = sortedItems.stream().sorted(Comparator.reverseOrder()).toList();
        assertEquals(expectedItems, sortedItems, "Items are not sorted Z to A");
    }

    @Test
    @DisplayName("Sort Items Price Low to High")
    void testSortItemsPriceLowToHigh() {
        inventoryPage.sortPriceLowtoHigh();
        List<String> allItems = inventoryPage.getAllInventoryItems();
        List<Double> prices = allItems.stream()
                .map(inventoryPage::getItemPrice)
                .map(price -> Double.parseDouble(price.replace("$", "")))
                .toList();

        List<Double> expectedPrices = new ArrayList<>(prices);
        expectedPrices.sort(Comparator.naturalOrder());

        assertEquals(expectedPrices, prices, "Items are not sorted by price low to high");
    }

    @Test
    @DisplayName("Sort Items Price High to Low")
    void testSortItemsPriceHighToLow() {
        inventoryPage.sortPriceHightoLow();
        List<String> allItems = inventoryPage.getAllInventoryItems();
        List<Double> prices = allItems.stream()
                .map(inventoryPage::getItemPrice)
                .map(price -> Double.parseDouble(price.replace("$", "")))
                .toList();

        List<Double> expectedPrices = new ArrayList<>(prices);
        expectedPrices.sort(Comparator.reverseOrder());

        assertEquals(expectedPrices, prices, "Items are not sorted by price high to low");
    }



    @Test
    @DisplayName("Verify Footer Social Media Links, Handle Popups, and Close Tabs")
    void testFooterSocialMediaLinksWithPopupCloseAndTabClose() {
        String twitterUrl = inventoryPage.clickFooterLinkAndHandlePopup("twitter");
        assertTrue(twitterUrl.contains("x.com/saucelabs"), "Twitter link navigation failed. Current URL: " + twitterUrl);

        String facebookUrl = inventoryPage.clickFooterLinkAndHandlePopup("facebook");
        assertTrue(facebookUrl.contains("facebook.com/saucelabs"), "Facebook link navigation failed. Current URL: " + facebookUrl);

        String linkedinUrl = inventoryPage.clickFooterLinkAndHandlePopup("linkedin");
        assertTrue(linkedinUrl.contains("linkedin.com/company/sauce-labs"), "LinkedIn link navigation failed. Current URL: " + linkedinUrl);
    }


}
