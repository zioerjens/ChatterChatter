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
    private static final String SUCCESSFUL_LOGIN_URL = "/users/management";

    public static LoginPage create(WebDriver driver, String url) {
        driver.navigate().to(url +BASE_PATH);
        return PageFactory.initElements(driver, LoginPage.class);
    }

    @FindBy(tagName = "input")
    private List<WebElement> formInputs;

    @FindBy(tagName = "h3")
    private WebElement loginTitle;

    @FindBy(tagName = "button")
    private WebElement loginButton;

    public Optional<WebElement> getTitle() {
        return Optional.ofNullable(loginTitle);
    }

    public List<WebElement> getLoginInputs() {
        return formInputs;
    }

    public Optional<WebElement> getUsernameInput(){
        return this.formInputs.stream().filter(i -> "Username".equals(i.getAttribute("placeholder"))).findFirst();
    }

    public Optional<WebElement> getPasswordInput(){
        return this.formInputs.stream().filter(i -> "Password".equals(i.getAttribute("placeholder"))).findFirst();
    }

    public Optional<WebElement> getLoginButton() {
        return Optional.ofNullable(loginButton);
    }

    public void waitUntilLoginProcessed(WebDriver webDriver){
        Wait<WebDriver> wait = new WebDriverWait(webDriver, 10);
        wait.until(driver -> {
            String currentURL = driver.getCurrentUrl();
            return currentURL.contains(SUCCESSFUL_LOGIN_URL);
        });
    }

    public String getTokenFromLocalStorage(WebDriver webDriver){
        WebStorage webStorage = (WebStorage) webDriver;
        LocalStorage localStorage = webStorage.getLocalStorage();
        return localStorage.getItem("token");
    }


}
