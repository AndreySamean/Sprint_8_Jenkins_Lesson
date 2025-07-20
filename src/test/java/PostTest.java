import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static model.constants.Token.ACCESS_TOKEN;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.notNullValue;

public class PostTest {

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://qa-mesto.praktikum-services.ru";
    }

    @Test
    @DisplayName("Create new place and check response") // имя теста
    public void createNewPlaceAndCheckResponse(){

        File json = new File("src/test/resources/newCard.json");

        Response response = given()
                .header("Content-type", "application/json")
                .auth().oauth2(ACCESS_TOKEN)
                .and()
                .body(json)
                .when()
                .post("/api/cards");
        response.then().assertThat().body("data._id", notNullValue())
                .and()
                .statusCode(201);
    }

}
