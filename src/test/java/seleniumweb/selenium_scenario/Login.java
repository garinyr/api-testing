package seleniumweb.selenium_scenario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Login {

   private WebDriver driver;
   private WebDriverWait wait;
   String baseUrl = "https://www.saucedemo.com/";

   @BeforeClass
   public void setUp() {
      ChromeOptions options = new ChromeOptions();

      // disable password leak detection pop-up
      Map<String, Object> prefs = new HashMap<>();
      prefs.put("profile.password_manager_leak_detection", false);
      options.setExperimentalOption("prefs", prefs);

      WebDriverManager.chromedriver().setup();
      driver = new ChromeDriver(options);
      driver.manage().window().maximize();
   }

   @AfterClass
   public void tearDown() {
      if (driver != null) {
         driver.quit();
      }
   }

   @Test
   public void testLoginValidCredential() throws InterruptedException {
      driver.get(baseUrl);
      wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

      By usernameInputLocator = By.id("user-name");
      By passwordInputLocator = By.id("password");
      By buttonLoginLocator = By.id("login-button");

      String validEmail = "standard_user";
      String validPassword = "secret_sauce";

      wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInputLocator)).sendKeys(validEmail);
      wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputLocator)).sendKeys(validPassword);
      wait.until(ExpectedConditions.visibilityOfElementLocated(buttonLoginLocator)).click();

      // Add assertions
      By homePageTitleLocator = By.xpath("//span[@class='title']");
      wait.until(ExpectedConditions.visibilityOfElementLocated(homePageTitleLocator));

      // title home page
      String expectedHomePageTitle = "Products";
      String actualHomePageTitle = driver.findElement(homePageTitleLocator).getText();

      Assert.assertEquals(actualHomePageTitle, expectedHomePageTitle);
   }

   @Test(dataProvider = "credentialData")
   public void testLoginInvalidCredential(String username, String password, String expectedErrorMessage)
         throws InterruptedException {
      driver.get(baseUrl);
      wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

      By usernameInputLocator = By.id("user-name");
      By passwordInputLocator = By.id("password");
      By buttonLoginLocator = By.id("login-button");

      wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInputLocator)).sendKeys(username);
      wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputLocator)).sendKeys(password);
      wait.until(ExpectedConditions.visibilityOfElementLocated(buttonLoginLocator)).click();

      // Add assertions
      if (expectedErrorMessage == null) {
         By homePageTitleLocator = By.xpath("//span[@class='title']");
         Assert.assertTrue(isElementPresent(homePageTitleLocator), "Home page title should be displayed");

         // title home page
         String expectedHomePageTitle = "Products";
         String actualHomePageTitle = driver.findElement(homePageTitleLocator).getText();

         Assert.assertEquals(actualHomePageTitle, expectedHomePageTitle);
      } else {
         By errorMessage = By.xpath("//div[@class='error-message-container error']/h3");
         Assert.assertTrue(isElementPresent(errorMessage), "Error message should be displayed");

         String actualErrorMessage = getErrorMessage();
         System.out.println("Actual error message: " + actualErrorMessage);

         Assert.assertEquals(actualErrorMessage, expectedErrorMessage,
               "Error message should be " + expectedErrorMessage);
      }

   }

   @Test(dataProvider = "credentialAnomaliesData")
   public void testLoginAnomaliesCredential(String username, String password, String condition)
         throws InterruptedException {
      driver.get(baseUrl);
      wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));

      By usernameInputLocator = By.id("user-name");
      By passwordInputLocator = By.id("password");
      By buttonLoginLocator = By.id("login-button");

      wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInputLocator)).sendKeys(username);
      wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputLocator)).sendKeys(password);
      wait.until(ExpectedConditions.visibilityOfElementLocated(buttonLoginLocator)).click();

      if (condition.equals("feature")) {
         By homePageTitleLocator = By.xpath("//span[@class='title']");
         Assert.assertTrue(isElementPresent(homePageTitleLocator), "Home page title should be displayed");

         String expectedHomePageTitle = "Products";
         String actualHomePageTitle = driver.findElement(homePageTitleLocator).getText();

         Assert.assertEquals(actualHomePageTitle, expectedHomePageTitle);

         System.out.println("condition is feature");

         String productName = "Sauce Labs Backpack";
         By ItemsLocator = By.className("inventory_item");
         By productNameLocator = By
               .xpath("//div[@class='inventory_item']//div[@class='inventory_item_description']//a/div");
         By buttonAddCartLocator = By.xpath(".//div[@class='pricebar']/button");
         By cartBadgeLocator = By.xpath("//span[@class='shopping_cart_badge']");

         Assert.assertTrue(isElementPresent(ItemsLocator), "Item should be displayed");

         List<WebElement> products = driver.findElements(ItemsLocator);
         WebElement productToSelect = products.stream()
               .filter(item -> item.findElement(productNameLocator).getText().equals(productName)).findFirst()
               .orElseThrow(() -> new RuntimeException("Product not found: " + productName));
         System.out.println("Product found: " + productToSelect.getText());

         WebElement addToCartButton = productToSelect.findElement(buttonAddCartLocator);
         String buttonText = addToCartButton.getText();
         System.out.println("Product buttonText: " + buttonText);

         addToCartButton.click();

         WebElement updatedButton = retryFindElement(productToSelect, buttonAddCartLocator, 5);
         String buttonTextUpdate = updatedButton.getText();
         System.out.println("Product buttonTextUpdate: " + buttonTextUpdate);

         wait.until(ExpectedConditions.textToBePresentInElementLocated(cartBadgeLocator, "1"));

         Assert.assertEquals(buttonTextUpdate, "Remove", "Button text should change to 'Remove'");

         updatedButton.click();
         WebElement addToCartButtonUpdateElement = retryFindElement(productToSelect, buttonAddCartLocator, 5);

         String buttonTextUpdateAgain = addToCartButtonUpdateElement.getText();
         Assert.assertEquals(buttonTextUpdateAgain, "Remove", "Button text should change to 'Remove'");
      } else {
         System.out.println("condition is not feature");

         By homePageTitleLocator = By.xpath("//span[@class='title']");
         Assert.assertTrue(isElementPresent(homePageTitleLocator), "Home page title should be displayed");

         String expectedHomePageTitle = "Products";
         String actualHomePageTitle = driver.findElement(homePageTitleLocator).getText();

         Assert.assertEquals(actualHomePageTitle, expectedHomePageTitle);
      }
   }

   public boolean isElementPresent(By by) {
      try {
         wait.until(ExpectedConditions.visibilityOfElementLocated(by));
         return true;
      } catch (Exception e) {
         return false;
      }
   }

   public String getErrorMessage() {
      By errorMessageLocator = By.xpath("//div[@class='error-message-container error']/h3");
      return driver.findElement(errorMessageLocator).getText();
   }

   public WebElement retryFindElement(WebElement parent, final By locator, int timeoutSeconds) {
      return new WebDriverWait(driver, java.time.Duration.ofSeconds(timeoutSeconds))
            .until(ExpectedConditions.presenceOfNestedElementLocatedBy(parent, locator));
   }

   @DataProvider
   public Object[][] credentialData() {
      return new Object[][] {
            // lock user case
            { "locked_out_user", "secret_sauce", "Epic sadface: Sorry, this user has been locked out." },

            // empty fields validation
            { "", "secret_sauce", "Epic sadface: Username is required" },
            { "standard_user", "", "Epic sadface: Password is required" },
            { "", "", "Epic sadface: Username is required" },

            // credentials not exist
            { "standard_user", "wrong_password",
                  "Epic sadface: Username and password do not match any user in this service" },
            { "randomUser123", "secret_sauce",
                  "Epic sadface: Username and password do not match any user in this service" },

            // can login
            // {"standard_user", "secret_sauce", null},
            // {"problem_user", "secret_sauce", null},
            // {"performance_glitch_user", "secret_sauce", null}
      };
   }

   @DataProvider
   public Object[][] credentialAnomaliesData() {
      return new Object[][] {
            // can login, anomalies cases
            { "problem_user", "secret_sauce", "feature" },
            { "performance_glitch_user", "secret_sauce", "delay" }
      };
   }

}
