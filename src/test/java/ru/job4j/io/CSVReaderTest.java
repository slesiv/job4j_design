package ru.job4j.io;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Files;

public class CSVReaderTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void whenFilterTwoColumns() throws Exception {
        String data = String.join(
                System.lineSeparator(),
                "name;age;last_name;education",
                "Tom;20;Smith;Bachelor",
                "Jack;25;Johnson;Undergraduate",
                "William;30;Brown;Secondary special"
        );
        File file = temporaryFolder.newFile("source1.csv");
        File target = temporaryFolder.newFile("target1.csv");
        ArgsName argsName = ArgsName.of(new String[]{
                "-path=" + file.getAbsolutePath(), "-delimiter=;", "-out=" + target.getAbsolutePath(), "-filter=name,age"
        });
        Files.writeString(file.toPath(), data);
        String expected = String.join(
                System.lineSeparator(),
                "name;age",
                "Tom;20",
                "Jack;25",
                "William;30"
        ).concat(System.lineSeparator());
        CSVReader csvr = new CSVReader();
        csvr.handle(argsName);
        Assert.assertEquals(expected, Files.readString(target.toPath()));
    }

    @Test
    public void whenFilterThreeColumns() throws Exception {
        String data = String.join(
                System.lineSeparator(),
                "name;age;last_name;education",
                "Tom;;Smith;Bachelor",
                "Jack;25;Johnson;Undergraduate",
                "William;30;Brown;Secondary special"
        );
        File file = temporaryFolder.newFile("source2.csv");
        File target = temporaryFolder.newFile("target2.csv");
        ArgsName argsName = ArgsName.of(new String[]{
                "-path=" + file.getAbsolutePath(), "-delimiter=;", "-out=" + target.getAbsolutePath(), "-filter=name,age,education"
        });
        Files.writeString(file.toPath(), data);
        String expected = String.join(
                System.lineSeparator(),
                "name;age;education",
                "Tom;;Bachelor",
                "Jack;25;Undergraduate",
                "William;30;Secondary special"
        ).concat(System.lineSeparator());
        CSVReader csvr = new CSVReader();
        csvr.handle(argsName);
        Assert.assertEquals(expected, Files.readString(target.toPath()));
    }
}