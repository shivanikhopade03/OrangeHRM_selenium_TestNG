package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;
import java.util.List;

/**
 * Page Object for the Employee List / search screen inside the PIM module.
 * Covers entering a search term and reading back the result rows.
 */
public class EmployeeSearchPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By employeeNameInput = By.xpath("(//input[@placeholder='Type for hints...'])[1]");
    private final By searchButton = By.xpath("//button[@type='submit']");
    private final By resultRows = By.xpath("//div[@class='oxd-table-body']//div[@role='row']");
    private final By recordsFoundText = By.className("orangehrm-horizontal-padding");

    public EmployeeSearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("explicit.wait")));
    }

    public void searchByEmployeeName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameInput)).sendKeys(name);
        driver.findElement(searchButton).click();
    }

    public int getResultsCount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(resultRows));
        List<org.openqa.selenium.WebElement> rows = driver.findElements(resultRows);
        return rows.size();
    }

    public String getRecordsFoundText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(recordsFoundText)).getText();
    }
}
