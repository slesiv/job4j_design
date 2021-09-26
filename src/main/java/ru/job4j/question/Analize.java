package ru.job4j.question;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

public class Analize {

    public static Info diff(Set<User> previous, Set<User> current) {
        int add = 0;
        int change = 0;
        int del = 0;

        for (User user : current) {
            if (!previous.contains(user)) {
                if (user.changed(previous)) {
                    change++;
                    continue;
                }
                add++;
            }
        }
        for (User user : previous) {
            if (!current.contains(user)) {
                if (user.changed(current)) {
                    continue;
                }
                del++;
            }
        }

        return new Info(add, change, del);
    }
}
