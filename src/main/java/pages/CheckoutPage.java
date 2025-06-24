package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {
    private WebDriver driver;

    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By finishButton = By.xpath("//button[@id='finish']");
    private final By confirmationHeader = By.className("complete-header");
    private final By errorMessage = By.xpath("//div[@class='error-message-container error']/h3");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterCheckoutInfo(String first, String last, String zip) {
        driver.findElement(firstNameField).sendKeys(first);
        driver.findElement(lastNameField).sendKeys(last);
        driver.findElement(postalCodeField).sendKeys(zip);
    }

    public void clickButtonContinue() {
        driver.findElement(continueButton).click();
    }

    public void clickButtonFinish() {
        driver.findElement(finishButton).click();
    }

    public boolean isCheckoutComplete() {
        return driver.findElement(confirmationHeader).getText().equals("Thank you for your order!");
    }

    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }
}
