package restassured;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class restAssuredImpl {

    @Test
    public void testLogin() {
        RestAssured.baseURI = "https://whitesmokehouse.com/webhook/api";

        String requestBody = "{\r\n    \"email\": \"albertsimanjuntak14@gmail.com\",\r\n    \"password\": \"@dmin123\"\r\n}";

        RestAssured.given()
            .header("Content-Type", "application/json")
            .body(requestBody)
            .when()
            .post("/login")
            .then()
            .statusCode(200)
            .log().all();
    }
}
