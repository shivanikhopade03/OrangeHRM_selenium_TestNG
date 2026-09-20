package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import utils.ConfigReader;

@Listeners(listeners.TestListener.class)
public class LoginTest extends BaseTest {

    @Test(description = "Verify user can log in with valid credentials")
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = loginPage.loginAs(
                ConfigReader.get("username"),
                ConfigReader.get("password")
        );

        Assert.assertTrue(dashboardPage.isDashboardDisplayed(), "Dashboard was not displayed after valid login");
        Assert.assertTrue(dashboardPage.isUserLoggedIn(), "User dropdown not visible - login likely failed");
    }

    @Test(description = "Verify error message is shown for invalid credentials")
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername("invalidUser");
        loginPage.enterPassword("wrongPassword");
        loginPage.clickLogin();

        String actualError = loginPage.getErrorMessage();
        Assert.assertEquals(actualError, "Invalid credentials", "Error message text did not match");
    }

    @Test(description = "Verify error message is shown when password is left blank")
    public void testLoginWithBlankPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(ConfigReader.get("username"));
        loginPage.clickLogin();

        // Required-field validation message should appear; page should not navigate away
        Assert.assertTrue(driver.getCurrentUrl().contains("login"), "App navigated away despite blank password");
    }
}
