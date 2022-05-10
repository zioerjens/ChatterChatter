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

public class RegisterPage {
    private static final String BASE_PATH = "/register";
    private static final String SUCCESSFUL_REGISTER_URL = "/login";
    private static final String PLACEHOLDER_SELECTOR = "placeholder";
    private static final String USERNAME_PLACEHOLDER = "Username";
    private static final String PASSWORD_PLACEHOLDER = "Password";
    private static final String REPEAT_PASSWORD_PLACEHOLDER = "Repeat password";
    private static final String FIRSTNAME_PLACEHOLDER = "Firstname";
    private static final String LASTNAME_PLACEHOLDER = "Lastname";
    private static final String EMAIL_PLACEHOLDER = "E-Mail";
    private static final String VALIDATION_MSG_CLASS_SELECTOR = "help-block";

    public static RegisterPage create(WebDriver driver, String url) {
        driver.navigate().to(url + BASE_PATH);
        return PageFactory.initElements(driver, RegisterPage.class);
    }

    @FindBy(tagName = "input")
    private List<WebElement> formInputs;

    @FindBy(tagName = "h3")
    private WebElement registerTitle;

    @FindBy(tagName = "button")
    private WebElement registerButton;

    @FindBy(tagName = "notifier-notification")
    private WebElement notificationMessage;

    @FindBy(className = VALIDATION_MSG_CLASS_SELECTOR)
    private List<WebElement> validationMessages;

    public Optional<WebElement> getTitle() {
        return Optional.ofNullable(registerTitle);
    }

    public List<WebElement> getRegisterInputs() {
        return formInputs;
    }

    public Optional<WebElement> getUsernameInput() {
        return this.formInputs.stream().filter(i -> USERNAME_PLACEHOLDER.equals(i.getAttribute(PLACEHOLDER_SELECTOR))).findFirst();
    }

    public Optional<WebElement> getEmailInput() {
        return this.formInputs.stream().filter(i -> EMAIL_PLACEHOLDER.equals(i.getAttribute(PLACEHOLDER_SELECTOR))).findFirst();
    }

    public Optional<WebElement> getFirstnameInput() {
        return this.formInputs.stream().filter(i -> FIRSTNAME_PLACEHOLDER.equals(i.getAttribute(PLACEHOLDER_SELECTOR))).findFirst();
    }

    public Optional<WebElement> getLastnameInput() {
        return this.formInputs.stream().filter(i -> LASTNAME_PLACEHOLDER.equals(i.getAttribute(PLACEHOLDER_SELECTOR))).findFirst();
    }

    public Optional<WebElement> getPasswordInput() {
        return this.formInputs.stream().filter(i -> PASSWORD_PLACEHOLDER.equals(i.getAttribute(PLACEHOLDER_SELECTOR))).findFirst();
    }

    public Optional<WebElement> getPasswordRepeatInput() {
        return this.formInputs.stream().filter(i -> REPEAT_PASSWORD_PLACEHOLDER.equals(i.getAttribute(PLACEHOLDER_SELECTOR))).findFirst();
    }

    public Optional<WebElement> getRegisterButton() {
        return Optional.ofNullable(registerButton);
    }

    public List<WebElement> getValidationMessages(){
        return validationMessages;
    }

    public void waitUntilRegisterProcessed(WebDriver webDriver) {
        Wait<WebDriver> wait = new WebDriverWait(webDriver, 10);
        wait.until(driver -> {
            String currentURL = driver.getCurrentUrl();
            return currentURL.contains(SUCCESSFUL_REGISTER_URL);
        });
    }

    public Optional<WebElement> getNotificationMessage() {
        return Optional.ofNullable(notificationMessage);
    }

    public void waitUntilNotificationAppears(WebDriver webDriver) {
        Wait<WebDriver> wait = new WebDriverWait(webDriver, 10);
        wait.until(driver -> notificationMessage.isDisplayed());
    }

}
