# API Testing with RestAssured & TestNG

This project demonstrates automated REST API testing using **RestAssured** ,**TestNG** and **Cucumber BDD**.

## Technologies Used
- Java 11
- Maven (dependency manager)
- Cucumber (for BDD)
- TestNG (test runner and reporting)
- RestAssured (API requests)
- Jackson (JSON mapper)

## Project Structure
```bash
src
├── main
│   └── java
│       └── com
│           └── afterofficetest
│               └── model
│                   ├── login
│                   │   ├── RequestLogin.java
│                   │   └── ResponseLogin.java
│                   ├── object
│                   │   ├── RequestAddObject.java
│                   │   ├── RequestAddObjectData.java
│                   │   ├── RequestUpdateObject.java
│                   │   ├── RequestUpdateObjectData.java
│                   │   ├── ResponseDeleteObject.java
│                   │   ├── ResponseGetListObjectById.java
│                   │   ├── ResponseGetListObjectByIdData.java
│                   │   ├── ResponseListAddObject.java
│                   │   ├── ResponseListAddObjectData.java
│                   │   ├── ResponseListUpdateObject.java
│                   │   └── ResponseListUpdateObjectData.java
│                   └── register
│                       ├── RequestRegister.java
│                       └── ResponseRegister.java
└── test
    ├── java
    │   ├── context
    │   │   └── ScenarioContext.java
    │   ├── cucumber
    │   │   ├── hooks
    │   │   │   └── Hooks.java
    │   │   ├── runners
    │   │   │   ├── ObjectRunner.java
    │   │   │   └── RegisterRunner.java
    │   │   └── stepdefinitions
    │   │       ├── AuthStepDefinitions.java
    │   │       ├── ObjectStepDefinitions.java
    │   │       └── RegisterStepDefinitions.java
    │   ├── e2e
    │   │   └── RestAssuredE2ETest.java
    │   ├── restassured
    │   │   └── restAssuredImpl.java
    │   └── utils
    │       └── ConfigReader.java
    └── resources
        ├── config.properties
        ├── cucumber.xml
        ├── testng.xml
        └── features
            ├── auth.feature
            ├── object_operations.feature
            └── register.feature
```

## How to Run
```bash
## Make sure you have:
Java 11 installed
Maven installed
Git (for cloning the repo)

# Clone the repository
git clone <repository-url>
cd api-testing

# Run Test Suites
✅ Run TestNG Tests
mvn clean test -Ptestng-tests -Denv=stg
mvn clean test -Ptestng-tests -Denv=prod

🎯 Run Cucumber BDD Tests
mvn clean test -Pcucumber-tests -Denv=stg
mvn clean test -Pcucumber-tests -Denv=prod
```

# Test Reports
- TestNG Reports : Located in target/surefire-reports/
- Cucumber Reports : Generated via cucumber.xml and can be viewed in a browser after running Cucumber tests. Located in target/cucumber-reports