package ru.job4j.io;

import java.util.*;
import java.util.stream.Collectors;

public class ArgsName {

    private Map<String, String> values = new HashMap<>();

    public String get(String key) {
        return values.get(key);
    }

    private void parse(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Need parameters");
        } else {
            values = Arrays.stream(args)
                    .filter(x -> {
                        if (!x.contains("-")) {
                            throw new IllegalArgumentException("The parameter is specified incorrectly, the \"-\" symbol is missing");
                        } else if (!x.contains("=")) {
                            throw new IllegalArgumentException("The parameter is specified incorrectly, the \"=\" symbol is missing");
                        }
                        return true; })
                    .map(str -> str.substring(1).split("="))
                    .collect(Collectors.toMap(s -> {
                        if (s[0].isEmpty()) {
                            throw new IllegalArgumentException("Key should not contains null");
                        }
                        return s[0];
                    }, s -> {
                        if (s.length <= 1) {
                            throw new IllegalArgumentException("Value should not contains null");
                        }
                        return s[1];
                    }));
        }
    }

    public static ArgsName of(String[] args) {
        ArgsName names = new ArgsName();
        names.parse(args);
        return names;
    }

    public static void main(String[] args) {
        ArgsName jvm = ArgsName.of(new String[] {"-Xmx=512", "-encoding=UTF-8"});
        System.out.println(jvm.get("Xmx"));

        ArgsName zip = ArgsName.of(new String[] {"-out=project.zip", "-encoding=UTF-8"});
        System.out.println(zip.get("out"));
    }
}
