package cucumber.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features/object_operations.feature", glue = { "cucumber.stepdefinitions",
      "cucumber.hooks" }, plugin = { "pretty", "html:target/cucumber-reports/object.html" })
public class ObjectRunner extends AbstractTestNGCucumberTests {
}
