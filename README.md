# Selenium + Java + TestNG Automation Framework

An end-to-end UI automation framework built for testing the **OrangeHRM demo application**
(https://opensource-demo.orangehrmlive.com) — covers login and employee search flows.

## Tech Stack
- **Java 11**
- **Selenium WebDriver 4**
- **TestNG** — test runner, assertions, suite management
- **Maven** — build & dependency management
- **WebDriverManager** — automatic browser driver binaries (no manual chromedriver downloads)
- **ExtentReports** — rich HTML test reports with pass/fail/skip status
- **Log4j2** — structured logging to console and file
- **Apache POI** — reads Excel test data for data-driven testing
- **GitHub Actions** — CI pipeline that runs the suite headless on every push

## Design
- **Page Object Model (POM)**: each page (`LoginPage`, `DashboardPage`, `EmployeeSearchPage`)
  encapsulates its own locators and actions. Test classes never touch a `By` or raw `WebElement`.
- **BaseTest**: centralizes WebDriver setup/teardown via TestNG's `@BeforeMethod`/`@AfterMethod`,
  so every test class starts with a clean browser session.
- **ConfigReader**: externalizes environment values (URL, credentials, browser, timeouts) into
  `config.properties` — no hardcoded strings in test logic.
- **TestListener**: implements `ITestListener` to auto-log every test's result into the
  Extent report, no manual logging calls needed inside `@Test` methods.

## Project Structure
```
selenium-framework/
├── src/test/java/
│   ├── base/BaseTest.java
│   ├── pages/            (LoginPage, DashboardPage, EmployeeSearchPage)
│   ├── tests/             (LoginTest, SearchTest, DataDrivenLoginTest)
│   ├── utils/             (ConfigReader, ExtentReportManager, ExcelUtils, DataProviders)
│   └── listeners/TestListener.java
├── src/test/resources/
│   ├── config.properties
│   ├── log4j2.xml
│   └── testdata/LoginData.xlsx
├── testng.xml
├── pom.xml
└── .github/workflows/maven.yml
```

## How to Run
```bash
mvn clean test
```
Reports are generated under `/reports/TestReport_<timestamp>.html` after each run.
Logs are written to `/logs/automation.log`.

## CI/CD
Every push to `main` triggers the GitHub Actions workflow (`.github/workflows/maven.yml`),
which runs the suite headless on Ubuntu and uploads the Extent report + Surefire results
as build artifacts.

## Test Coverage
| Test Class          | Scenario                                                |
|---------------------|----------------------------------------------------------|
| LoginTest           | Valid login, invalid credentials, blank password         |
| SearchTest          | Search returns results, search with no matches           |
| DataDrivenLoginTest | Multiple username/password/expected-result combinations, sourced from `testdata/LoginData.xlsx` via `@DataProvider` |

## Data-Driven Testing
`DataDrivenLoginTest` doesn't hardcode credentials — it reads rows from
`src/test/resources/testdata/LoginData.xlsx` through `ExcelUtils` (Apache POI)
and TestNG's `@DataProvider` (defined centrally in `utils.DataProviders`).
Adding a new test case means adding a new Excel row (username, password, expectedResult) —
no Java code changes needed. TestNG runs the test method once per row automatically.

## Possible Extensions (good interview talking points)
- Cross-browser execution (Firefox/Edge) driven by `config.properties`
- Parallel execution via `testng.xml` `parallel="methods"`
- Dockerized Selenium Grid for CI
- Screenshot-on-failure attached directly into the Extent report
