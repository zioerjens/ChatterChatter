package com.example.chatterchatter.e2e;

import com.example.chatterchatter.e2e.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LoginPageE2E {

    private static final String APP_URL = "http://localhost:4200";

    @Autowired
    private WebDriver webDriver;

    private LoginPage loginPage;

    @BeforeEach
    private void setUp() {
        this.loginPage = LoginPage.create(webDriver, APP_URL);
    }

    @Test
    public void loginPage_loadContext_OK() {
        WebElement title = loginPage.getTitle().get();
        WebElement loginBtn = loginPage.getLoginButton().get();
        List<WebElement> inputs = loginPage.getLoginInputs();

        assertEquals("ChatterChatter - Login", title.getText());
        assertEquals("Login", loginBtn.getText());
        assertEquals(2, inputs.size());
        assertEquals("Username", inputs.get(0).getAttribute("placeholder"));
        assertEquals("Password", inputs.get(1).getAttribute("placeholder"));
    }

    @Test
    public void loginPage_validUsernameAndPassword_OK() {
        WebElement loginBtn = loginPage.getLoginButton().get();
        WebElement usernameInput = loginPage.getUsernameInput().get();
        WebElement passwordInput = loginPage.getPasswordInput().get();

        usernameInput.sendKeys("admin");
        passwordInput.sendKeys("admin");
        loginBtn.click();

        loginPage.waitUntilLoginProcessed(webDriver);

        assertTrue(webDriver.getCurrentUrl().contains("/users/management"));
        assertNotNull(loginPage.getTokenFromLocalStorage(webDriver));
    }

    @Test
    public void loginPage_invalidUsernameAndPassword_NOK() {
        WebElement loginBtn = loginPage.getLoginButton().get();
        WebElement usernameInput = loginPage.getUsernameInput().get();
        WebElement passwordInput = loginPage.getPasswordInput().get();

        usernameInput.sendKeys("admin2");
        passwordInput.sendKeys("admin2");
        loginBtn.click();

        loginPage.waitUntilNotificationAppears(webDriver);
        WebElement notification = loginPage.getNotificationMessage().get();

        assertTrue(webDriver.getCurrentUrl().contains("/login"));
        assertNull(loginPage.getTokenFromLocalStorage(webDriver));
        assertNotNull(notification);

    }

    @AfterEach
    public void cleanUp() {
        webDriver.quit();
    }
}
