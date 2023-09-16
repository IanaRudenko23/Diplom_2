import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.client.Client;
import org.example.client.ClientSteps;
import org.junit.Before;
import org.junit.Test;

import static org.example.client.ClientSteps.*;
import static org.hamcrest.Matchers.equalTo;

public class UpdateClientTest {
    String token;
    String newName = "{\n" +
            "\"name\":\"bunny.box1\"\n" +
            "}";
    String newPassword = "{\n" +
            "\"password\":\"bunny.box11\"\n" +
            "}";
    String newEmail = "{\n" +
            "\"email\":\"bunny.box11@mail.ru\"\n" +
            "}";
    @Before
    public void setUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    @DisplayName("Успешное обновление имени пользователя")
    public void updateClientWithNewName(){
        Client client = new Client("bunny.bunny771@yandex.ru","Bunny123451","Bunny101");
        Response response = createClient(client);
        response.then().assertThat().statusCode(200).and().body("success", equalTo(true));
        token = response.body().jsonPath().getString("accessToken");
        client = new Client(newName);
        token = response.body().jsonPath().getString("accessToken");
        response = updateClientWithToken(client,token);
        response.then().assertThat().statusCode(200).and().body("success",equalTo(true));
        ClientSteps.deleteClient(token);
    }
    @Test
    @DisplayName("Успешное обновление пароля пользователя")
    public void updateClientWithNewPassword(){
        Client client = new Client("bunny.bunny771@yandex.ru","Bunny123451","Bunny101");
        Response response = createClient(client);
        response.then().assertThat().statusCode(200).and().body("success", equalTo(true));
        token = response.body().jsonPath().getString("accessToken");
        client = new Client(newPassword);
        token = response.body().jsonPath().getString("accessToken");
        response = updateClientWithToken(client,token);
        response.then().assertThat().statusCode(200).and().body("success",equalTo(true));
        ClientSteps.deleteClient(token);
    }
    @Test
    @DisplayName("Успешное обновление email пользователя")
    public void updateClientWithNewEmail(){
        Client client = new Client("bunny.bunny771@yandex.ru","Bunny123451","Bunny101");
        Response response = createClient(client);
        response.then().assertThat().statusCode(200).and().body("success", equalTo(true));
        token = response.body().jsonPath().getString("accessToken");
        client = new Client(newEmail);
        token = response.body().jsonPath().getString("accessToken");
        response = updateClientWithToken(client,token);
        response.then().assertThat().statusCode(200).and().body("success",equalTo(true));
        ClientSteps.deleteClient(token);
    }

    @Test
    @DisplayName("Нельзя изменить данные пользователя без авторизации")
    public void updateClientWithoutLogin(){
        Client client = new Client("bunny.bunny771@yandex.ru","Bunny123451","Bunny101");
        Response response = createClient(client);
        response.then().assertThat().statusCode(200).and().body("success", equalTo(true));
        token = response.body().jsonPath().getString("accessToken");
        client = new Client(newName);
        token = response.body().jsonPath().getString("accessToken");
        response = updateClientWithoutToken(client);
        response.then().assertThat().statusCode(401).and().body("success",equalTo(false));
        System.out.println(response.body().print());
        ClientSteps.deleteClient(token);
    }
}
