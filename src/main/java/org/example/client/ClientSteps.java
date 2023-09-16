package org.example.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ClientSteps {
    private static final String BASE_PATH_REGISTER = "/api/auth/register";
    private static final String BASE_PATH_LOGIN = "/api/auth/login";
    private static final String BASE_PATH_UPDATE = "/api/auth/user";
    private static final String BASE_PATH_DELETE = "/api/auth/user";
    @Step("Метод создания клиента")
    public static Response createClient (Client client){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(client)
                .when()
                .post(BASE_PATH_REGISTER);
    }

    @Step("Метод авторизации клиента")
    public static Response loginClient (Client client){
        return given()
                .header("Content-type", "application/json")
                .body(client)
                .when()
                .post(BASE_PATH_LOGIN);
    }

    @Step("Метод изменения данных клиента с токеном")
    public static Response updateClientWithToken(Client client, String token){
        return given()
                .header("Content-type", "application/json")
                .header("Authorization",token)
                .and()
                .body(client)
                .when()
                .patch(BASE_PATH_UPDATE);
    }
    @Step("Метод изменения данных клиента без токена")
    public static Response updateClientWithoutToken(Client client){
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(client)
                .when()
                .patch(BASE_PATH_UPDATE);
    }

    @Step("Метод удаления пользователя")
    public static Response deleteClient(String token){
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .when()
                .delete(BASE_PATH_DELETE);
    }

}
