package ru.job4j.serialization;

import javax.xml.bind.*;
import javax.xml.bind.annotation.*;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Arrays;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DealCard {

    @XmlAttribute
    private int id;
    @XmlAttribute
    private String name;
    @XmlAttribute
    private boolean isActive;
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
        Car car = new Car(123, new BigDecimal("2345000.55"), "Toyota Rav");
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
        }
    }
}
