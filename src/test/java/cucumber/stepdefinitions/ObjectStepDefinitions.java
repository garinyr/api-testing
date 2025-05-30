package cucumber.stepdefinitions;

import com.afterofficetest.model.object.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import context.ScenarioContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class ObjectStepDefinitions {

    ObjectMapper objectMapper = new ObjectMapper();
    Response lastResponse;
    String objectId;
    RequestAddObject requestAddBody;
    RequestUpdateObject requestUpdateBody;

    @When("I add a new object with:")
    public void i_add_a_new_object_with(DataTable dataTable) throws Exception {
        Map<String, String> data = dataTable.asMap();
        requestAddBody = new RequestAddObject(
                data.get("name"),
                new RequestAddObjectData(
                        data.get("year"),
                        data.get("price"),
                        data.get("cpu_model"),
                        data.get("hdd_size"),
                        Integer.parseInt(data.get("capacity")),
                        Integer.parseInt(data.get("screen_size")),
                        data.get("color")));

        // Send request
        lastResponse = RestAssured.given()
                .baseUri("https://whitesmokehouse.com/webhook")
                .header("Authorization", "Bearer " + ScenarioContext.token)
                .header("Content-Type", "application/json")
                .body(objectMapper.writeValueAsString(requestAddBody))
                .post("/api/objects");

        // Store objectId
        List<ResponseListAddObject> responseList = objectMapper.readValue(
                lastResponse.asString(),
                new TypeReference<List<ResponseListAddObject>>() {
                });
        ScenarioContext.objectId = String.valueOf(responseList.get(0).getId());
        Assert.assertEquals(lastResponse.statusCode(), 200);
    }

    @Then("the object should be added successfully")
    public void verifyAddSuccess() throws Exception {
        Response response = RestAssured.given().baseUri("https://whitesmokehouse.com/webhook/api")
                .header("Authorization", "Bearer " + ScenarioContext.token)
                .queryParam("id", ScenarioContext.objectId)
                .get("/objectslistId");

        Assert.assertEquals(response.statusCode(), 200);

        List<ResponseGetListObjectById> actualObject = objectMapper.readValue(
                response.asString(),
                new TypeReference<List<ResponseGetListObjectById>>() {
                });
        ResponseGetListObjectById retrieved = actualObject.get(0);

        Assert.assertEquals(retrieved.getName(), requestAddBody.getName());
        Assert.assertEquals(retrieved.getData().getYear(), requestAddBody.getData().getYear());
        Assert.assertEquals(retrieved.getData().getPrice(), requestAddBody.getData().getPrice());
    }

    @When("I update the object with new attributes")
    public void updateObject() throws Exception {
        RequestUpdateObjectData data = new RequestUpdateObjectData("2022", 2849, "M4 Pro", "2", "4", "16", "silver");
        requestUpdateBody = new RequestUpdateObject("Apple MacBook Pro 22", data);

        lastResponse = RestAssured.given()
                .baseUri("https://whitesmokehouse.com/webhook/37777abe-a5ef-4570-a383-c99b5f5f7906/api")
                .header("Authorization", "Bearer " + ScenarioContext.token)
                .header("Content-Type", "application/json")
                .body(objectMapper.writeValueAsString(requestUpdateBody))
                .put("/objects/" + ScenarioContext.objectId);

        Assert.assertEquals(lastResponse.statusCode(), 200);
    }

    @Then("the object should be updated successfully")
    public void verifyUpdate() throws Exception {
        Response getResp = RestAssured.given().baseUri("https://whitesmokehouse.com/webhook/api/")
                .header("Authorization", "Bearer " + ScenarioContext.token)
                .queryParam("id", ScenarioContext.objectId).get("/objectslistId");

        Assert.assertEquals(getResp.statusCode(), 200);

        List<ResponseGetListObjectById> retrievedObjects = objectMapper.readValue(
                getResp.asString(),
                new TypeReference<List<ResponseGetListObjectById>>() {
                });

        ResponseGetListObjectById retrieved = retrievedObjects.get(0);
        Assert.assertEquals(retrieved.getName(), requestUpdateBody.getName());
    }

    @When("I delete the object")
    public void deleteObject() {
        lastResponse = RestAssured.given()
                .baseUri("https://whitesmokehouse.com/webhook/d79a30ed-1066-48b6-83f5-556120afc46f/api/")
                .header("Authorization", "Bearer " + ScenarioContext.token)
                .delete("/objects/" + ScenarioContext.objectId);
    }

    @Then("the object should be deleted successfully")
    public void verifyDelete() throws Exception {
        ResponseDeleteObject response = objectMapper.readValue(lastResponse.asString(), ResponseDeleteObject.class);
        Assert.assertEquals(response.getStatus(), "deleted");
    }

    @And("retrieving it should return an empty response")
    public void verifyDeletedObjectIsGone() {
        Response getResp = RestAssured.given().baseUri("https://whitesmokehouse.com/webhook/api/")
                .header("Authorization", "Bearer " + ScenarioContext.token)
                .queryParam("id", ScenarioContext.objectId).get("/objectslistId");

        Assert.assertTrue(getResp.asString().isEmpty());
    }
}
