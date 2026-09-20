package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import utils.DataProviders;

/**
 * Same login flow as LoginTest, but data-driven: username/password/expected
 * result combinations come from src/test/resources/testdata/LoginData.xlsx
 * instead of being hardcoded, so adding a new case means adding an Excel row,
 * not touching Java code.
 */
@Listeners(listeners.TestListener.class)
public class DataDrivenLoginTest extends BaseTest {

    @Test(dataProvider = "loginData", dataProviderClass = DataProviders.class,
          description = "Data-driven login using multiple credential sets from Excel")
    public void testLoginWithMultipleCredentials(String username, String password, String expectedResult) {
        LoginPage loginPage = new LoginPage(driver);

        if (expectedResult.equalsIgnoreCase("valid")) {
            DashboardPage dashboardPage = loginPage.loginAs(username, password);
            Assert.assertTrue(dashboardPage.isDashboardDisplayed(),
                    "Expected successful login for username: " + username);
        } else {
            loginPage.enterUsername(username);
            loginPage.enterPassword(password);
            loginPage.clickLogin();
            Assert.assertTrue(driver.getCurrentUrl().contains("login"),
                    "Expected login to fail and stay on login page for username: " + username);
        }
    }
}
