package ru.job4j.searchfile;

import ru.job4j.io.ArgsName;
import ru.job4j.io.Search;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

public class SearchFile {

    /*
    For checking:
    -d=C:\projects\job4j_design\data -n=.+\.txt -t=regex -o=fileFound.txt
    -d=C:\projects\job4j_design\data -n=**.txt -t=mask -o=fileFound.txt
    */
    public static void main(String[] args) throws IOException {

        ArgsName argsName = ArgsName.of(args);
        Path dir = Paths.get(argsName.get("d"));
        String name = argsName.get("n");
        String type = argsName.get("t");
        String out = argsName.get("o");

        if (args.length != 4) {
            throw new IllegalArgumentException("Missing parameters. Usage java -jar dir.jar -d=start_folder -n=name/mask/regex -t=name/mask/regex -o=file.txt");
        }
        if (!dir.toFile().exists() && !dir.toFile().isDirectory()) {
            throw new IllegalArgumentException("Root folder must be a existing directory");
        }

        if ("name".equals(type)) {
            try (PrintWriter writer = new PrintWriter(out)) {
                Search.search(dir, p -> (name).equals(p.toFile().getName())).forEach(writer::println);
            }
        }

        if ("mask".equals(type)) {
            PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + name);
            try (PrintWriter writer = new PrintWriter(out)) {
                Search.search(dir, matcher::matches).forEach(writer::println);
            }
        }

        if ("regex".equals(type)) {
            PathMatcher matcher = FileSystems.getDefault().getPathMatcher("regex:" + name);
            try (PrintWriter writer = new PrintWriter(out)) {
                Search.search(dir, matcher::matches).forEach(writer::println);
            }
        }

        System.out.println("Search completed.");
    }
}
