import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.client.Client;
import org.example.client.ClientSteps;
import org.example.order.OrderSteps;
import org.junit.Before;
import org.junit.Test;
import static org.example.client.ClientSteps.createClient;
import static org.example.order.OrderSteps.createOrderWithIngredientsAndAuth;
import static org.hamcrest.Matchers.equalTo;

public class GetOrdersTest {
    String token;
    String hash = "{\n" +
            "\"ingredients\": [\"61c0c5a71d1f82001bdaaa6d\",\"61c0c5a71d1f82001bdaaa6c\"]\n" +
            "}";
    @Before
    public void setUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    @DisplayName("Получение информации о заказах авторизованного пользователя")
    public void getOrdersOfAuthClient(){
        Client client = new Client("bunny.bunnyBumm@yandex.ru","Bunny123451","BunnyBumm");
        Response response = createClient(client);
        response.then().assertThat().statusCode(200).and().body("success", equalTo(true));
        token = response.body().jsonPath().getString("accessToken");
        createOrderWithIngredientsAndAuth(hash,token);
        response = OrderSteps.getOrdersOfAuthClient(token);
        response.then().assertThat().statusCode(200).and().body("success",equalTo(true));
        System.out.println(response.body().print());
        ClientSteps.deleteClient(token);
    }
    @Test
    @DisplayName("Получение заказов пользователя без авторизации")
    public void getOrdersWithoutAuth(){
        Response response = OrderSteps.getOrdersOfNotAuthClient();
        response.then().assertThat().statusCode(401).and().body("success",equalTo(false));
        System.out.println(response.body().print());
    }
}
