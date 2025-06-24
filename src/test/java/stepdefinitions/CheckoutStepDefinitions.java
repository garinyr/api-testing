package stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pages.CartPage;
import pages.CheckoutPage;
import utils.ElementHelper;
import hooks.Hooks;

import java.util.List;

public class CheckoutStepDefinitions {

   private WebDriver driver = Hooks.driver;

   CartPage cartPage = new CartPage(driver);
   CheckoutPage checkoutPage = new CheckoutPage(driver);

   ElementHelper helper = new ElementHelper(driver, 10);

   String itemName;

   @When("User adds {string} to the cart")
   public void userAddsToCart(String productName) {
      this.itemName = productName;

      By itemContainer = By.className("inventory_item");
      By productNameLocator = By
            .xpath("//div[@class='inventory_item']//div[@class='inventory_item_description']//a/div");
      By buttonAddToCart = By.xpath(".//div[@class='pricebar']/button");

      Assert.assertTrue(helper.isElementPresent(itemContainer), "Item should be displayed");

      List<WebElement> products = driver.findElements(itemContainer);
      WebElement productToAdd = products.stream()
            .filter(p -> p.findElement(productNameLocator).getText().equals(productName))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Product not found: " + productName));
      System.out.println("Product found: " + productToAdd.getText());

      productToAdd.findElement(buttonAddToCart).click();

      // go to cart
      driver.findElement(By.className("shopping_cart_link")).click();
   }

   @When("User proceeds to checkout with first name {string}, last name {string}, postal code {string}")
   public void userProceedsToCheckout(String first, String last, String zip) {
      cartPage.clickCheckout();
      checkoutPage.enterCheckoutInfo(first, last, zip);
   }

   @Then("Checkout should be failed")
   public void checkoutShouldBeFailed() {
      checkoutPage.clickButtonContinue();

      Assert.assertEquals(checkoutPage.getErrorMessage(), "Error: Last Name is required");
   }

   @Then("Checkout should be success")
   public void checkoutShouldBeSuccess() {
      checkoutPage.clickButtonContinue();

      By itemCheckout = By.xpath("//div[@class='inventory_item_name']");

      String actualText = helper.getTextElement(itemCheckout);

      Assert.assertEquals(actualText, this.itemName, "Item name did not match");

      checkoutPage.clickButtonFinish();

      Assert.assertTrue(checkoutPage.isCheckoutComplete(), "Confirmation text should be show");
   }
}
