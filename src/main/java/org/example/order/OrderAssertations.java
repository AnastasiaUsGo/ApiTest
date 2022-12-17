package org.example.order;

import io.restassured.response.Response;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderAssertations {

    public void createdSuccessfully(Response response){
        response.then().log().all()
                .extract().response();
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
    }

    public void getListOfOrders(Response response){
        response.then().log().all()
                .assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }
}
