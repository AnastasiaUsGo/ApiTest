package org.example.order;

import java.util.List;

public class Order {

    private List<String> color;

    public Order() {
    }

    public Order(List<String> color) {
        this.color = color;
    }

    public List<String> getColor() {
        return color;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }
}
