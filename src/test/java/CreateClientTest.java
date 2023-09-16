import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.client.Client;
import org.example.client.ClientSteps;
import org.junit.Before;
import org.junit.Test;

import static org.example.client.ClientSteps.createClient;
import static org.hamcrest.Matchers.equalTo;

public class CreateClientTest {
    String token;

    @Before
    public void setUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    @DisplayName("Успешно создали уникального пользователя")
    public void createNewClient(){
        Client client = new Client("bunny.bunny878@yandex.ru","Bunny12345","Bunny1768");
        Response response = createClient(client);
        response.then().assertThat().statusCode(200).and().body("success", equalTo(true));
        token = response.body().jsonPath().getString("accessToken");
        ClientSteps.deleteClient(token);
    }

    @Test
    @DisplayName("Создание уже зарегистрированного пользователя")
    public void createExistedClient(){
        Client client = new Client("bunny.bunny9142@yandex.ru","Bunny12345","Bunny17116");
        Response response = createClient(client);
        response.then().assertThat().statusCode(200).and().body("success", equalTo(true));
        token = response.body().jsonPath().getString("accessToken");
        response = createClient(client);
        response.then().assertThat().statusCode(403).and().body("success", equalTo(false));
        ClientSteps.deleteClient(token);
    }

    @Test
    @DisplayName("Создание пользователя без заполнения обязательного поля")
    public void createClientWithoutRequiredData(){
        Client client = new Client("bunny.bunny45@yandex.ru","Bunny12345");
        Response response = createClient(client);
        response.then().assertThat().statusCode(403).and().body("success", equalTo(false));
    }

}
