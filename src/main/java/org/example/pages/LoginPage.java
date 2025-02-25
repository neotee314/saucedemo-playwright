package org.example.pages;

import com.microsoft.playwright.Page;

public class LoginPage {
    private final Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    public void login(String username, String password) {
        page.locator("#user-name").fill(username);
        page.locator("#password").fill(password);
        page.click("#login-button");
    }

    public boolean isErrorDisplayed() {
        return page.isVisible("[data-test='error']");
    }

    public String getErrorMessage() {
        if (isErrorDisplayed()) {
            return page.textContent("[data-test='error']");
        }
        return "";
    }

    public boolean isLoggedIn() {
        return page.url().contains("inventory.html");
    }
}
