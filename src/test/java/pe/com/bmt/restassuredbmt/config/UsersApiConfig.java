package pe.com.bmt.restassuredbmt.config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class UsersApiConfig {

    private static RequestSpecification requestSpecification;

    private static ResponseSpecification responseSpecification;

    @BeforeAll
    public static void setup(){

        requestSpecification = new RequestSpecBuilder()
                        .setBaseUri("https://jsonplaceholder.typicode.com")
                        .setBasePath("/")
                        .addHeader("Content-Type", "application/json")
                        .addFilter(new RequestLoggingFilter())
                        .addFilter(new ResponseLoggingFilter())
                        .build();

        responseSpecification = new ResponseSpecBuilder()
                        .build();

        RestAssured.requestSpecification = requestSpecification;
        RestAssured.responseSpecification = responseSpecification;
    }

    @AfterAll
    public static void cleanUp(){
        RestAssured.reset();
    }
}