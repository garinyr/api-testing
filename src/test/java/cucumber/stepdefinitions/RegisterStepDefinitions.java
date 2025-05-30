package cucumber.stepdefinitions;

import com.afterofficetest.model.register.RequestRegister;
import com.afterofficetest.model.login.RequestLogin;
import com.afterofficetest.model.login.ResponseLogin;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import context.ScenarioContext;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

public class RegisterStepDefinitions {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private String name;
    private String email;
    private String password = "@dmin123";
    private Response lastResponse;

    @Given("I have employee details with name {string} and email {string}")
    public void i_have_employee_details(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @When("I send a registration request")
    public void i_send_a_registration_request() throws Exception {
        RequestRegister req = new RequestRegister(email, name, password, "Technology", "08121122334");
        String body = objectMapper.writeValueAsString(req);

        lastResponse = RestAssured.given()
            .baseUri("https://whitesmokehouse.com/webhook/api")
            .header("Content-Type", "application/json")
            .body(body)
            .post("/register");
    }

    @Then("the employee should be successfully registered")
    public void employee_should_be_successfully_registered() {
        Assert.assertEquals(lastResponse.statusCode(), 200, "Registration failed!");
    }

    @Given("I am a registered employee with email {string} and password {string}")
    public void i_am_a_registered_employee(String email, String password) throws Exception {
        this.email = email;
        this.password = password;

        RequestRegister req = new RequestRegister(email, "AutoTest User", password, "Tech", "08121122334");
        String body = objectMapper.writeValueAsString(req);

        Response response = RestAssured.given()
            .baseUri("https://whitesmokehouse.com/webhook/api")
            .header("Content-Type", "application/json")
            .body(body)
            .post("/register");

        Assert.assertTrue(response.statusCode() == 200 || response.statusCode() == 400,
            "Unexpected status: " + response.statusCode());
    }

    @When("I login")
    public void i_login() throws Exception {
        RequestLogin req = new RequestLogin(email, password);
        String body = objectMapper.writeValueAsString(req);

        lastResponse = RestAssured.given()
            .baseUri("https://whitesmokehouse.com/webhook/api")
            .header("Content-Type", "application/json")
            .body(body)
            .post("/login");
    }

    @Then("I should receive a valid auth token")
    public void i_should_receive_a_valid_auth_token() throws Exception {
        ResponseLogin login = objectMapper.readValue(lastResponse.asString(), ResponseLogin.class);
        Assert.assertNotNull(login.getToken(), "Token should not be null");
        ScenarioContext.token = login.getToken();
        ScenarioContext.email = email;
        ScenarioContext.password = password;
    }

    @Given("I use email {string} and password {string}")
    public void i_use_email_and_password(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Then("the login response should contain message {string}")
    public void the_login_response_should_contain_message(String expectedMessage) throws Exception {
        JsonNode json = objectMapper.readTree(lastResponse.asString());
        String actualMessage = json.path("message").asText();
        Assert.assertEquals(actualMessage, expectedMessage, "Unexpected error message.");
    }
}
