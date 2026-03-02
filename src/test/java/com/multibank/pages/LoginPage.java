package com.multibank.pages;

import com.multibank.utils.ConfigReader;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;

public class LoginPage {

    private final Page page;
    private final Locator emailInput;
    private final Locator passwordInput;
    private final Locator loginButton;

    public LoginPage(Page page) {
        this.page = page;
        this.emailInput = page.getByLabel("Email address");
        this.passwordInput = page.getByLabel("Password");
        this.loginButton = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Log In"));
    }

    @Step("Open login page")
    public LoginPage open() {
        page.navigate(ConfigReader.get("baseUrl"));
        return this;
    }

    @Step("Enter username (email): {username}")
    public LoginPage enterUsername(String username) {
        emailInput.fill(username);
        return this;
    }

    @Step("Enter password")
    public LoginPage enterPassword(String password) {
        passwordInput.fill(password);
        return this;
    }

    @Step("Click login button")
    public void clickLogin() {
        loginButton.click();
    }

    @Step("Login with username: {username}")
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        page.waitForURL(ConfigReader.get("baseUrl"));
    }
}

