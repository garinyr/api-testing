package e2e;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.afterofficetest.model.login.RequestLogin;
import com.afterofficetest.model.login.ResponseLogin;
import com.afterofficetest.model.object.RequestAddObjectData;
import com.afterofficetest.model.object.RequestUpdateObject;
import com.afterofficetest.model.object.RequestUpdateObjectData;
import com.afterofficetest.model.object.ResponseDeleteObject;
import com.afterofficetest.model.object.RequestAddObject;
import com.afterofficetest.model.object.ResponseListAddObject;
import com.afterofficetest.model.object.ResponseListUpdateObject;
import com.afterofficetest.model.object.ResponseGetListObjectById;
import com.afterofficetest.model.register.RequestRegister;
import com.afterofficetest.model.register.ResponseRegister;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestAssuredE2ETest {
   String token, objectId;
   ObjectMapper objectMapper = new ObjectMapper();

   @BeforeSuite
   public void setup() throws JsonProcessingException {
      RestAssured.baseURI = "https://whitesmokehouse.com/webhook/api";

      String email = "garinyr@gmail.com";
      String password = "@dmin123";

      RequestLogin requestLogin = new RequestLogin(email, password);

      String requestBody = objectMapper.writeValueAsString(requestLogin);

      Response responseLoginAPI = RestAssured.given()
            .header("Content-Type", "application/json")
            .body(requestBody)
            .when()
            .post("/login");
      ResponseLogin responseLogin = objectMapper.readValue(responseLoginAPI.asString(), ResponseLogin.class);
      token = responseLogin.getToken();
   }

   // @Test
   public void testLogin() throws JsonProcessingException {
      RestAssured.baseURI = "https://whitesmokehouse.com/webhook/api";

      String email = "garinyr@gmail.com";
      String password = "@dmin123";

      RequestLogin requestLogin = new RequestLogin(email, password);
      String requestBody = objectMapper.writeValueAsString(requestLogin);

      Response response = RestAssured.given()
            .header("Content-Type", "application/json")
            .body(requestBody)
            .when()
            .post("/login");

      ResponseLogin responseLogin = objectMapper.readValue(response.asString(), ResponseLogin.class);
      String token = responseLogin.getToken();

      Assert.assertEquals(response.statusCode(), 200, "Expected HTTP status 200");
      Assert.assertNotNull(token, "Token should not be null");
      Assert.assertFalse(token.isEmpty(), "Token should not be empty");
   }

   @Test(priority = 1)
   public void E2EregisterEmployeeTest() throws JsonProcessingException {
      RestAssured.baseURI = "https://whitesmokehouse.com/webhook/api";

      // create random number for email
      String randomNumber = String.valueOf((int) (Math.random() * 10000));
      String email = "garinyr" + randomNumber + "@gmail.com";
      String full_name = "Garin YR";
      String password = "@dmin123";
      String department = "Technology";
      String phone_number = "08121122334";

      RequestRegister requestRegister = new RequestRegister(email, full_name, password, department, phone_number);

      String registerRequestBody = objectMapper.writeValueAsString(requestRegister);

      Response responseRegisterAPI = RestAssured.given()
            .header("Content-Type", "application/json")
            .body(registerRequestBody)
            .when()
            .post("/register");

      ResponseRegister responseRegister = objectMapper.readValue(responseRegisterAPI.asString(),
            ResponseRegister.class);

      Assert.assertEquals(responseRegisterAPI.statusCode(), 200, "Expected HTTP status 200");

      Assert.assertEquals(responseRegister.getEmail(), email, "Email does not match");
      Assert.assertEquals(responseRegister.getFull_name(), full_name, "Full name does not match");
      Assert.assertEquals(responseRegister.getDepartment(), department, "Department does not match");
      Assert.assertEquals(responseRegister.getPhone_number(), phone_number, "Phone number does not match");

      RequestLogin requestLogin = new RequestLogin(email, password);
      String loginRequestBody = objectMapper.writeValueAsString(requestLogin);

      Response responseLoginAPI = RestAssured.given()
            .header("Content-Type", "application/json")
            .body(loginRequestBody)
            .when()
            .post("/login");
      ResponseLogin responseLogin = objectMapper.readValue(responseLoginAPI.asString(), ResponseLogin.class);
      String token = responseLogin.getToken();

      Assert.assertEquals(responseLoginAPI.statusCode(), 200, "Expected HTTP status 200");

      Assert.assertNotNull(token, "Token should not be null");
      Assert.assertFalse(token.isEmpty(), "Token should not be empty");
   }

   @Test(priority = 2)
   public void E2EAddObjectTest() throws JsonProcessingException {
      RestAssured.baseURI = "https://whitesmokehouse.com/webhook/api";

      String name = "Apple MacBook Pro 16";
      String year = "2019";
      String price = "1849";
      String cpu_model = "Intel Core i9";
      String hard_disk_size = "1";
      int capacity = 2;
      int screen_size = 14;
      String color = "red";

      RequestAddObjectData data = new RequestAddObjectData(year, price, cpu_model, hard_disk_size, capacity,
            screen_size, color);
      RequestAddObject requestAddObject = new RequestAddObject(name, data);

      String addObjectRequestBody = objectMapper.writeValueAsString(requestAddObject);

      Response responseAddObjectAPI = RestAssured.given()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + token)
            .body(addObjectRequestBody)
            .when()
            .post("/objects");

      System.out.println("Response Add Object API: " + responseAddObjectAPI.asString());

      Assert.assertEquals(responseAddObjectAPI.statusCode(), 200, "Expected HTTP status 200");

      // Deserialize response to POJO
      List<ResponseListAddObject> addedObjects = objectMapper.readValue(
            responseAddObjectAPI.asString(),
            new TypeReference<List<ResponseListAddObject>>() {
            });

      Assert.assertFalse(addedObjects.isEmpty(), "Added object list should not be empty");
      ResponseListAddObject added = addedObjects.get(0);
      System.out.println("Added object: " + added.getData().toString());

      Assert.assertEquals(added.getName(), name, "Name does not match");
      Assert.assertEquals(added.getData().getYear(), year, "Year does not match");
      Assert.assertEquals(added.getData().getPrice(), price, "Price does not match");
      Assert.assertEquals(added.getData().getCpu_model(), cpu_model, "CPU model does not match");
      Assert.assertEquals(added.getData().getHard_disk_size(), hard_disk_size, "Hard disk size does not match");
      Assert.assertEquals(added.getData().getCapacity(), capacity, "Capacity does not match");
      Assert.assertEquals(added.getData().getScreen_size(), screen_size, "Screen size does not match");
      Assert.assertEquals(added.getData().getColor(), color, "Color does not match");

      objectId = String.valueOf(added.getId());

      Response responseListObjectByIdAPI = RestAssured.given()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + token)
            .queryParam("id", objectId)
            .when()
            .get("/objectslistId");

      System.out.println("Response List Object By ID: " + responseListObjectByIdAPI.asString());

      Assert.assertEquals(responseListObjectByIdAPI.statusCode(), 200, "Expected HTTP status 200");

      List<ResponseGetListObjectById> retrievedObjects = objectMapper.readValue(
            responseListObjectByIdAPI.asString(),
            new TypeReference<List<ResponseGetListObjectById>>() {
            });

      Assert.assertFalse(retrievedObjects.isEmpty(), "Retrieved object list should not be empty");

      ResponseGetListObjectById retrieved = retrievedObjects.get(0);
      Assert.assertEquals(retrieved.getId(), added.getId(), "Object ID does not match");
      Assert.assertEquals(retrieved.getName(), name, "Retrieved name mismatch");
   }

   @Test(priority = 3, dependsOnMethods = "E2EAddObjectTest")
   public void E2EUpdateObjectTest() throws JsonProcessingException {
      /**
       * update object
       * get object by id
       */
      RestAssured.baseURI = "https://whitesmokehouse.com/webhook/37777abe-a5ef-4570-a383-c99b5f5f7906/api/";

      String name = "Apple MacBook Pro 22";
      String year = "2022";
      int price = 2849;
      String cpu_model = "M4 Pro";
      String hard_disk_size = "2";
      String capacity = "4";
      String screen_size = "16";
      String color = "silver";

      RequestUpdateObjectData data = new RequestUpdateObjectData(year, price, cpu_model, hard_disk_size, capacity,
            screen_size, color);

      RequestUpdateObject requestUpdateObject = new RequestUpdateObject(name, data);

      String requestBody = objectMapper.writeValueAsString(requestUpdateObject);

      Response responseUpdateObjectAPI = RestAssured.given()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + token)
            .body(requestBody)
            .when()
            .put("/objects/" + objectId);
      System.out.println("Response: " + responseUpdateObjectAPI.asString());

      Assert.assertEquals(responseUpdateObjectAPI.statusCode(), 200, "Expected HTTP status 200");

      List<ResponseListUpdateObject> UpdateObjects = objectMapper.readValue(
            responseUpdateObjectAPI.asString(),
            new TypeReference<List<ResponseListUpdateObject>>() {
            });

      Assert.assertFalse(UpdateObjects.isEmpty(), "Update object list should not be empty");

      ResponseListUpdateObject updates = UpdateObjects.get(0);
      System.out.println("Added object: " + updates.getData().toString());

      Assert.assertEquals(updates.getName(), name, "Name does not match");
      Assert.assertEquals(updates.getData().getYear(), year, "Year does not match");
      Assert.assertEquals(updates.getData().getPrice(), price, "Price does not match");
      Assert.assertEquals(updates.getData().getcpuModel(), cpu_model, "CPU model does not match");
      Assert.assertEquals(updates.getData().gethardDiskSize(), hard_disk_size, "Hard disk size does not match");
      Assert.assertEquals(updates.getData().getCapacity(), capacity, "Capacity does not match");
      Assert.assertEquals(updates.getData().getScreen_size(), screen_size, "Screen size does not match");
      Assert.assertEquals(updates.getData().getColor(), color, "Color does not match");

      objectId = String.valueOf(updates.getId());

      RestAssured.baseURI = "https://whitesmokehouse.com/webhook/api/";

      Response responseListObjectByIdAPI = RestAssured.given()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + token)
            .queryParam("id", objectId)
            .when()
            .get("/objectslistId");

      System.out.println("Response List Object By ID: " + responseListObjectByIdAPI.asString());

      Assert.assertEquals(responseListObjectByIdAPI.statusCode(), 200, "Expected HTTP status 200");

      List<ResponseGetListObjectById> retrievedObjects = objectMapper.readValue(
            responseListObjectByIdAPI.asString(),
            new TypeReference<List<ResponseGetListObjectById>>() {
            });

      Assert.assertFalse(retrievedObjects.isEmpty(), "Retrieved object list should not be empty");

      ResponseGetListObjectById retrieved = retrievedObjects.get(0);
      Assert.assertEquals(retrieved.getId(), updates.getId(), "Object ID does not match");
      Assert.assertEquals(retrieved.getName(), name, "Retrieved name mismatch");
   }

   @Test(priority = 4, dependsOnMethods = "E2EAddObjectTest")
   public void E2EDeleteObjectTest() throws JsonMappingException, JsonProcessingException {
      /**
       * delete object
       * get object by id
       */
      System.out.println("Object ID to delete: " + objectId);

      RestAssured.baseURI = "https://whitesmokehouse.com/webhook/d79a30ed-1066-48b6-83f5-556120afc46f/api/";

      Response responseDeleteObjectAPI = RestAssured.given()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + token)
            .when()
            .delete("/objects/" + objectId);

      System.out.println("Response Delete: " + responseDeleteObjectAPI.asString());

      Assert.assertEquals(responseDeleteObjectAPI.statusCode(), 200, "Expected HTTP status 200");

      ResponseDeleteObject deleteResponse = objectMapper.readValue(
            responseDeleteObjectAPI.asString(),
            ResponseDeleteObject.class);

      Assert.assertEquals(deleteResponse.getStatus(), "deleted", "Expected status to be 'deleted'");
      Assert.assertTrue(deleteResponse.getMessage().contains("has been deleted."), "Delete message mismatch");

      RestAssured.baseURI = "https://whitesmokehouse.com/webhook/api/";

      Response responseListObjectByIdAPI = RestAssured.given()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + token)
            .queryParam("id", objectId)
            .when()
            .get("/objectslistId");

      System.out.println("Response List Object By ID: " + responseListObjectByIdAPI.asString());

      Assert.assertEquals(responseListObjectByIdAPI.statusCode(), 200, "Expected HTTP status 200");

      Assert.assertTrue(responseListObjectByIdAPI.asString().isEmpty(), "Retrieved object list should be empty");
   }
}
