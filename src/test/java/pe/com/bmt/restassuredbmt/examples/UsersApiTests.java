package pe.com.bmt.restassuredbmt.examples;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import pe.com.bmt.restassuredbmt.config.UsersApiConfig;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsersApiTests extends UsersApiConfig {

    private Response response;

    private RequestSpecification request;

    @Test
    public void testGetAllUsers(){

        given().
        when()
                .get("users").
        then()
                .statusCode(200);
    }

    @Test
    public void testCreateNewUser(){

        String userBody = "      {\n" +
                "        \"name\": \"Test User\",\n" +
                "        \"username\": \"testuser\",\n" +
                "        \"email\": \"test@user.com\",\n" +
                "        \"address\": {\n" +
                "          \"street\": \"Has No Name\",\n" +
                "          \"suite\": \"Apt. 123\",\n" +
                "          \"city\": \"Electri\",\n" +
                "          \"zipcode\": \"54321-6789\"\n" +
                "        }\n" +
                "      }";

        given()
                .body(userBody).
        when()
                .post("users").
                then()
                .statusCode(201);
    }

    @Test
    public void testGetASingleUserData(){

        given()
                .pathParam("id",1).
        when()
                .get("users/{id}").
        then()
                .statusCode(200);
    }

    @Test
    public void testUpdateUser(){

        String userBody = "      {\n" +
                "        \"name\": \"Jose Hurtado\",\n" +
                "        \"username\": \"testuser\",\n" +
                "        \"email\": \"test@user.com\",\n" +
                "        \"address\": {\n" +
                "          \"street\": \"Has No Name\",\n" +
                "          \"suite\": \"Apt. 123\",\n" +
                "          \"city\": \"Electri\",\n" +
                "          \"zipcode\": \"54321-6789\"\n" +
                "        }\n" +
                "      }";

        given()
                .pathParam("id",1)
                .body(userBody).
        when()
                .put("users/{id}").
        then()
                .statusCode(200);
    }

    @Test
    public void testDeleteUser(){

        given()
                .pathParam("id",1).
        when()
                .delete("users/{id}").
        then()
                .statusCode(200);
    }

    @Test
    public void testUserValidateSchemaJSON() {

        given()
                .pathParam("id", 1).
        when()
                .get("users/{id}").
        then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("user_schema.json"));
    }

    @Test
    public void testValidateUserData(){

        given()
                .pathParam("id",1).
        when()
                .get("users/{id}").
        then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("user_schema.json"))
                .body("id", equalTo(1))
                .body("name", equalTo("Leanne Graham"))
                .body("username", equalTo("Bret"))
                .body("email", equalTo("Sincere@april.biz"))
                .body("address.street", equalTo("Kulas Light"));
    }

    @Test
    public void testValidateUserDataUsingJsonPath(){

            request = given()
                    .pathParam("id",1);

            response = request
                    .when()
                        .get("users/{id}");

            response
                    .then()
                    .statusCode(200)
                    .body(matchesJsonSchemaInClasspath("user_schema.json"));


            JsonPath actualData = new JsonPath(response.getBody().asString());

            assertEquals(1, actualData.getInt("id"),"El id no es el correcto");
            assertEquals("Leanne Graham", actualData.getString("name"),"El name no es el correcto");
            assertEquals("Bret", actualData.getString("username"),"El username no es el correcto");
            assertEquals("Sincere@april.biz", actualData.getString("email"),"El email no es el correcto");
            assertEquals("Kulas Light", actualData.getString("address.street"),"La street no es el correcto");

    }
}
