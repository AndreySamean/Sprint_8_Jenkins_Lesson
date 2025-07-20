import io.qameta.allure.Issue;
import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import io.qameta.allure.Description; // импорт Description
import io.qameta.allure.Step; // импорт Step
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static model.constants.Token.ACCESS_TOKEN;
import static org.hamcrest.Matchers.equalTo;

public class RestAssuredGetAllureTest {

    String bearerToken = ACCESS_TOKEN;

    @BeforeEach
    public void setUp() {

        RestAssured.baseURI= "https://qa-mesto.praktikum-services.ru";
    }

    @Test
    @DisplayName("Check status code of /users/me") // имя теста
    @Description("Basic test for /users/me endpoint") // описание теста
    void getMyInfoStatusCode() {
        given()
                .auth().oauth2(bearerToken)
                .get("/api/users/me")
                .then().statusCode(200);
    }

    @Test
    @DisplayName("Check user name") // имя теста
    @Description("Checking user name is very important") // описание теста
    void checkUserName() {
        given()
                .auth().oauth2(bearerToken)
                .get("/api/users/me")
                .then().assertThat().body("data.name",equalTo("Василий Васильев"));
    }

    @Test
    @DisplayName("Check user name and print response body") // имя теста
    @Description("This is a more complicated test with console output") // описание теста
    @TmsLink("TestCase-112") // ссылка на тест-кейс
    @Issue("BUG-985") // ссылка на баг-репорт
    public void checkUserNameAndPrintResponseBody() {

        Response response = sendGetRequestUsersMe();
        // отправили запрос и сохранили ответ в переменную response - экземпляр класса Response

        compareUserNameToText(response, "Василий Васильев");
        // проверили, что в теле ответа ключу name соответствует нужное имя пользователя

        printResponseBodyToConsole(response); // вывели тело ответа на экран

    }

    @Step("Send GET request to /api/users/me")
    public Response sendGetRequestUsersMe(){
        Response response =given().auth().oauth2(bearerToken).get("/api/users/me");
        return response;
    }

    // метод для шага "Сравнить имя пользователя с заданным":
    @Step("Compare user name to something")
    public void compareUserNameToText(Response response, String username){
        response.then().assertThat().body("data.name",equalTo(username));
    }

    // метод для шага "Вывести тело ответа в консоль":
    @Step("Print response body to console")
    public void printResponseBodyToConsole(Response response){
        System.out.println(response.body().asString());
    }

}