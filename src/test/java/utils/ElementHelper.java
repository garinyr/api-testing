package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementHelper {

    private WebDriver driver;
    private WebDriverWait wait;

    public ElementHelper(WebDriver driver, int timeoutSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(timeoutSeconds));
    }

    public boolean isElementVisible(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isElementPresent(By locator) {
        try {
            return driver.findElements(locator).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public String getTextElement(By locator) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            return driver.findElement(locator).getText();
        } catch (Exception e) {
            return "null";
        }
    }
}
