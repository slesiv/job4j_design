package ru.job4j.io;

import java.io.*;
import java.util.*;

public class CSVReader {

    private List<List<String>> csvAllList = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ArgsName argsName = validationArgs(args);
        CSVReader csvr = new CSVReader();
        csvr.handle(argsName);
    }

    public void handle(ArgsName argsName) throws Exception {
        readData(argsName.get("path"));
        List<Integer> indexColumns = findIndexColumn(argsName.get("filter").split(","));
        List<String> selectCSV = new ArrayList<>();

        for (List<String> rowInCSV : csvAllList) {
            List<String> resultRow = new ArrayList<>();
            for (int index : indexColumns) {
                resultRow.add(rowInCSV.get(index));
            }
            selectCSV.add(String.join(";", resultRow));
        }

        if (argsName.get("out").equals("stdout")) {
            System.out.println(String.join(System.lineSeparator(), selectCSV));
        } else {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(argsName.get("out")))) {
                for (String s : selectCSV) {
                    bw.write(s);
                    bw.write(System.lineSeparator());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private  List<Integer> findIndexColumn(String[] filters) {
        List<Integer> indexColumns = new ArrayList<>();

        for (String filter : filters) {
            int index = csvAllList.get(0).indexOf(filter);
            if (index < 0) {
                throw new RuntimeException("Not found column: " + filter);
            }
            indexColumns.add(index);
        }
        return indexColumns;
    }

    private  void readData(String file) {
        try (Scanner scanner = new Scanner(new FileInputStream(file)).useDelimiter(",")) {
            while (scanner.hasNext()) {
                List<String> cells = Arrays.asList(scanner.nextLine().split(";"));
                csvAllList.add(cells);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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