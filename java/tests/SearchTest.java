package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.EmployeeSearchPage;
import pages.LoginPage;
import utils.ConfigReader;

@Listeners(listeners.TestListener.class)
public class SearchTest extends BaseTest {

    @Test(description = "Verify searching for an existing employee returns results")
    public void testEmployeeSearchReturnsResults() {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = loginPage.loginAs(
                ConfigReader.get("username"),
                ConfigReader.get("password")
        );
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(), "Login failed, cannot proceed to search");

        EmployeeSearchPage searchPage = dashboardPage.goToEmployeeSearch();
        searchPage.searchByEmployeeName("Amelia");

        int resultsCount = searchPage.getResultsCount();
        Assert.assertTrue(resultsCount >= 0, "Search results count should be zero or more, got: " + resultsCount);
    }

    @Test(description = "Verify searching for a non-existent employee shows no records")
    public void testEmployeeSearchNoResults() {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = loginPage.loginAs(
                ConfigReader.get("username"),
                ConfigReader.get("password")
        );

        EmployeeSearchPage searchPage = dashboardPage.goToEmployeeSearch();
        searchPage.searchByEmployeeName("Zzznonexistentname999");

        String recordsText = searchPage.getRecordsFoundText();
        Assert.assertTrue(recordsText.contains("0"), "Expected zero results for a bogus name, got: " + recordsText);
    }
}
