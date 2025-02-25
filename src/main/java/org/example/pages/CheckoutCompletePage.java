package org.example.pages;

import com.microsoft.playwright.Page;

public class CheckoutCompletePage {
    private final Page page;

    private final String completeHeaderLocator = ".complete-header";
    private final String completeTextLocator = ".complete-text";
    private final String ponyExpressImageLocator = ".pony_express";
    private final String backHomeButton = "[data-test='back-to-products']";

    public CheckoutCompletePage(Page page) {
        this.page = page;
    }

    public boolean isOnCheckoutCompletePage() {
        return page.url().contains("checkout-complete.html");
    }

    public String getCompleteHeader() {
        return page.textContent(completeHeaderLocator).trim();
    }

    public String getCompleteText() {
        return page.textContent(completeTextLocator).trim();
    }

    public boolean isPonyExpressImageVisible() {
        return page.isVisible(ponyExpressImageLocator);
    }

    public void clickBackHome() {
        page.click(backHomeButton);
    }
}
