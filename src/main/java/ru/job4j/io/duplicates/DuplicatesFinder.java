package ru.job4j.io.duplicates;

import java.io.IOException;

public class DuplicatesFinder {
    public static void main(String[] args) throws IOException {
        DuplicatesVisitor dv = new DuplicatesVisitor();

        dv.createMapDuplicates("./").forEach((key, value) -> {
            System.out.println(key);
            value.forEach(System.out::println);
            System.out.println();
        });
    }
}
