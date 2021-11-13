package rest.api.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

@Epic("REST-API Tests")
@Feature("API tests for site reqres.in")
public class SiteReqresAPITests extends TestBase{
    @Test
    @DisplayName("Create User")
    public void createUserTest() {
        given()
                .contentType(JSON)
                .body("{\"name\": \"vasili\", \"job\": \"AQA-Team\"}")
                .when()
                .post(("https://reqres.in/api/users"))
                .then()
                .statusCode(201)
                .body("name", is("vasili"),
                        "job", is("AQA-Team"),
                                             "id", notNullValue());
    }

    @Test
    @DisplayName("Update User")
    public void updateUserTest() {
        given()
                .contentType(JSON)
                .body("{\"name\": \"vasili\", \"job\": \"AQA-Team\"}")
                .when()
                .put(("https://reqres.in/api/users/2"))
                .then()
                .statusCode(200)
                .body("name", is("vasili"),
                        "job", is("AQA-Team"),
                        "updatedAt", notNullValue());
    }

    @Test
    @DisplayName("Delete User")
    public void deleteUserTest() {
        given()
                .when()
                .delete(("https://reqres.in/api/users/2"))
                .then()
                .statusCode(204);
    }

    @Test
    @DisplayName("Login Test")
    public void loginTest() {
        given()
                .contentType(JSON)
                .body("{\"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}")
                .when()
                .post(("https://reqres.in/api/register"))
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @DisplayName("Get list")
    void getListTest() {
        given()
                .get("https://reqres.in/api/unknown")
                .then()
                .statusCode(200)
                .body("page", is(1),
                        "data.name", hasItem("cerulean"));
    }
}
