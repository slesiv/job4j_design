package ru.job4j.io.duplicates;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.Collectors;

public class DuplicatesVisitor extends SimpleFileVisitor<Path> {

    private final Map<FileProperty, List<Path>> mapFileAndPaths = new HashMap<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        putMapFaP(file);
        return super.visitFile(file, attrs);
    }

    public Map<FileProperty, List<Path>> createMapDuplicates(String start) throws IOException {
        Files.walkFileTree(Path.of(start), this);
        return mapFileAndPaths.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void putMapFaP(Path file) {
        FileProperty fileProp = new FileProperty(file.toFile().length(), file.toFile().getName());
        if (!mapFileAndPaths.containsKey(fileProp)) {
            List<Path> paths = new ArrayList<>();
            paths.add(file.toAbsolutePath());
            mapFileAndPaths.put(fileProp, paths);
        } else {
            mapFileAndPaths.get(fileProp).add(file.toAbsolutePath());
        }
    }
}
