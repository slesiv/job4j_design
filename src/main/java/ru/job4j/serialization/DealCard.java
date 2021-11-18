package ru.job4j.serialization;

import java.util.Arrays;

public class DealCard {
    private int id;
    private String name;
    private boolean isActive;
    private String[] buyers;
    private Car car;

    public DealCard(int id, String name, boolean isActive, String[] buyers, Car car) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
        this.buyers = buyers;
        this.car = car;
    }

    @Override
    public String toString() {
        return "DealCard{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", isActive=" + isActive
                + ", buyers=" + Arrays.toString(buyers)
                + ", car=" + car
                + '}';
    }
}
