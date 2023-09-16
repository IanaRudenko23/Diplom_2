import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.client.Client;
import org.example.client.ClientSteps;
import org.junit.Before;
import org.junit.Test;

import static org.example.client.ClientSteps.createClient;
import static org.example.client.ClientSteps.loginClient;
import static org.hamcrest.Matchers.equalTo;

public class LoginClientTest {
    String token;
    @Before
    public void setUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    @DisplayName("Логин существующего пользователя")
    public void loginOfExistedClient(){
        Client client = new Client("et.euismod@aol.com","8tOlfn#zg","Yvette Shields");
        Response response = createClient(client);
        token = response.body().jsonPath().getString("accessToken");
        client = new Client("et.euismod@aol.com","8tOlfn#zg");
        response = loginClient(client);
        token = response.body().jsonPath().getString("accessToken");
        response.then().assertThat().statusCode(200).and().body("success", equalTo(true));
        ClientSteps.deleteClient(token);
    }
    @Test
    @DisplayName("Попытка залогиниться с неверным логином и паролем")
    public void loginClientWithWrongCredits(){
        Client client = new Client("bunny.bunny22@yandex.ru","Bunny123451","Bunny22");
        Response response = createClient(client);
        response.then().assertThat().statusCode(200).and().body("success", equalTo(true));
        token = response.body().jsonPath().getString("accessToken");
        client = new Client("bunny.bunny22@","Bunny");
        response = loginClient(client);
        response.then().assertThat().statusCode(401).and().body("success", equalTo(false));
        ClientSteps.deleteClient(token);
    }

}
