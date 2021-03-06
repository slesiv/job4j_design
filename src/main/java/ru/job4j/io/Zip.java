package ru.job4j.io;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    private final String[] excludeFile;
    private final String nameZip;
    private final String directoryForPack;

    public static void main(String[] args) {
        ArgsName argsName = validationArgs(args);
        Zip zip = new Zip(argsName.get("o"), argsName.get("d"), argsName.get("e").split(","));
        zip.packZip();
        System.out.println("zip successfully packing");
    }

    public Zip(String nameZip, String directoryForPack, String[] excludeFile) {
        this.nameZip = nameZip;
        this.directoryForPack = directoryForPack;
        this.excludeFile = excludeFile;
    }

    public void packZip() {
        File file = new File(directoryForPack);
        try (ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(nameZip)))) {
            packDir(Arrays.asList(file.listFiles()), zos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void packDir(List<File> files, ZipOutputStream zos) {
        for (File file : files) {
            if (file.isDirectory()) {
                List<File> filesList = Arrays.asList(file.listFiles());
                if (filesList.isEmpty()) {
                    packEmptyDir(file, zos);
                } else {
                    packDir(filesList, zos);
                }
            } else if (!isFileForExclude(file, excludeFile)) {
                packSingleFile(file, zos);
            }
        }
    }

    private void packSingleFile(File file, ZipOutputStream zos) {
        try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(file))) {
            zos.putNextEntry(new ZipEntry(file.getPath() + file.getName()));
            zos.write(out.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void packEmptyDir(File file, ZipOutputStream zos) {
        try {
            zos.putNextEntry(new ZipEntry(file.getPath() + "/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isFileForExclude(File file, String[] exclude) {
        for (String s : exclude) {
            if (getFileExtension(file.toString()).equals(s)) {
                return true;
            }
        }
        return false;
    }

    private String getFileExtension(String file) {
        int index = file.lastIndexOf(".");
        if (index == -1) {
            return "";
        }
        return file.substring(index + 1);
    }

    private static ArgsName validationArgs(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("The following arguments are available and required: \n-d=\"directory for packing\", \n-o=\"name for zip\", \n-e=\"extensions file fo exclude(txt,xml,...)\", \nNo other arguments are accepted.\n");
        }
        ArgsName argsName = ArgsName.of(args);
        isValidArg(argsName, "d", "-d=\"directory for packing\"");
        isValidArg(argsName, "o", "-o=\"name for zip\"");
        isValidArg(argsName, "e", "-e=\"extensions file fo exclude(txt,xml,...)\"");

        File d = new File(argsName.get("d"));
        if (!d.isDirectory()) {
            throw new IllegalArgumentException("Not found folder \"" + d + "\"");
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