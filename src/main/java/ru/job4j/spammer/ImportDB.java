package ru.job4j.spammer;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ImportDB {

    private Properties cfg;
    private String dump;

    public ImportDB(Properties cfg, String dump) {
        this.cfg = cfg;
        this.dump = dump;
    }

    public List<User> load() throws IOException {
        List<User> users = new ArrayList<>();

        try (BufferedReader rd = new BufferedReader(new FileReader(dump))) {
            String lineFile;
            while ((lineFile = rd.readLine()) != null) {
                String[] lineUser = lineFile.split(";");
                if (lineUser.length != 2 || lineUser[0].isEmpty() || lineUser[1].isEmpty()) {
                    throw new IllegalArgumentException("There must be two non-empty parameters. Example: Name;Email;");
                }
                User u = new User(lineUser[0], lineUser[1]);
                users.add(u);
            }
        }
        return users;
    }

    public void save(List<User> users) throws ClassNotFoundException, SQLException {
        Class.forName(cfg.getProperty("postgres.driver"));
        try (Connection cnt = DriverManager.getConnection(
                cfg.getProperty("postgres.url"),
                cfg.getProperty("postgres.user"),
                cfg.getProperty("postgres.password")
        )) {
            for (User user : users) {
                try (PreparedStatement ps = cnt.prepareStatement("insert into users(name, email) values (?, ?)")) {
                    ps.setString(1, user.name);
                    ps.setString(2, user.email);
                    ps.execute();
                }
            }
        }
    }

    private static class User {
        String name;
        String email;

        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }

    public static void main(String[] args) throws Exception {
        Properties cfg = new Properties();
        try (FileInputStream in = new FileInputStream("app.properties")) {
            cfg.load(in);
        }
        ImportDB db = new ImportDB(cfg, "dump.txt");
        db.save(db.load());
    }
}