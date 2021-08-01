package ru.job4j;

import java.util.*;

public class User {
    private String name;
    private int children;
    private Calendar birthday;

    public User(String name, int children, Calendar birthday) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return children == user.children && Objects.equals(name, user.name) && Objects.equals(birthday, user.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, children, birthday);
    }

    public static void main(String[] args) {
        String name = "Ilya";
        int children = 0;
        Calendar birthday = new GregorianCalendar(1992, 0, 24);
        User user1 = new User(name, children, birthday);
        User user2 = new User(name, children, birthday);
        Object object = new Object();

        Map<User, Object> mapUser = new HashMap<>();
        mapUser.put(user1, object);
        mapUser.put(user2, object);

        System.out.println(Objects.hashCode(user1));
        System.out.println(Objects.hashCode(user2));
        System.out.println(mapUser);
        System.out.println(user1.birthday.getTimeInMillis());
        System.out.println(user2.birthday.getTimeInMillis());
    }
}
