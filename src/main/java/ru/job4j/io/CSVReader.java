package ru.job4j.io;

import java.io.*;
import java.util.*;

public class CSVReader {

    public static void main(String[] args) throws Exception {
        ArgsName argsName = validationArgs(args);
        CSVReader csvr = new CSVReader();
        csvr.handle(argsName);
    }

    public void handle(ArgsName argsName) throws Exception {
        int i = 0;
        List<Integer> indexColumns = new ArrayList<>();

        try (Scanner scanner = new Scanner(new FileInputStream(argsName.get("path"))).useDelimiter(",");
             BufferedWriter bw = new BufferedWriter(new FileWriter(argsName.get("out")))) {

            while (scanner.hasNext()) {
                List<String> rowInCSV = Arrays.asList(scanner.nextLine().split(";"));

                if (i == 0) {
                    indexColumns = findIndexColumn(argsName.get("filter"), rowInCSV);
                    i++;
                }

                List<String> resultRow = new ArrayList<>();
                for (int index : indexColumns) {
                    resultRow.add(rowInCSV.get(index));
                }

                if (argsName.get("out").equals("stdout")) {
                    System.out.println(String.join(";", resultRow));
                } else {
                    bw.write(String.join(";", resultRow));
                    bw.write(System.lineSeparator());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private  List<Integer> findIndexColumn(String filters, List<String> str) {
        List<Integer> indexColumns = new ArrayList<>();
        String[] filtersArray = filters.split(",");
        for (String filter : filtersArray) {
            int index = str.indexOf(filter);
            if (index < 0) {
                throw new RuntimeException("Not found column: " + filter);
            }
            indexColumns.add(index);
        }
        return indexColumns;
    }

    private static ArgsName validationArgs(String[] args) {
        if (args.length != 4) {
            throw new IllegalArgumentException("The following arguments are available and required: \n-path=\"file address\" \n-out=\"file name to save OR \"stdout\" for console output.\" \n-delimetr=\"delimiter in csv\" \n-filter=\"name columns for output (name,age,...)\"\n");
        }
        ArgsName argsName = ArgsName.of(args);
        isValidArg(argsName, "path", "-path=\"file address\"");
        isValidArg(argsName, "out", "-out=\"file name to save OR \"stdout\" for console output.\"");
        isValidArg(argsName, "delimiter", "-delimiter=\"delimiter in csv\"");
        isValidArg(argsName, "filter", "-filter=\"name columns for output (name,age,...)\"");

        File file = new File(argsName.get("path"));
        if (!file.exists()) {
            throw new IllegalArgumentException("Not found folder \"" + file + "\"");
        }
        return argsName;
    }

    private static void isValidArg(ArgsName argsName, String arg, String msgError) {
        String necessary = "Is necessary argument ";
        if (argsName.get(arg) == null) {
            throw new IllegalArgumentException(necessary + msgError);
        }
    }
}