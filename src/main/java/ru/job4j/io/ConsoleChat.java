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
    private List<String> phrases = new ArrayList<>();

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
        readPhrases();
    }

    public void run() {
        String msgClient = "";
        Scanner scanner = new Scanner(System.in);
        int stop = 0;

        while (!msgClient.equals(OUT)) {
            System.out.print("Введите сообщение: ");
            msgClient = scanner.nextLine();
            log.add("Сообщение клиента: " + msgClient);
            if (msgClient.equals(OUT)) {
                break;
            }
            if (stop != 1 && msgClient.equals(STOP)) {
                System.out.println("Сообщение бота: Беседа остановлена, для продолжения введите \"продолжить\".");
                log.add("Сообщение бота: Беседа остановлена, для продолжения введите \"продолжить\".");
                stop++;
            }
            if (stop == 0 || msgClient.equals(CONTINUE)) {
                String msgBot = getRandomPhrase(phrases);
                System.out.println("Сообщение бота: " + msgBot);
                log.add("Сообщение бота: " + msgBot);
                stop = 0;
            }
        }

        scanner.close();
        saveLog(log);
    }

    private void readPhrases() {
        try (BufferedReader br = new BufferedReader(new FileReader(botAnswers, Charset.forName("WINDOWS-1251")))) {
            phrases = br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveLog(List<String> log) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, Charset.forName("WINDOWS-1251")))) {
            for (String line : log) {
                bw.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getRandomPhrase(List<String> phrases) {
        Random random = new Random();
        if (!phrases.isEmpty()) {
            return phrases.get(random.nextInt(phrases.size()));
        }
        return "Словарь для ответов отсутствует";
    }

    public static void main(String[] args) {
        ConsoleChat cc = new ConsoleChat("log.txt", "./data/ListWordMSWORD.txt");
        cc.run();
    }
}
