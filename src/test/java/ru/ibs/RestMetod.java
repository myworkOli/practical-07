package ru.ibs;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import pojos.Food;

import java.net.CookieHandler;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static ru.ibs.Specifications.requestSpecification;

public class RestMetod {


    @Step("Получение всех данных таблицы")
    public static List<Food> getFood(String baseUrl, String url, String cookie) {

        List<Food> foodList = given()
                .spec(requestSpecification(baseUrl,cookie))//---> Указание RequestSpecification для формирования request
                .get(url)//---> Endpoint для выполнения запроса GET
                .then()
                .extract()
                .jsonPath().getList("", Food.class);

        return foodList;
    }

    @Step("Получение ID сессия")
    public static String  getCookieId(String baseUri, String url) {

        Response response= given()
                .baseUri(baseUri)//---> Cтартовая URL
                .get(url)//---> Endpoint для выполнения запроса GET
                ;
        String cookieValue = response.getCookie("JSESSIONID");

        return cookieValue;
    }
    Cookie cookie;



    @Step("Добавление нового товара ")
    public static void addFood(String baseUrl, String url, Food food, String cookie) {

        RestAssured.given()
                .spec(requestSpecification(baseUrl,cookie))
                .body(food)//---> body для запроса с методом POST
                .post(url)//---> Endpoint для выполнения запроса GET
                .then()
                .statusCode(200);//---> Проверка статус код
    }


    @Step("Сброс тестовых данных")
    public static void deleteTestDate(String baseUrl, String url,String cookie) {

        RestAssured.given()
                .spec(requestSpecification(baseUrl,cookie))//---> Указание RequestSpecification для формирования request
                .body("")
                .post(url)//---> Endpoint для выполнения запроса GET
                .then()
                .statusCode(200);//---> Проверка статус код
    }




}