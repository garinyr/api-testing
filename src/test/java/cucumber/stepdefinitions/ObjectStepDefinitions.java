package cucumber.stepdefinitions;

import com.afterofficetest.model.login.RequestLogin;
import com.afterofficetest.model.login.ResponseLogin;
import com.afterofficetest.model.object.RequestAddObject;
import com.afterofficetest.model.object.RequestAddObjectData;
import com.afterofficetest.model.object.RequestUpdateObject;
import com.afterofficetest.model.object.RequestUpdateObjectData;
import com.afterofficetest.model.object.ResponseDeleteObject;
import com.afterofficetest.model.object.ResponseGetListObjectById;
import com.afterofficetest.model.object.ResponseListAddObject;
import com.afterofficetest.model.register.RequestRegister;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class ObjectStepDefinitions {

    String token, objectId;
    String name, email, password = "@dmin123";
    String objectName;
    Response lastResponse;
    ObjectMapper objectMapper = new ObjectMapper();

    @Given("I have employee details with random email")
    public void generateEmployeeDetails() {
        int random = new Random().nextInt(1000);
        email = "garinyr" + random + "@gmail.com";
        name = "Garin YR";
    }

    @When("I send a registration request")
    public void registerEmployee() throws Exception {
        RequestRegister req = new RequestRegister(email, name, password, "Technology", "08121122334");
        String body = objectMapper.writeValueAsString(req);

        lastResponse = RestAssured.given().baseUri("https://whitesmokehouse.com/webhook/api")
                .header("Content-Type", "application/json")
                .body(body).post("/register");

        Assert.assertEquals(lastResponse.statusCode(), 200);
    }

    @Then("the employee should be successfully registered and able to login")
    public void verifyLogin() throws Exception {
        RequestLogin req = new RequestLogin(email, password);
        String body = objectMapper.writeValueAsString(req);

        Response loginResp = RestAssured.given().baseUri("https://whitesmokehouse.com/webhook/api")
                .header("Content-Type", "application/json")
                .body(body).post("/login");

        ResponseLogin login = objectMapper.readValue(loginResp.asString(), ResponseLogin.class);
        token = login.getToken();

        Assert.assertNotNull(token);
    }

    @Given("I am authenticated")
    public void authenticate() throws Exception {
        // Reuse login from setup()
        email = "garinyr@gmail.com";
        password = "@dmin123";
        verifyLogin();
    }

    @When("I add a new object with specified attributes")
    public void addObject() throws Exception {
        RequestAddObjectData data = new RequestAddObjectData("2019", "1849", "Intel Core i9", "1", "2", "14", "red");
        RequestAddObject req = new RequestAddObject("Apple MacBook Pro 16", data);

        lastResponse = RestAssured.given().baseUri("https://whitesmokehouse.com/webhook/api")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(objectMapper.writeValueAsString(req)).post("/objects");

        List<ResponseListAddObject> list = objectMapper.readValue(
                lastResponse.asString(), new TypeReference<List<ResponseListAddObject>>() {
                });
        objectId = String.valueOf(list.get(0).getId());
        // log
        System.out.println("Object ID: " + objectId);

        Assert.assertEquals(lastResponse.statusCode(), 200);
    }

    @Then("the object should be added successfully")
    public void verifyAddSuccess() {
        Assert.assertNotNull(objectId);
    }

    @And("I should be able to retrieve the object by ID")
    public void getObjectById() throws Exception {
        Response getResp = RestAssured.given().baseUri("https://whitesmokehouse.com/webhook/api")
                .header("Authorization", "Bearer " + token)
                .queryParam("id", objectId).get("/objectslistId");

        List<ResponseGetListObjectById> list = objectMapper.readValue(
                getResp.asString(), new TypeReference<List<ResponseGetListObjectById>>() {
                });
        Assert.assertFalse(list.isEmpty());
    }

    @Given("I have an existing object ID")
    public void existingObject() {
        // log
        System.out.println("Using existing object ID: " + objectId);
        Assert.assertNotNull(objectId);
    }

    @When("I update the object with new attributes")
    public void updateObject() throws Exception {
        objectName = "Apple MacBook Pro 22";

        RequestUpdateObjectData data = new RequestUpdateObjectData("2022", 2849, "M4 Pro", "2", "4", "16", "silver");
        RequestUpdateObject req = new RequestUpdateObject(objectName, data);

        lastResponse = RestAssured.given()
                .baseUri("https://whitesmokehouse.com/webhook/37777abe-a5ef-4570-a383-c99b5f5f7906/api")
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(objectMapper.writeValueAsString(req))
                .put("/objects/" + objectId);

        Assert.assertEquals(lastResponse.statusCode(), 200);
    }

    @Then("the object should be updated successfully")
    public void verifyUpdate() throws JsonMappingException, JsonProcessingException {
        Response getResp = RestAssured.given().baseUri("https://whitesmokehouse.com/webhook/api/")
                .header("Authorization", "Bearer " + token)
                .queryParam("id", objectId).get("/objectslistId");

        Assert.assertEquals(getResp.statusCode(), 200, "Expected HTTP status 200");

        List<ResponseGetListObjectById> retrievedObjects = objectMapper.readValue(
                getResp.asString(),
                new TypeReference<List<ResponseGetListObjectById>>() {
                });

        Assert.assertFalse(retrievedObjects.isEmpty(), "Retrieved object list should not be empty");

        ResponseGetListObjectById retrieved = retrievedObjects.get(0);
        Assert.assertEquals(retrieved.getId(), Integer.parseInt(objectId), "Object ID does not match");
        Assert.assertEquals(retrieved.getName(), objectName, "Retrieved name mismatch");
    }

    @When("I delete the object")
    public void deleteObject() {
        lastResponse = RestAssured.given()
                .baseUri("https://whitesmokehouse.com/webhook/d79a30ed-1066-48b6-83f5-556120afc46f/api/")
                .header("Authorization", "Bearer " + token)
                .delete("/objects/" + objectId);
    }

    @Then("the object should be deleted successfully")
    public void verifyDelete() throws Exception {
        ResponseDeleteObject response = objectMapper.readValue(lastResponse.asString(), ResponseDeleteObject.class);
        Assert.assertEquals(response.getStatus(), "deleted");
    }

    @And("retrieving it should return an empty response")
    public void verifyDeletedObjectIsGone() {
        Response getResp = RestAssured.given().baseUri("https://whitesmokehouse.com/webhook/api/")
                .header("Authorization", "Bearer " + token)
                .queryParam("id", objectId).get("/objectslistId");

        Assert.assertTrue(getResp.asString().isEmpty());
    }
}
