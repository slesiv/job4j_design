package ru.job4j.io;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ConsoleChat {

    private final String path;
    private final String botAnswers;
    private static final String OUT = "закончить";
    private static final String STOP = "стоп";
    private static final String CONTINUE = "продолжить";
    private final List<String> log = new ArrayList<>();

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
    }

    public void run() {
        String msgClient = "";
        Scanner scanner = new Scanner(System.in);

        start:
        while (!msgClient.equals(OUT)) {
            msgClient = writeMsgClient(scanner);
            if (msgClient.equals(STOP)) {
                writeMsgBot("Беседа остановлена, для продолжения введите \"продолжить\".");
                while (!msgClient.equals(CONTINUE)) {
                    msgClient = writeMsgClient(scanner);
                    if (msgClient.equals(OUT)) {
                        break start;
                    }
                }
                writeMsgBot(getRandomPhrase(readPhrases()));
            } else if (!msgClient.equals(OUT)) {
                writeMsgBot(getRandomPhrase(readPhrases()));
            }
        }

        scanner.close();
        saveLog(log);
    }

    private List<String> readPhrases() {
        try (BufferedReader br = new BufferedReader(new FileReader(botAnswers, Charset.forName("WINDOWS-1251")))) {
            return br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private void saveLog(List<String> log) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (String line : log) {
                bw.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String writeMsgClient(Scanner scanner) {
        System.out.print("Введите сообщение: ");
        String msg = scanner.nextLine();
        log.add("Сообщение клиента: " + msg);
        return msg;
    }

    private void writeMsgBot(String response) {
        System.out.println("Сообщение бота: " + response);
        log.add("Сообщение бота: " + response);
    }

    private String getRandomPhrase(List<String> phrases) {
        Random random = new Random();
        if (!phrases.isEmpty()) {
            return phrases.get(random.nextInt(phrases.size()));
        }
        return "Словарь для ответов отсутствует";
    }

    public static void main(String[] args) {
        ConsoleChat cc = new ConsoleChat("log.txt", "./data/listWords.txt");
        cc.run();
    }
}
