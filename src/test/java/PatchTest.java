import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static model.constants.Token.ACCESS_TOKEN;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PatchTest {

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://qa-mesto.praktikum-services.ru";
    }

    @Test
    @DisplayName("Update profile and check status code") // имя теста
    void updateProfileAndCheckStatusCode() {
        File json = new File("src/test/resources/updateProfile.json"); // запиши файл в файловую переменную
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2(ACCESS_TOKEN)
                        .and()
                        .body(json)
                        .when()
                        .patch("/api/users/me");
        response.then().assertThat().statusCode(200)
                .and().body("data.name", equalTo("Василий Васильев"));
    }
}
