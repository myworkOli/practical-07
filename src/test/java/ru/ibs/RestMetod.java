package ru.ibs;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import pojos.Food;

import java.util.List;

import static io.restassured.RestAssured.given;
import static ru.ibs.Specifications.requestSpecification;

public class RestMetod {


    @Step("Получение всех данных таблицы")
    public static List<Food> getFood(String baseUri, String url) {

        List<Food> foodList = given()
                .spec(requestSpecification(baseUri))//---> Указание RequestSpecification для формирования request
                .get(url)//---> Endpoint для выполнения запроса GET
                .then()
                .extract()
                .jsonPath().getList("", Food.class);

        return foodList;
    }


    @Step("Добавление нового товара ")
    public static void addFood(String baseUri, String url, Food food) {

        RestAssured.given()
                .spec(requestSpecification(baseUri))//---> Указание RequestSpecification для формирования request
                .body(food)//---> body для запроса с методом POST
                .post(url)//---> Endpoint для выполнения запроса GET
                .then()
                .statusCode(200);//---> Проверка статус код
    }


    @Step("Сброс тестовых данных")
    public static void deleteTestDate(String baseUri, String url) {

        RestAssured.given()
                .spec(requestSpecification(baseUri))//---> Указание RequestSpecification для формирования request
                .body("")
                .post(url)//---> Endpoint для выполнения запроса GET
                .then()
                .statusCode(200);//---> Проверка статус код
    }






}