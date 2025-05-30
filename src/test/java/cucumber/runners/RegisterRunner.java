package cucumber.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features/register.feature", glue = { "cucumber.stepdefinitions",
            "cucumber.hooks" }, plugin = { "pretty", "html:target/cucumber-reports/register.html" })
public class RegisterRunner extends AbstractTestNGCucumberTests {
}
