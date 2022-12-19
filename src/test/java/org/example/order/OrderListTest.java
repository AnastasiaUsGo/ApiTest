package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.order.Order;
import org.example.order.OrderAssertations;
import org.example.order.OrderClient;
import org.junit.Test;

public class OrderListTest {

    private final OrderClient client = new OrderClient();
    private final OrderAssertations check = new OrderAssertations();

    @Test
    @DisplayName("Get List of orders")
    @Description("Проверим, что в тело ответа возвращается список заказов.")
    public void getListOfOrder(){
        Order order = new Order();
        Response response = client.get(order);
        check.getListOfOrders(response);
    }
}
