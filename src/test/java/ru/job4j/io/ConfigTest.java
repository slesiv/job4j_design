package ru.job4j.io;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;

import static org.junit.Assert.assertThat;

public class ConfigTest {

    @Test
    public void whenPairWithoutComment() {
        String path = "./app.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("hibernate.dialect"), is("org.hibernate.dialect.PostgreSQLDialect"));
        assertThat(config.value("hibernate.connection.url"), is("jdbc:postgresql://127.0.0.1:5432/trackstudio"));
        assertThat(config.value("hibernate.connection.driver_class"), is("org.postgresql.Driver"));
        assertThat(config.value("hibernate.connection.username"), is("postgres"));
        assertThat(config.value("hibernate.connection.password"), is("password"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenIncorrectKey() {
        String path = "./data/incorrectKey.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("key"), is("value"));

    }

    @Test(expected = IllegalArgumentException.class)
    public void whenIncorrectValue() {
        String path = "./data/incorrectValue.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("key"), is("value"));

    }

    @Test(expected = IllegalArgumentException.class)
    public void whenIncorrectComment() {
        String path = "./data/incorrectComment.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("key"), is("value"));
    }
}