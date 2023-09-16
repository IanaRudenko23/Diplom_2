import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.client.Client;
import org.example.client.ClientSteps;
import org.example.order.OrderSteps;
import org.junit.Before;
import org.junit.Test;
import static org.example.client.ClientSteps.createClient;
import static org.example.order.OrderSteps.createOrderWithIngredients;
import static org.example.order.OrderSteps.createOrderWithIngredientsAndAuth;
import static org.hamcrest.Matchers.equalTo;

public class CreateOrderTest {
    String token;
    String wrongHash ="{\n" +
            "\"ingredients\": [\"00c0c0a00d0f00000bdaaa0c\",\"00c0c0a00d0f00000bdaaa0c\"]\n" +
            "}";
    String hash = "{\n" +
            "\"ingredients\": [\"61c0c5a71d1f82001bdaaa6d\",\"61c0c5a71d1f82001bdaaa6c\"]\n" +
            "}";
    @Before
    public void setUp(){
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }
    @Test
    @DisplayName("Создание заказа с авторизацией пользователя с ингридиентами")
    public void createOrderWithAuthAndIngredients(){
        Client client = new Client("bunny.bunnyBox21@yandex.ru","Bunny123451","Bunny101");
        Response response = createClient(client);
        response.then().assertThat().statusCode(200).and().body("success", equalTo(true));
        token = response.body().jsonPath().getString("accessToken");
        response = createOrderWithIngredientsAndAuth(hash,token);
        response.then().assertThat().statusCode(200).and().body("success",equalTo(true));
        ClientSteps.deleteClient(token);
    }
    @Test
    @DisplayName("Создание заказа без авторизации пользователя с ингридиентами")
    public void createOrderWithoutAuthWithIngredients(){
        Response response = createOrderWithIngredients(hash);
        response.then().assertThat().statusCode(200).and().body("success",equalTo(true));
        System.out.println(response.body().print());
    }
    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void createOrderWithoutIngredients(){
        Response response = OrderSteps.createOrderWithoutIngredients();
        response.then().assertThat().statusCode(400).and().body("success",equalTo(false));
        System.out.println(response.body().print());
    }
    @Test
    @DisplayName("Создание заказа с неверным хешем ингридиентов")
    public void createOrderWithWrongHash(){
        Response response = createOrderWithIngredients(wrongHash);
        response.then().assertThat().statusCode(400).and().body("success",equalTo(false));
        System.out.println(response.body().print());
    }
}
