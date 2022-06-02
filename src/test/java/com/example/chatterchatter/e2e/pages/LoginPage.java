package com.example.chatterchatter.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Optional;

public class LoginPage {
    private static final String BASE_PATH = "/login";
    private static final String SUCCESSFUL_LOGIN_URL = "/chat";
    private static final String TOKEN_SELECTOR = "token";
    private static final String PLACEHOLDER_SELECTOR = "placeholder";
    private static final String USERNAME_PLACEHOLDER = "Username";
    private static final String PASSWORD_PLACEHOLDER = "Password";

    public static LoginPage create(WebDriver driver, String url) {
        driver.navigate().to(url + BASE_PATH);
        return PageFactory.initElements(driver, LoginPage.class);
    }

    @FindBy(tagName = "input")
    private List<WebElement> formInputs;

    @FindBy(tagName = "h3")
    private WebElement loginTitle;

    @FindBy(tagName = "button")
    private WebElement loginButton;

    @FindBy(tagName = "notifier-notification")
    private WebElement notificationMessage;

    public Optional<WebElement> getTitle() {
        return Optional.ofNullable(loginTitle);
    }

    public List<WebElement> getLoginInputs() {
        return formInputs;
    }

    public Optional<WebElement> getUsernameInput() {
        return this.formInputs.stream().filter(i -> USERNAME_PLACEHOLDER.equals(i.getAttribute(PLACEHOLDER_SELECTOR))).findFirst();
    }

    public Optional<WebElement> getPasswordInput() {
        return this.formInputs.stream().filter(i -> PASSWORD_PLACEHOLDER.equals(i.getAttribute(PLACEHOLDER_SELECTOR))).findFirst();
    }

    public Optional<WebElement> getLoginButton() {
        return Optional.ofNullable(loginButton);
    }

    public void waitUntilLoginProcessed(WebDriver webDriver) {
        Wait<WebDriver> wait = new WebDriverWait(webDriver, 10);
        wait.until(driver -> {
            String currentURL = driver.getCurrentUrl();
            return currentURL.contains(SUCCESSFUL_LOGIN_URL);
        });
    }

    public Optional<WebElement> getNotificationMessage() {
        return Optional.ofNullable(notificationMessage);
    }

    public void waitUntilNotificationAppears(WebDriver webDriver) {
        Wait<WebDriver> wait = new WebDriverWait(webDriver, 10);
        wait.until(driver -> notificationMessage.isDisplayed());
    }

    public String getTokenFromLocalStorage(WebDriver webDriver) {
        WebStorage webStorage = (WebStorage) webDriver;
        LocalStorage localStorage = webStorage.getLocalStorage();
        return localStorage.getItem(TOKEN_SELECTOR);
    }

}
