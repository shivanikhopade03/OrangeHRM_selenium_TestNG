package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;

/**
 * Page Object for the dashboard shown right after a successful login.
 * Used both to verify login succeeded and as the entry point to
 * navigate into other modules such as employee search.
 */
public class DashboardPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By dashboardHeader = By.xpath("//h6[text()='Dashboard']");
    private final By userDropdown = By.className("oxd-userdropdown-tab");
    private final By pimMenuLink = By.xpath("//span[text()='PIM']");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("explicit.wait")));
    }

    public boolean isDashboardDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardHeader)).isDisplayed();
    }

    public boolean isUserLoggedIn() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(userDropdown)).isDisplayed();
    }

    public EmployeeSearchPage goToEmployeeSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(pimMenuLink)).click();
        return new EmployeeSearchPage(driver);
    }
}
