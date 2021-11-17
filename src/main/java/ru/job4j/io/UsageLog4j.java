package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsageLog4j {

    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());

    public static void main(String[] args) {
        boolean married = true;
        byte countPets = 1;
        short age = 29;
        int region = 178;
        long tel = 9012223344L;
        float height = 175.8f;
        double weight = 74.55;
        char sex = 'M';

        LOG.debug("About me(lie), sex: {}, married: {}, countPets: {}, age: {}, region: {}, tel: {}, height: {}, weight: {}", sex, married, countPets, age, region, tel, height, weight);
    }
}