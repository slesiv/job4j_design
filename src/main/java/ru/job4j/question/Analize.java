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

    public static void main(String[] args) {
        User u1 = new User(1, "A");
        User u2 = new User(2, "B");
        User u3 = new User(3, "C");
        User u4 = new User(3, "D");

        Set<User> previous = new HashSet<>();
        previous.add(u1);
        previous.add(u2);
        //previous.add(u4);

        Set<User> current = new HashSet<>();
        current.add(u1);
        current.add(u2);
        current.add(u3);

        Info i = diff(previous, current);
        System.out.println(i);
    }
}
