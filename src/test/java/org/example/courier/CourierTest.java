package courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.courier.Courier;
import org.example.courier.CourierAssertations;
import org.example.courier.CourierClient;
import org.example.courier.CourierGenerator;
import org.junit.After;
import org.junit.Test;

public class CourierTest {

    private final CourierGenerator generator = new CourierGenerator();
    private final CourierClient client = new CourierClient();
    private final CourierAssertations check = new CourierAssertations();
    private int courierId;

    @Test
    @DisplayName("Create a random courier and verify him")
    @Description("Проверим, что курьера можно создать(нужно передать в ручку все обязательные поля, " +
            "запрос возвращает правильный код ответа, успешный запрос возвращает ok: true.")
    public void createCourier() {
        Courier courier = generator.random();
        Response response = client.create(courier);
        check.createdSuccessfully(response);
        Response responseId = client.login(courier);
        responseId.then().log().all();
        courierId = responseId.path("id");//вычислили курьера по айди
    }

    @Test
    @DisplayName("Create courier without password")
    @Description("Проверим, что если одного из полей нет, запрос возвращает ошибку.")
    public void createCourierWithoutPassword() {
        Courier courier = generator.generic();
        courier.setPassword(null);
        Response response = client.create(courier);
        check.createdWithoutPasswordFailed(response);
    }

    @Test
    @DisplayName("Create two same couriers")
    @Description("Проверим, что если создать пользователя с логином, который уже есть, возвращается ошибка.")
    public void createCourierTwice() {
        Courier courier = generator.random();
        client.create(courier);
        Response response = client.create(courier);
        check.createdFailed(response);
    }

    @Test
    @DisplayName("Successful courier authorization")
    @Description("Проверим, что курьер может авторизоваться (успешный запрос возвращает id).")
    public void loginCourier() {
        Courier courier = generator.loginCredentials();
        Response response = client.login(courier);
        check.loginSuccessfully(response);
    }

    @Test
    @DisplayName("Log in with wrong password")
    @Description("Проверим, что система вернёт ошибку, если неправильно указать пароль.")
    public void loginCourierWithWrongPassword() {
        Courier courier = generator.loginCredentials();
        courier.setPassword("alan111");
        Response response = client.login(courier);
        check.loginFailed(response);
    }

    @Test
    @DisplayName("Log in without password")
    @Description("Проверим, что если какого-то поля нет, запрос возвращает ошибку.")
    public void loginCourierWithoutPassword(){
        Courier courier = generator.loginCredentials();
        courier.setPassword("");
        Response response = client.login(courier);
        check.loginWithoutPasswordFailed(response);
    }

    @Test
    @DisplayName("Log in as a non-existent user")
    @Description("Проверим, что если авторизоваться под несуществующим пользователем, запрос возвращает ошибку.")
    public void loginNotExistedCourier() {
        Courier courier = generator.loginCredentials();
        courier.setLogin("Maria");
        courier.setPassword("password");
        Response response = client.login(courier);
        check.loginFailed(response);
    }

    @After
    public void deleteCourier() {
        if (courierId > 0) {
            Response responseDelete = client.delete(courierId);//удалили курьера по айди
            check.deleteSuccessfully(responseDelete);//проверили ответ
        }
    }
}


