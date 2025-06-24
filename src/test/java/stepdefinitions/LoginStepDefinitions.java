package stepdefinitions;

import io.cucumber.java.en.*;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import pages.LoginPage;
import hooks.Hooks;

public class LoginStepDefinitions {

    private WebDriver driver = Hooks.driver;
    private LoginPage loginPage = new LoginPage(driver);
    private HomePage homePage = new HomePage(driver);
    private final String baseUrl = "https://www.saucedemo.com/";

    @Given("User is on the login page")
    public void userIsOnLoginPage() {
        loginPage.openLoginPage(baseUrl);
    }

    @When("User logs in with username {string} and password {string}")
    public void userLogsIn(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
    }

    @Then("User should see {string}")
    public void userShouldSee(String expected) {
        if (loginPage.isErrorMessageDisplayed()) {
            System.out.println("Error message: " + loginPage.getErrorMessage());
            System.out.println("expected message: " + expected);
            Assert.assertTrue(loginPage.getErrorMessage().startsWith(expected));
        } else {
            Assert.assertTrue(homePage.isLoaded());
            Assert.assertEquals(homePage.getTitle(), expected);
        }
    }
}
