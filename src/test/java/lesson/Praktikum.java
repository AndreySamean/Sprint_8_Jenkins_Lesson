package lesson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;

import static model.constants.Token.ACCESS_TOKEN;

public class Praktikum {

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://qa-mesto.praktikum-services.ru";
    }

    @Test
    public void checkUserName() {
        User user = given()
                .auth().oauth2(ACCESS_TOKEN)
                .get("/api/users/me")
                .body().as(User.class);
        MatcherAssert.assertThat(user, notNullValue());

        Gson gson = new Gson();
        String userToJson = gson.toJson(user);
        System.out.println(userToJson);

        Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
        String userToJsonBuilder = gsonBuilder.toJson(user);
        System.out.println(userToJsonBuilder);
    }
}