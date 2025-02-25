package org.example.pages;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage {
    private final Page page;

    private final String cartItemLocator = ".cart_item";
    private final String cartTitleLocator = ".inventory_item_name";
    private final String cartPriceLocator = ".inventory_item_price";
    private final String removeButtonLocator = "button[id^='remove']";
    private final String continueShoppingButton = "[data-test='continue-shopping']";
    private final String checkoutButton = "[data-test='checkout']";

    public CartPage(Page page) {
        this.page = page;
    }

    public List<String> getCartItems() {
        List<ElementHandle> items = page.querySelectorAll(cartTitleLocator);
        return items.stream()
                .map(ElementHandle::innerText)
                .collect(Collectors.toList());
    }

    public List<String> getItemPrices() {
        List<ElementHandle> prices = page.querySelectorAll(cartPriceLocator);
        return prices.stream()
                .map(ElementHandle::innerText)
                .collect(Collectors.toList());
    }

    public void removeItemByName(String itemName) {
        String xpath = "//div[text()='" + itemName + "']//ancestor::div[@class='cart_item']//button";
        if (page.isVisible(xpath)) {
            page.click(xpath);
            System.out.println(itemName + " removed from cart.");
        } else {
            System.out.println("Item not found in cart: " + itemName);
        }
    }

    public void clickContinueShopping() {
        page.click(continueShoppingButton);
    }

    public void clickCheckout() {
        page.click(checkoutButton);
    }

    public boolean isCartEmpty() {
        return page.querySelectorAll(cartItemLocator).isEmpty();
    }

    public int getCartItemCount() {
        List<ElementHandle> items = page.querySelectorAll(cartItemLocator);
        return items.size();
    }

    public boolean isItemInCart(String itemName) {
        String xpath = "//div[text()='" + itemName + "']";
        return page.isVisible(xpath);
    }



}
