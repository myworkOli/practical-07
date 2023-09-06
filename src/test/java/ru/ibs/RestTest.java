package ru.ibs;


import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pojos.Food;
import pojos.FoodSql;

import java.sql.SQLException;
import java.util.List;


public class RestTest {


    @TmsLink(value = "Проверка добавления товара")
    @ParameterizedTest
    @CsvSource({"Кивано # 1 (Новая Зеландия),VEGETABLE ,true", "Lemon (☼),FRUIT, false"})
    void test(String name, String type, boolean exotic) throws SQLException {
        int n;
        Food food = new Food();
        food.setName(name);
        food.setType(type);
        food.setExotic(exotic);
        String cookieId = RestMetod.getCookieId("http://localhost:8080/api", "/food");

        //Сколько товаров с характеристиками food уже есть в БД
        List<FoodSql> testFoodStart = Jdbc.getTestFoodString(food);

        n = testFoodStart.size();

        //добавили товар food
        RestMetod.addFood("http://localhost:8080/api", "/food", food, cookieId);

        // получили список товаров после добавления нового товарат (Get)
        List<Food> foodList = RestMetod.getFood("http://localhost:8080/api", "/food", cookieId);
        int row = foodList.size();

        Assertions.assertTrue(foodList.get(row - 1).getName().contains(food.getName()), "наименование добавлено не корректно");
        Assertions.assertTrue(foodList.get(row - 1).getType().contains(food.getType()), "Тип добавлено не корректно");
        Assertions.assertTrue(foodList.get(row - 1).isExotic() == (food.isExotic()), "Экзотический не корректно добавлен");

        //список из БД с характеристиками food
        List<FoodSql> testFoodString = Jdbc.getTestFoodString(food);

        //появилась новая запись в БД
        Assertions.assertTrue(testFoodString.size() > n, "в БД не появилась новая запись с характеристиками food");

        //Сброс тестовых данных
        RestMetod.deleteTestDate("http://localhost:8080/api", "/data/reset", cookieId);

        List<FoodSql> testFoodStringEnd = Jdbc.getTestFoodString(food);
        //
        Assertions.assertTrue(testFoodStringEnd.size() == n, "добавленная в тесте запись Удалена");

    }


}
