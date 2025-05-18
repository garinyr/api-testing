package restassured;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class restAssuredImpl {
    String token;

    @BeforeSuite
    public void setup() {
        RestAssured.baseURI = "https://whitesmokehouse.com/webhook/api";

        String email = "garinyr@gmail.com";
        String password = "@dmin123";

        String requestBody = String.format("""
                {
                    "email": "%s",
                    "password": "%s"
                }
                """, email, password);

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/login");
        System.out.println("Response: " + response.asString());
        token = response.jsonPath().getString("token");
    }

    @Test
    public void testRegister() {
        RestAssured.baseURI = "https://whitesmokehouse.com/webhook/api";

        String email = "garinyr3@gmail.com";
        String full_name = "Garin YR";
        String password = "@dmin123";
        String department = "Technology";
        String phone_number = "08121122334";

        String requestBody = String.format("""
                {
                    "email": "%s",
                    "full_name": "%s",
                    "password": "%s",
                    "department": "%s",
                    "phone_number": "%s"
                }
                """, email, full_name, password, department, phone_number);

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/register");
        System.out.println("Response: " + response.asString());
        System.out.println("Status Code: " + response.statusCode());

        assert response.statusCode() == 200 : "Expected status code 200 but got " + response.statusCode();
        assert response.jsonPath().getString("email").equals(email) : "Email does not match";
        assert response.jsonPath().getString("full_name").equals(full_name) : "Full name does not match";
        assert response.jsonPath().getString("department").equals(department) : "Department does not match";
        assert response.jsonPath().getString("phone_number").equals(phone_number) : "Phone number does not match";
    }

    @Test
    public void testLogin() {
        RestAssured.baseURI = "https://whitesmokehouse.com/webhook/api";

        String email = "garinyr@gmail.com";
        String password = "@dmin123";

        String requestBody = String.format("""
                {
                    "email": "%s",
                    "password": "%s"
                }
                """, email, password);

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/login");
        token = response.jsonPath().getString("token");

        assert response.statusCode() == 200 : "Expected status code 200 but got " + response.statusCode();
        assert response.jsonPath().getString("token").length() > 0 : "Token is empty";
    }

    @Test
    public void testGetAllDepartments() {
        RestAssured.baseURI = "https://whitesmokehouse.com/webhook/api";

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/department");
        System.out.println("Response: " + response.asString());

        assert response.statusCode() == 200 : "Expected status code 200 but got " + response.statusCode();
        assert response.jsonPath().getList("data").size() > 0 : "No departments found";
    }

    @Test
    public void testGetListAllObjects() {
        RestAssured.baseURI = "https://whitesmokehouse.com/webhook/api";

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/objects");
        System.out.println("Response: " + response.asString());

        assert response.statusCode() == 200 : "Expected status code 200 but got " + response.statusCode();
    }

    @Test
    public void testGetObjectById() {
        RestAssured.baseURI = "https://whitesmokehouse.com/webhook/api";
        String objectId = "94";
        String objectId2 = "95";

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .queryParam("id", objectId)
                .queryParam("id", objectId2)
                .when()
                .get("/objectslistId");
        System.out.println("Response: " + response.asString());

        assert response.statusCode() == 200 : "Expected status code 200 but got " + response.statusCode();
        assert response.jsonPath().getString("data").length() > 0 : "No objects found";
        assert response.jsonPath().getString("[0].id").equals(objectId) : "Object ID does not match";
        assert response.jsonPath().getString("[1].id").equals(objectId2) : "Object ID does not match";
    }

    @Test
    public void testSingleObject() {
        RestAssured.baseURI = "https://whitesmokehouse.com/webhook/8749129e-f5f7-4ae6-9b03-93be7252443c/api";

        String objectId = "94";
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/objects/" + objectId);
        System.out.println("Response: " + response.asString());

        assert response.statusCode() == 200 : "Expected status code 200 but got " + response.statusCode();
        assert response.jsonPath().getString("data").length() > 0 : "No objects found";
        assert response.jsonPath().getString("id").equals(objectId) : "Object ID does not match";
    }

    @Test
    public void testAddObject() {
        RestAssured.baseURI = "https://whitesmokehouse.com/webhook/api";

        String name = "Apple MacBook Pro 16";
        String year = "2019";
        String price = "1849.99";
        String cpu_model = "Intel Core i9";
        String hard_disk_size = "1 TB";
        String capacity = "2 cpu";
        String screen_size = "14 Inch";
        String color = "red";

        String requestBody = String.format("""
                {
                    "name": "%s",
                    "data": {
                        "year": %s,
                        "price": %s,
                        "cpu_model": "%s",
                        "hard_disk_size": "%s",
                        "capacity": "%s",
                        "screen_size": "%s",
                        "color": "%s"
                    }
                }
                """, name, year, price, cpu_model, hard_disk_size, capacity, screen_size, color);

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(requestBody)
                .when()
                .post("/objects");
        System.out.println("Response: " + response.asString());

        assert response.statusCode() == 200 : "Expected status code 200 but got " + response.statusCode();
        assert response.jsonPath().getString("[0].name").equals(name) : "Name does not match";
        assert response.jsonPath().getString("[0].data.year").equals(year) : "Year does not match";
        assert response.jsonPath().getString("[0].data.price").equals(price) : "Price does not match";
        assert response.jsonPath().getString("[0].data.cpu_model").equals(cpu_model) : "CPU model does not match";
        assert response.jsonPath().getString("[0].data.hard_disk_size").equals(hard_disk_size)
                : "Hard disk size does not match";
        assert response.jsonPath().getString("[0].data.capacity").equals(capacity) : "Capacity does not match";
        assert response.jsonPath().getString("[0].data.screen_size").equals(screen_size) : "Screen size does not match";
        assert response.jsonPath().getString("[0].data.color").equals(color) : "Color does not match";
    }

    @Test
    public void testUpdateObject() {
        RestAssured.baseURI = "https://whitesmokehouse.com/webhook/37777abe-a5ef-4570-a383-c99b5f5f7906/api/";

        String objectId = "94";
        String name = "Apple MacBook Pro 17";
        String year = "2019";
        String price = "1849.99";
        String cpu_model = "Intel Core i9";
        String hard_disk_size = "1 TB";
        String capacity = "2 cpu";
        String screen_size = "14 Inch";
        String color = "red";

        String requestBody = String.format("""
                {
                    "name": "%s",
                    "data": {
                        "year": %s,
                        "price": %s,
                        "cpu_model": "%s",
                        "hard_disk_size": "%s",
                        "capacity": "%s",
                        "screen_size": "%s",
                        "color": "%s"
                    }
                }
                """, name, year, price, cpu_model, hard_disk_size, capacity, screen_size, color);

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(requestBody)
                .when()
                .put("/objects/" + objectId);
        System.out.println("Response: " + response.asString());

        assert response.statusCode() == 200 : "Expected status code 200 but got " + response.statusCode();
        assert response.jsonPath().getString("[0].name").equals(name) : "Name does not match";
        assert response.jsonPath().getString("[0].data.year").equals(year) : "Year does not match";
        assert response.jsonPath().getString("[0].data.price").equals(price) : "Price does not match";
        assert response.jsonPath().getString("[0].data['CPU model']").equals(cpu_model) : "CPU model does not match";
        assert response.jsonPath().getString("[0].data['Hard disk size']").equals(hard_disk_size)
                : "Hard disk size does not match";
        assert response.jsonPath().getString("[0].data.capacity").equals(capacity) : "Capacity does not match";
        assert response.jsonPath().getString("[0].data.screen_size").equals(screen_size) : "Screen size does not match";
        assert response.jsonPath().getString("[0].data.color").equals(color) : "Color does not match";
    }

    @Test
    public void testPartiallyUpdateObject() {
        RestAssured.baseURI = "https://whitesmokehouse.com/webhook/39a0f904-b0f2-4428-80a3-391cea5d7d04/api/";

        String objectId = "94";
        String name = "Apple MacBook Pro X 22";
        String year = "2022";

        String requestBody = String.format("""
                {
                    "name": "%s",
                    "year": %s
                }
                """, name, year);
        System.out.println("Request Body: " + requestBody);
        
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(requestBody)
                .when()
                .patch("/object/" + objectId);
        System.out.println("Response: " + response.asString());

        assert response.statusCode() == 200 : "Expected status code 200 but got " + response.statusCode();
        assert response.jsonPath().getString("name").equals(name) : "Name does not match";
        assert response.jsonPath().getString("data.year").equals(year) : "Year does not match";
    }

    @Test
    public void testDeleteObject() {
        RestAssured.baseURI = "https://whitesmokehouse.com/webhook/d79a30ed-1066-48b6-83f5-556120afc46f/api/";

        String objectId = "58";
        String expectedMsg = "Object with id = " + objectId + ", has been deleted.";

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/objects/" + objectId);
        System.out.println("Response: " + response.asString());

        assert response.statusCode() == 200 : "Expected status code 200 but got " + response.statusCode();
        assert response.jsonPath().getString("message").equals(expectedMsg) : "Delete message does not match";
        assert response.jsonPath().getString("status").equals("deleted") : "Status does not match";
    }
}
