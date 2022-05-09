package com.example.chatterchatter.e2e;

import com.example.chatterchatter.e2e.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ChatterChatterE2ETest {

    @LocalServerPort
    int port;

    private static final String APP_URL = "http://localhost:4200";

    private LoginPage loginPage;

    @Autowired
    private WebDriver webDriver;

    @BeforeEach
    private void setUp(){
        this.loginPage = LoginPage.create(webDriver, APP_URL);
    }

    @Test
    public void loginPage_loadContext_OK() {
        WebElement title = loginPage.getTitle().get();
        WebElement loginBtn = loginPage.getLoginButton().get();
        List<WebElement> inputs = loginPage.getLoginInputs();

        assertEquals("ChatterChatter - Login",title.getText());
        assertEquals("Login",loginBtn.getText());
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

    @AfterEach
    public void cleanUp(){
        webDriver.quit();
    }
}
