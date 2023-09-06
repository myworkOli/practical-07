package ru.ibs;

import io.qameta.allure.Step;
import org.springframework.jdbc.core.JdbcTemplate;
import pojos.Food;
import pojos.FoodSql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ru.ibs.BDConnection.getH2DataSource;

public class Jdbc {

    public static JdbcTemplate jdbcTemplate;


    @Step("Получение списка записей соответствующих добавленному товару")
    public static List<FoodSql> getTestFoodString(Food food) throws SQLException {
        String sql = "SELECT * FROM FOOD WHERE FOOD_NAME= ? and FOOD_TYPE=? and FOOD_EXOTIC=? ";
        List<FoodSql> foodTest = new ArrayList<>();

        try (Connection connection = BDConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, food.getName());
            ps.setString(2, food.getType());
            ps.setInt(3, food.isExotic()?1:0);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    foodTest.add(new FoodSql(rs.getInt("food_Id"),
                            rs.getString("food_name"),
                            rs.getString("food_type"),
                            rs.getInt("FOOD_EXOTIC")));

                }
            }

            return foodTest;

        }
    }
}
