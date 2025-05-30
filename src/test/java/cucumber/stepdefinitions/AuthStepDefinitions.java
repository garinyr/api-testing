package cucumber.stepdefinitions;

import com.afterofficetest.model.login.RequestLogin;
import com.afterofficetest.model.login.ResponseLogin;
import cucumber.hooks.Hooks;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import com.fasterxml.jackson.databind.ObjectMapper;

import context.ScenarioContext;

public class AuthStepDefinitions {

   ObjectMapper objectMapper = new ObjectMapper();

   @Given("I am already authenticated")
   public void validAuthenticate() throws Exception {
      RequestLogin req = new RequestLogin(Hooks.validEmail, Hooks.validPassword);
      String body = objectMapper.writeValueAsString(req);

      Response loginResp = RestAssured.given().baseUri("https://whitesmokehouse.com/webhook/api")
            .header("Content-Type", "application/json")
            .body(body).post("/login");

      ResponseLogin login = objectMapper.readValue(loginResp.asString(), ResponseLogin.class);

      Assert.assertNotNull(login.getToken(), "Token should not be null");
      ScenarioContext.token = login.getToken();
   }

   @Given("I am authenticated")
   public void authenticate() throws Exception {
      RequestLogin req = new RequestLogin(Hooks.email, Hooks.password);
      String body = objectMapper.writeValueAsString(req);

      Response loginResp = RestAssured.given().baseUri("https://whitesmokehouse.com/webhook/api")
            .header("Content-Type", "application/json")
            .body(body).post("/login");

      ResponseLogin login = objectMapper.readValue(loginResp.asString(), ResponseLogin.class);

      Assert.assertNotNull(login.getToken(), "Token should not be null");
      Hooks.token = login.getToken();
   }
}
