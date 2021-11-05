package ru.job4j.io.duplicates;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class DuplicatesVisitor extends SimpleFileVisitor<Path> {

    private final Map<FileProperty, List<Path>> files = new HashMap<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        FileProperty fileProp = new FileProperty(file.toFile().length(), file.toFile().getName());
        if (!files.containsKey(fileProp)) {
            List<Path> paths = new ArrayList<>();
            paths.add(file.toAbsolutePath());
            files.put(fileProp, paths);
        } else {
            files.get(fileProp).add(file.toAbsolutePath());
        }
        return super.visitFile(file, attrs);
    }

    public Map<FileProperty, List<Path>> getFiles() {
        return files;
    }
}
