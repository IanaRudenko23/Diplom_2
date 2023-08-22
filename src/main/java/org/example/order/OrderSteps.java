package org.example.order;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderSteps{
    private static final String BASE_URI_ORDERS = "/api/orders";
    @Step("Создать заказ с ингридиентами")
    public static Response createOrderWithIngredients(String hash){
        return given()
                .header("Content-type", "application/json")
                .body(hash)
                .when()
                .post(BASE_URI_ORDERS);
    }
    @Step("Создать заказ с ингридиентами и авторизацией")
    public static Response createOrderWithIngredientsAndAuth(String hash, String token){
        return given()
                .header("Content-type", "application/json")
                .header("Authorization",token)
                .body(hash)
                .when()
                .post(BASE_URI_ORDERS);
    }
    @Step("Создать заказ без ингридиентов")
    public static Response createOrderWithoutIngredients(){
        return given()
                .header("Content-type", "application/json")
                .when()
                .post(BASE_URI_ORDERS);
    }
    @Step("Получение заказов авторизованного пользователя")
    public static Response getOrdersOfAuthClient(String token){
        return given()
                .header("Content-type", "application/json")
                .header("Authorization",token)
                .and()
                .when()
                .get(BASE_URI_ORDERS);
    }
    @Step("Получение заказов гнавторизованного пользователя")
    public static Response getOrdersOfNotAuthClient(){
        return given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .get(BASE_URI_ORDERS);
    }
}
