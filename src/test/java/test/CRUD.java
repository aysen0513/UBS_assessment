package test;


import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.lessThan;

/*
Create (code)  a RESTful Web Service for with 5 URL mappings.

Return a list of Employees +
Create a new Employee +
Return all the Roles
Create a new Role for a User +
Associate a Role to a User +


Integration tests

As an API User,

Given the User Service

When I call the first URL mapping, and pass a new User JSON, it’s created on the Service



As an API User,

Given the User Service

When I call the third URL mapping, I get a list of Roles



As an API User,

Given the User Service

When I call the third URL mapping, I get a list of Users



As an API User,

Given the User Service

When I call the fourth URL mapping, and pass a new Role  JSON, it’s created on the Service





As an API User,

Given the User Service

When I call the fifth URL mapping, and pass a User-Role association JSON, the concerned role is applied to the User



Notes:

You can start with mock data.
Minimum attributes
Employee: First Name, Last Name, Employee ID
Role: Role Name
Write Unit (80% coverage) and the above automated Integration tests in the same source code.
 */
public class CRUD {

    @BeforeAll
    public static void setUp(){

        baseURI = "http://111.11.111.111:1111";

        basePath = "/api" ;


    }

    @AfterAll
    public static void tearDown(){

        reset();
    }



    @DisplayName("Create a new Employee using Map as body")
    @Test
    public void postRequest(){
        /*
            {
                "firstName": "Amir",
                "lastName": "Khali"
                "employeeID": 1000089,
                "roleName": "QA"
            }
         */

        Map<String, Object> employees = new HashMap<>();
        employees.put("firstName", "Amir");
        employees.put("lastName", "Khali");
        employees.put("employeeID", 1000089);
        employees.put("roleName", "QA");


                given()
                        .contentType(ContentType.JSON)
                        .body(employees)
                .when()
                        .post("/employees").
                then()
                        .statusCode(201)
                        .contentType(ContentType.JSON);



    }


    @DisplayName("Read newly created Employee")
    @Test
    public void getRequestForAnEmployee(){
        given()
                .pathParam("id", 1000089).
                when()
                .get("/employees/{id}").
                then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .time(lessThan(3000L), TimeUnit.MILLISECONDS);
    }


    @DisplayName("Update newly created Employee")
    @Test
    public void putRequest(){
        Map<String, Object> employees = new HashMap<>();
        employees.put("firstName", "Amir");
        employees.put("lastName", "Khali");
        employees.put("employeeID", 1000089);
        employees.put("roleName", "QA2");

        given()
                .body(employees)
                .contentType(ContentType.JSON)
                .pathParam("id", 1000089).
                when()
                .put("/employees/{id}").
                then()
                .statusCode(204)
                .time(lessThan(3000L), TimeUnit.MILLISECONDS);



    }

    public void getRequestForAllEmployees(){
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/employees").
                then()
                .statusCode(200)
                .contentType(ContentType.JSON);

    }

    public void getRequestForAllQAroles(){
        given()
                .pathParam("roleName", "QA").
                when()
                .get("/employees/{roleName}").
                then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .time(lessThan(3000L), TimeUnit.MILLISECONDS);

    }



}
