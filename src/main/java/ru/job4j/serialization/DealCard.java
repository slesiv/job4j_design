package ru.job4j.serialization;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.bind.*;
import javax.xml.bind.annotation.*;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DealCard {

    @XmlAttribute
    private int id;
    @XmlAttribute
    private String name;
    @XmlAttribute
    private boolean isActive;

    @XmlElementWrapper
    @XmlElement(name = "buyer")
    private String[] buyers;
    private Car car;

    public DealCard() {
    }

    public DealCard(int id, String name, boolean isActive, String[] buyers, Car car) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
        this.buyers = buyers;
        this.car = car;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return isActive;
    }

    public String[] getBuyers() {
        return buyers;
    }

    public Car getCar() {
        return car;
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

    public static void main(String[] args) throws Exception {
        JSONObject jsonCar = new JSONObject("{"
                + "\"id\":123,"
                + "\"price\":2345000.55,"
                + "\"model\":\"Toyota Rav\""
                + "}");

        List<String> buyers = new ArrayList<>();
        buyers.add("Denis");
        buyers.add("Alex");
        JSONArray jsonBuyers = new JSONArray(buyers);

        Car car = new Car(123, new BigDecimal("2345000.55"), "Toyota Rav");
        DealCard dealcard = new DealCard(1, "Buy Toyota Rav", true, new String[] {"Ivan", "Petr"}, car);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", dealcard.getId());
        jsonObject.put("name", dealcard.getName());
        jsonObject.put("isActive", dealcard.isActive());
        jsonObject.put("buyers", jsonBuyers);
        jsonObject.put("car", jsonCar);

        System.out.println(jsonObject);

        System.out.println(new JSONObject(dealcard));


        /*Car car = new Car(123, new BigDecimal("2345000.55"), "Toyota Rav");
        DealCard dealcard = new DealCard(1, "Buy Toyota Rav", true, new String[] {"Ivan", "Petr"}, car);

        JAXBContext context = JAXBContext.newInstance(DealCard.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        String xml = "";
        try (StringWriter writer = new StringWriter()) {
            marshaller.marshal(dealcard, writer);
            xml = writer.getBuffer().toString();
            System.out.println(xml);
        }

        Unmarshaller unmarshaller = context.createUnmarshaller();
        try (StringReader reader = new StringReader(xml)) {
            DealCard result = (DealCard) unmarshaller.unmarshal(reader);
            System.out.println(result);
        }*/
    }
}
