package ru.job4j.io;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Analizy {
    public static void unavailable(String source, String target) {
        boolean errorStart = false;
        int code;
        String dateTimeStart = "";
        String dateTimeEnd;
        String lineLog;

        try (PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(target)));
                BufferedReader in = new BufferedReader(new FileReader(source))) {
                while ((lineLog = in.readLine()) != null) {
                    if (!lineLog.isEmpty()) {
                        code = Integer.parseInt(lineLog.substring(0, 3));
                        if (code == 500 || code == 400) {
                            if (!errorStart) {
                                dateTimeStart = lineLog.substring(4);
                                errorStart = true;
                            }
                        } else if (errorStart) {
                            dateTimeEnd = lineLog.substring(4);
                            errorStart = false;
                            out.write(dateTimeStart + ";" + dateTimeEnd + ";");
                            out.write(System.lineSeparator());
                        }
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        unavailable("logError.txt", "target.txt");
    }
}
