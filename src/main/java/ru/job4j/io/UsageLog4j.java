package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsageLog4j {

    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());

    public static void main(String[] args) {
        String firstName = "Ilia";
        boolean married = true;
        byte countPets = 1;
        short age = 29;
        int region = 178;
        long tel = 9012223344L;
        float height = 175.8f;
        double weight = 74.55;

        LOG.debug("About me(lie), name: {}, married: {}, countPets: {}, age: {}, region: {}, tel: {}, height: {}, weight: {}", firstName, married, countPets, age, region, tel, height, weight);
    }
}