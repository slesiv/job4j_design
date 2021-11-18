package ru.job4j.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Car car = new Car(123, new BigDecimal("2345000.55"), "Toyota Rav");
        DealCard dc = new DealCard(1, "Buy Toyota Rav", true, new String[] {"Ivan", "Petr"}, car);

        final Gson gson = new GsonBuilder().create();
        System.out.println(gson.toJson(dc));

        final String dcJson = "{"
                                + "\"id\":1,"
                                + "\"name\":\"Buy Toyota Rav\","
                                + "\"isActive\":true,"
                                + "\"buyers\":"
                                + "[\"Ivan\",\"Petr\"],"
                                + "\"car\":"
                                + "{"
                                + "\"id\":123,"
                                + "\"price\":2345000.55,"
                                + "\"model\":\"Toyota Rav\""
                                + "}"
                                + "}";
        final DealCard dcMod = gson.fromJson(dcJson, DealCard.class);
        System.out.println(dcMod);
    }
}
