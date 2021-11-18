package ru.job4j.serialization;

import java.math.BigDecimal;

public class Car {
    private int id;
    private BigDecimal price;
    private String model;

    public Car(int id, BigDecimal price, String model) {
        this.id = id;
        this.price = price;
        this.model = model;
    }

    @Override
    public String toString() {
        return "Car{"
                + "id=" + id
                + ", price=" + price
                + ", model='" + model + '\''
                + '}';
    }
}
