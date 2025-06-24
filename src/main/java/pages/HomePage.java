package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private WebDriver driver;
    private final By title = By.xpath("//span[@class='title']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTitle() {
        return driver.findElement(title).getText();
    }

    public boolean isLoaded() {
        return driver.findElements(title).size() > 0;
    }
}
