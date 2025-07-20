import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static model.constants.Token.ACCESS_TOKEN;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class GetTest {

    // аннотация BeforeEach показывает, что метод будет выполняться перед каждым тестовым методом
    @BeforeEach
    public void setUp() {
        // повторяющуюся для разных ручек часть URL лучше записать в переменную в методе Before
        // если в классе будет несколько тестов, указывать её придётся только один раз
        RestAssured.baseURI = "https://qa-mesto.praktikum-services.ru";
    }

    // создаём метод автотеста
    @Test
    @DisplayName("Check status code of /users/me") // имя теста
    @Description("Basic test for /users/me endpoint") // описание теста
    void getMyInfoStatusCode() {
        // метод given() помогает сформировать запрос
        given()
                // указываем протокол и данные авторизации
                .auth().oauth2(ACCESS_TOKEN)
                // отправляем GET-запрос с помощью метода get, недостающую часть URL (ручку) передаём в него в качестве параметра
                .get("/api/users/me")
                // проверяем, что статус-код ответа равен 200
                .then().statusCode(200);
    }

    @Test
    @DisplayName("Check user name") // имя теста
    @Description("Checking user name is very important") // описание теста
    void checkUserName() {
        given()
                .auth().oauth2(ACCESS_TOKEN)
                .get("/api/users/me")
                .then().assertThat().body("data.name", equalTo("Василий Васильев"));
    }

    @Test
    @DisplayName("Check Header And Print Response Headers") // имя теста
    @Description("This is a more complicated test with console output") // описание теста
    void checkHeaderAndPrintResponseHeaders() {

        // отправляет запрос и сохраняет ответ в переменную response, экзмепляр класса Response
        Response response = given().auth().oauth2(ACCESS_TOKEN)
                .get("/api/users/me");
        // проверяет, что в теле ответа ключу name соответствует нужное имя пользователя
        response.then().assertThat().header("X-XSS-Protection", equalTo("0"));
        //;body("data.name",equalTo("Аристарх Сократович"));
        // выводит тело ответа на экран
        System.out.println(response.headers());
    }

    @Test
    @DisplayName("Check Cards Status Code")
    void checkCardsStatusCode() {
        // проверяем статус-код ответа на запрос «Получение всех карточек»
        given()
                .auth().oauth2(ACCESS_TOKEN)
                .get("/api/cards")
                .then().statusCode(200);
    }

    @Test
    @DisplayName("Check User Activity And Print Response Body")
    void checkUserActivityAndPrintResponseBody() {

        // отправляет запрос и сохраняет ответ в переменную response, экзмепляр класса Response
      Response response = given().auth().oauth2(ACCESS_TOKEN).get("/api/users/me");
        // проверяет, что в теле ответа ключу about соответствует нужное занятие
        response.then().assertThat().body("data.about", equalTo("Самый крутой исследователь"));
        // выводит тело ответа на экран
        System.out.println(response.body().asString());

    }
}