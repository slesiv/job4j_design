package ru.job4j.question;

import java.util.*;
import java.util.stream.Collectors;

public class Analize {

    public static Info diff(Set<User> previous, Set<User> current) {
        int add = 0;
        int change = 0;
        int del = 0;

        Map<Integer, String> currentMap = current.stream().collect(Collectors.toMap(User::getId, User::getName));

        for (User user : previous) {
            Integer id = user.getId();
            String name = currentMap.get(id);
            if (name != null) {
                User u = new User(id, name);
                if (!u.equals(user)) {
                    change++;
                }
            } else {
                del++;
            }
            currentMap.remove(id);
        }
        add = currentMap.size();

        return new Info(add, change, del);
    }
}
