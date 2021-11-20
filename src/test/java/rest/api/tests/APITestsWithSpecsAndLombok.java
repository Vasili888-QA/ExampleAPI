package rest.api.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rest.api.lombok.LombokUserData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static rest.api.Specs.request;
import static rest.api.Specs.responseSpec;

public class APITestsWithSpecsAndLombok {
    @Test
    @DisplayName("Get list")
    void getListTest() {
        given()
                .spec(request)
                .when()
                .get("/unknown")
                .then()
                .spec(responseSpec)
                .log().body()
                .body("data.findAll{it.name}.name.flatten()", hasItem("cerulean"))
                .body("data.findAll{it.id}.id.flatten()", hasItem(1))
                .body("data.findAll{it.year}.year.flatten()", hasItem(2000))
                .body("data.findAll{it.color}.color.flatten()", hasItem("#98B2D1"));
    }

    @Test
    void checkUserWithLombok() {
        LombokUserData data = given()
                .spec(request)
                .when()
                .get("/users/2")
                .then()
                .spec(responseSpec)
                .log().body()
                .extract().as(LombokUserData.class);
        assertEquals(2, data.getUser().getId());
        assertEquals("janet.weaver@reqres.in", data.getUser().getEmail());
        assertEquals("Janet", data.getUser().getFirstName());
        assertEquals("Weaver", data.getUser().getLastName());
    }

    @Test
    public void checkUserData() {
        given()
                .spec(request)
                .when()
                .get("/users")
                .then()
                .spec(responseSpec)
                .log().body()
                .body("page", is(1))
                .body("data.findAll{it.id}.id.flatten()", hasItem(1))
                .body("data.findAll{it.email =~/.*?@reqres.in/}.email.flatten()", hasItem("tracey.ramos@reqres.in"))
                .body("data.findAll{it.first_name}.first_name.flatten()", hasItem("Charles"));
    }
}
