package org.example.pages;

import com.microsoft.playwright.Page;

public class CheckoutStepOnePage {
    private final Page page;

    private final String firstNameInput = "[data-test='firstName']";
    private final String lastNameInput = "[data-test='lastName']";
    private final String postalCodeInput = "[data-test='postalCode']";
    private final String continueButton = "[data-test='continue']";
    private final String cancelButton = "[data-test='cancel']";
    private final String errorMessage = "[data-test='error']";

    public CheckoutStepOnePage(Page page) {
        this.page = page;
    }

    public void enterFirstName(String firstName) {
        page.fill(firstNameInput, firstName);
    }

    public void enterLastName(String lastName) {
        page.fill(lastNameInput, lastName);
    }

    public void enterPostalCode(String postalCode) {
        page.fill(postalCodeInput, postalCode);
    }

    public void clickContinue() {
        page.click(continueButton);
    }

    public void clickCancel() {
        page.click(cancelButton);
    }

    public String getErrorMessage() {
        if (page.isVisible(errorMessage)) {
            return page.textContent(errorMessage);
        }
        return "";
    }

    public boolean isOnCheckoutStepOnePage() {
        return page.url().contains("checkout-step-one.html");
    }

    public void fillCheckoutInfo(String firstName, String LastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(LastName);
        enterPostalCode(postalCode);
    }
}
