package ru.job4j.io.duplicates;

import java.io.IOException;

public class DuplicatesFinder {
    public static void main(String[] args) throws IOException {
        DuplicatesVisitor dv = new DuplicatesVisitor();

        dv.createMapDuplicates("./").entrySet().stream()
                .forEach(entry -> {
                    System.out.println(entry.getKey());
                    entry.getValue().forEach(System.out::println);
                    System.out.println();
                });
    }
}
