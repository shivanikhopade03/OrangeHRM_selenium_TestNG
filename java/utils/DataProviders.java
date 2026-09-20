package utils;

import org.testng.annotations.DataProvider;

/**
 * Central home for every @DataProvider used across test classes.
 * Keeping providers here (instead of inside each test class) means
 * the data source can be swapped (Excel/CSV/DB) without touching tests.
 */
public class DataProviders {

    private static final String LOGIN_DATA_PATH =
            "src/test/resources/testdata/LoginData.xlsx";

    /**
     * Supplies rows from LoginData.xlsx as {username, password, expectedResult}.
     * TestNG calls the test method once per row, in parallel-safe fashion
     * since BaseTest creates a fresh driver per test method.
     */
    @DataProvider(name = "loginData")
    public static Object[][] loginData() {
        return ExcelUtils.getTestData(LOGIN_DATA_PATH, "LoginData");
    }
}
