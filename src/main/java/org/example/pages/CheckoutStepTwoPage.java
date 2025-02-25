package org.example.pages;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;

import java.util.List;
import java.util.stream.Collectors;

public class CheckoutStepTwoPage {
    private final Page page;

    private final String cartItemLocator = ".cart_item";
    private final String itemNameLocator = "[data-test='inventory-item-name']";
    private final String itemPriceLocator = ".inventory_item_price";
    private final String summarySubtotalLocator = ".summary_subtotal_label";
    private final String summaryTaxLocator = ".summary_tax_label";
    private final String summaryTotalLocator = ".summary_total_label";
    private final String finishButton = "[data-test='finish']";
    private final String cancelButton = "[data-test='cancel']";

    public CheckoutStepTwoPage(Page page) {
        this.page = page;
    }

    public List<String> getCartItems() {
        return page.querySelectorAll("[data-test='inventory-item-name']").stream()
                .map(ElementHandle::innerText)
                .collect(Collectors.toList());
    }


    public List<String> getItemPrices() {
        return page.querySelectorAll(itemPriceLocator).stream()
                .map(ElementHandle::innerText)
                .collect(Collectors.toList());
    }

    public double getSubtotal() {
        String subtotalText = page.textContent(summarySubtotalLocator).replace("Item total: $", "").trim();
        return Double.parseDouble(subtotalText);
    }

    public double getTax() {
        String taxText = page.textContent(summaryTaxLocator).replace("Tax: $", "").trim();
        return Double.parseDouble(taxText);
    }

    public double getTotal() {
        String totalText = page.textContent(summaryTotalLocator).replace("Total: $", "").trim();
        return Double.parseDouble(totalText);
    }

    public void clickFinish() {
        page.click(finishButton);
    }

    public void clickCancel() {
        page.click(cancelButton);
    }

    public boolean isOnCheckoutStepTwoPage() {
        return page.url().contains("checkout-step-two.html");
    }
}
