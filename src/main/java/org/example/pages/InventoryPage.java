package org.example.pages;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import lombok.SneakyThrows;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage {
    private final Page page;
    private final BrowserContext context;

    public InventoryPage(Page page) {
        this.page = page;
        this.context = page.context();
    }

    @SneakyThrows
    public List<String> getAllInventoryItems() {
        page.waitForSelector("//div[@data-test='inventory-item']", new Page.WaitForSelectorOptions().setTimeout(10000));

        List<String> items = page.querySelectorAll("//div[@data-test='inventory-item-name']")
                .stream().map(ElementHandle::textContent)
                .collect(Collectors.toList());

        System.out.println("Found Items: " + items);
        return items;

    }

    public void addItemToCart(String itemName) {
        String xpath = "//div[text()='" + itemName + "']//ancestor::div[@data-test='inventory-item']//button";
        page.click(xpath);
    }


    public void removeItemFromCart(String itemName) {
        String xpath = "//div[text()='" + itemName + "']//ancestor::div[@data-test='inventory-item']//button";
        page.click(xpath);
    }

    public int getCartItemCount() {
        String selector = "[data-test='shopping-cart-badge']";

        Locator cartBadge = page.locator(selector);

        if (cartBadge.isVisible()) {
            String countText = cartBadge.textContent().trim();
            return Integer.parseInt(countText);
        }
        return 0;
    }

    public void sortItemsAtoZ() {
        page.selectOption("//select[@data-test='product-sort-container']", "az");
    }

    public void sortItemsZtoA() {
        page.selectOption("//select[@data-test='product-sort-container']", "za");
    }

    public void sortPriceLowtoHigh() {
        page.selectOption("//select[@data-test='product-sort-container']", "lohi");
    }

    public void sortPriceHightoLow() {
        page.selectOption("//select[@data-test='product-sort-container']", "hilo");
    }

    public void openMenu() {
        page.click("#react-burger-menu-btn", new Page.ClickOptions().setForce(true));
    }

    public void clickAllItems() {
        page.click("[data-test='inventory-sidebar-link']");
    }

    public void clickAbout() {
        page.click("[data-test='about-sidebar-link']");
    }

    public void clickLogout() {
        page.click("[data-test='logout-sidebar-link']");
    }

    public void clickResetAppState() {
        page.click("[data-test='reset-sidebar-link']");
    }


    public void clickFooterLink(String socialMedia) {
        page.click("//a[contains(@href, '" + socialMedia + "')]");
    }

    public String clickFooterLinkAndHandlePopup(String socialMedia) {
        Page newPage = context.waitForPage(() -> {
            page.click("//a[contains(@href, '" + socialMedia + "')]");
        });


        newPage.waitForLoadState();

        if (socialMedia.contains("linkedin")) {

            if (newPage.isVisible("//button[@aria-label='Schließen']")) {
                newPage.click("//button[@aria-label='Schließen']");
                newPage.waitForSelector("//button[@aria-label='Schließen']",
                        new Page.WaitForSelectorOptions().setState(WaitForSelectorState.DETACHED));
                System.out.println("LinkedIn login popup closed successfully.");
            } else {
                System.out.println("No LinkedIn login popup detected.");
            }
        }

        String currentUrl = newPage.url();

        newPage.close();

        return currentUrl;
    }

    public void goToCart() {
        page.click("//a[@class='shopping_cart_link']");
    }

    public String getItemPrice(String itemName) {
        String xpath = "//div[text()='" + itemName + "']//ancestor::div[@class='inventory_item']//div[@class='inventory_item_price']";
        return page.textContent(xpath).trim();
    }
}



