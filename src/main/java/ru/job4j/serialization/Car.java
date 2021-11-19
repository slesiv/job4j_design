package ru.job4j.serialization;

import javax.xml.bind.annotation.XmlAttribute;
import java.math.BigDecimal;

public class Car {

    @XmlAttribute
    private int id;
    @XmlAttribute
    private BigDecimal price;
    @XmlAttribute
    private String model;

    public Car() {
    }

    public Car(int id, BigDecimal price, String model) {
        this.id = id;
        this.price = price;
        this.model = model;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getModel() {
        return model;
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
