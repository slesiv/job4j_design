package ru.job4j.io;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Config {

    private final String path;
    private Map<String, String> values = new HashMap<>();

    public Config(final String path) {
        this.path = path;
    }

    /**
     * В методе сначала происходит проверка на пустую строку
     * А потом на корректность строки (начинается с = || не содержит =)
     */
    public void load() {
        try (BufferedReader in = new BufferedReader(
                new FileReader(this.path))) {
            String read;
            while ((read = in.readLine()) != null) {
                if (read.matches("#.*|^?$")) {
                    continue;
                } else if (read.matches("^=.*") || !read.contains("=")) {
                    throw new IllegalArgumentException();
                }
                String[] kv = read.split("=");
                if (kv.length == 1) {
                    this.values.put(kv[0], "");
                } else {
                    this.values.put(kv[0], kv[1]);
                }
            }

            this.values.entrySet().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String value(String key) {
        if (values.isEmpty()) {
            return null;
        }
        return values.get(key);
    }

    @Override
    public String toString() {
        StringJoiner out = new StringJoiner(System.lineSeparator());
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines().forEach(out::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    public static void main(String[] args) {
        Config config = new Config("app.properties");
        config.load();

    }
}