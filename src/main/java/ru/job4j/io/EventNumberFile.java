package ru.job4j.io;

import java.io.FileInputStream;

public class EventNumberFile {
    public static void main(String[] args) {
        try (FileInputStream in = new FileInputStream("even.txt")) {
            StringBuilder text = new StringBuilder();
            int read;
            while ((read = in.read()) != -1) {
                text.append((char) read);
            }

            int number = 0;
            String even = "";
            String[] lines = text.toString().split(System.lineSeparator());
            for (String line : lines) {
                number = Integer.parseInt(line);
                even = number % 2 == 0 ? "true" : "false";
                System.out.println(number + " - " + even);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
