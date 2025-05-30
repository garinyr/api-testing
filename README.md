# API Testing with RestAssured & TestNG

This project demonstrates API testing using **RestAssured** and **TestNG**

## Technologies Used
- Java 11
- Maven (dependency manager)
- Cucumber (for BDD)
- TestNG (test runner and reporting)
- RestAssured (API requests)
- Jackson (JSON mapper)

## How to Run

```bash
# Clone the repository
git clone this repo

# Navigate to the project folder
cd api-testing

# Run the test suite
### how to run testng test
mvn clean test -P testng-tests
### how to run cucumber test
mvn clean test -P cucumber-tests
```

## Project Structure
```bash
+---src
    +---main
    |   +---java
    |   |   \---com
    |   |       \---afterofficetest
    |   |           \---model
    |   |               +---login
    |   |               |       RequestLogin.java
    |   |               |       ResponseLogin.java
    |   |               +---object
    |   |               |       RequestAddObject.java
    |   |               |       RequestAddObjectData.java
    |   |               |       RequestUpdateObject.java
    |   |               |       RequestUpdateObjectData.java
    |   |               |       ResponseDeleteObject.java
    |   |               |       ResponseGetListObjectById.java
    |   |               |       ResponseGetListObjectByIdData.java
    |   |               |       ResponseListAddObject.java
    |   |               |       ResponseListAddObjectData.java
    |   |               |       ResponseListUpdateObject.java
    |   |               |       ResponseListUpdateObjectData.java
    |   |               \---register
    |   |                       RequestRegister.java
    |   |                       ResponseRegister.java
    |   \---resources
    \---test
        +---java
        |   +---context
        |   |       ScenarioContext.java
        |   +---cucumber
        |   |   +---hooks
        |   |   |       Hooks.java
        |   |   +---runners
        |   |   |       ObjectRunner.java
        |   |   |       RegisterRunner.java
        |   |   \---stepdefinitions
        |   |           AuthStepDefinitions.java
        |   |           ObjectStepDefinitions.java
        |   |           RegisterStepDefinitions.java
        |   +---e2e
        |   |       RestAssuredE2ETest.java
        |   \---restassured
        |           restAssuredImpl.java
        \---resources
            |   cucumber.xml
            |   testng.xml
            \---features
                    auth.feature
                    object_operations.feature
                    register.feature