import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.JSON_objects.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static model.constants.Token.ACCESS_TOKEN;
import static org.hamcrest.Matchers.equalTo;

public class PatchGsonTest {

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://qa-mesto.praktikum-services.ru";
    }

    @Test
    @DisplayName("Update profile and check status code") // имя теста
    public void updateProfileAndCheckStatusCode(){
        Profile profile = new Profile("Василий Васильев", "Самый крутой исследователь");

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2(ACCESS_TOKEN)
                        .and()
                        .body(profile)
                        .when()
                        .patch("/api/users/me");
        response.then().assertThat().statusCode(200)
                .and().body("data.name", equalTo("Василий Васильев"));
    }

}
