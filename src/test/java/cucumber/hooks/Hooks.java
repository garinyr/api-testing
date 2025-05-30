package cucumber.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

   // Shared test context variables
   public static String validEmail = "garinyr_test1@gmail.com";
   public static String validPassword = "@dmin123";
   public static String email;
   public static String name;
   public static String password;
   public static String token;
   public static String objectId;

   @Before(order = 0)
   public void initializeTestContext() {
      // Generate default employee credentials
      int random = (int) (Math.random() * 10000);
      email = "garinyr" + random + "@gmail.com";
      name = "Garin YR";
      password = "@dmin123";

      // Reset reusable state
      token = null;
      objectId = null;
   }

   @Before(order = 1)
   public void beforeScenario(Scenario scenario) {
      System.out.println("[START] Scenario: " + scenario.getName());
   }

   @After
   public void afterScenario(Scenario scenario) {
      System.out.println("[END] Scenario: " + scenario.getName());
      if (scenario.isFailed()) {
         System.out.println("[FAILED] Scenario failed: " + scenario.getName());
      }
   }
}
