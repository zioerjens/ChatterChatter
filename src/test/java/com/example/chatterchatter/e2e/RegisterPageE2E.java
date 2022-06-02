package com.example.chatterchatter.e2e;

import com.example.chatterchatter.e2e.pages.RegisterPage;
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
public class RegisterPageE2E {

    private static final String APP_URL = "http://localhost:4200";

    @Autowired
    private WebDriver webDriver;

    private RegisterPage registerPage;

    @BeforeEach
    private void setUp() {
        this.registerPage = RegisterPage.create(webDriver, APP_URL);
    }

    @Test
    public void registerPage_loadContext_OK() {
        WebElement title = registerPage.getTitle().get();
        WebElement loginBtn = registerPage.getRegisterButton().get();
        List<WebElement> inputs = registerPage.getRegisterInputs();

        assertEquals("ChatterChatter - Register", title.getText());
        assertEquals("Register", loginBtn.getText());
        assertEquals(6, inputs.size());
        assertEquals("Username", inputs.get(0).getAttribute("placeholder"));
        assertEquals("Password", inputs.get(4).getAttribute("placeholder"));
        assertEquals("Repeat password", inputs.get(5).getAttribute("placeholder"));
    }

    @Test
    public void registerPage_validFormInputs_OK() {
        WebElement registerBtn = registerPage.getRegisterButton().get();
        WebElement usernameInput = registerPage.getUsernameInput().get();
        WebElement emailInput = registerPage.getEmailInput().get();
        WebElement firstNameInput = registerPage.getFirstnameInput().get();
        WebElement lastNameInput = registerPage.getLastnameInput().get();
        WebElement passwordInput = registerPage.getPasswordInput().get();
        WebElement passwordRepeatInput = registerPage.getPasswordRepeatInput().get();

        usernameInput.sendKeys("maria");
        firstNameInput.sendKeys("Maria");
        lastNameInput.sendKeys("Bernasconi");
        emailInput.sendKeys("maria@berna.ch");
        passwordInput.sendKeys("password");
        passwordRepeatInput.sendKeys("password");
        registerBtn.click();

        registerPage.waitUntilRegisterProcessed(webDriver);

        assertTrue(webDriver.getCurrentUrl().contains("/login"));
    }

    @Test
    public void registerPage_unequalPasswords_NOK() {
        WebElement passwordInput = registerPage.getPasswordInput().get();
        WebElement passwordRepeatInput = registerPage.getPasswordRepeatInput().get();

        passwordInput.sendKeys("password");
        passwordRepeatInput.sendKeys("xyz");

        WebElement loginBtn = registerPage.getRegisterButton().get();
        assertFalse(loginBtn.isEnabled());

        List<WebElement> messages = registerPage.getValidationMessages();
        assertFalse(messages.isEmpty());
        assertTrue(messages.stream().anyMatch(m -> "Please repeat the password".equals(m.getText())));
        assertTrue(webDriver.getCurrentUrl().contains("/register"));
    }

    @AfterEach
    public void cleanUp() {
        webDriver.quit();
    }
}
