package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.order.Order;
import org.example.order.OrderAssertations;
import org.example.order.OrderClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class OrderTest {

    private final OrderClient client = new OrderClient();
    private final OrderAssertations check = new OrderAssertations();
    private final List color;

    public OrderTest(List color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] colorOfScooter() {
        return new Object[][]{
                { Arrays.asList("BLACK") },
                { Arrays.asList("GREY") },
                { Arrays.asList("BLACK", "GREY") },
                { Arrays.asList("") },
        };
    }

    @Test
    @DisplayName("Create an order")
    @Description("Проверим, что когда создаёшь заказ можно указать один из цветов — BLACK или GREY, " +
            "можно указать оба цвета, можно совсем не указывать цвет, тело ответа содержит track.")
    public void orderCreation() {
        Order order = new Order(color);
        Response response = client.create(order);
        check.createdSuccessfully(response);
    }
}
