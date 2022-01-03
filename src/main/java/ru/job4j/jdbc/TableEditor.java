package ru.job4j.jdbc;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;
import java.util.StringJoiner;

public class TableEditor implements AutoCloseable {

    private Connection connection;

    private Properties properties;

    public TableEditor(Properties properties) {
        this.properties = properties;
        initConnection();
    }

    private void initConnection() {
        String url = properties.getProperty("postgres.url");
        String login = properties.getProperty("postgres.user");
        String password = properties.getProperty("postgres.password");

        try {
            connection = DriverManager.getConnection(url, login, password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void createTable(String tableName) {
        String createTable = String.format("CREATE TABLE %s();", tableName);
        buildSqlRequest(createTable);
    }

    public void dropTable(String tableName) {
        String dropTable = String.format("DROP TABLE %s;", tableName);
        buildSqlRequest(dropTable);
    }

    public void addColumn(String tableName, String columnName, String type) {
        String addColumn = String.format("ALTER TABLE %s ADD COLUMN %s %s;", tableName, columnName, type);
        buildSqlRequest(addColumn);
    }

    public void dropColumn(String tableName, String columnName) {
        String dropColumn = String.format("ALTER TABLE %s DROP COLUMN %s;", tableName, columnName);
        buildSqlRequest(dropColumn);
    }

    public void renameColumn(String tableName, String columnName, String newColumnName) {
        String renameColumn = String.format("ALTER TABLE %s RENAME COLUMN %s TO %s;", tableName, columnName, newColumnName);
        buildSqlRequest(renameColumn);
    }

    private void buildSqlRequest(String request) {
        try (var statement = connection.createStatement()) {
            statement.execute(request);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static String getTableScheme(Connection connection, String tableName) throws Exception {
        var rowSeparator = "-".repeat(30).concat(System.lineSeparator());
        var header = String.format("%-15s|%-15s%n", "NAME", "TYPE");
        var buffer = new StringJoiner(rowSeparator, rowSeparator, rowSeparator);
        buffer.add(header);
        try (var statement = connection.createStatement()) {
            var selection = statement.executeQuery(String.format(
                    "select * from %s limit 1", tableName
            ));
            var metaData = selection.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                buffer.add(String.format("%-15s|%-15s%n",
                        metaData.getColumnName(i), metaData.getColumnTypeName(i))
                );
            }
        }
        return buffer.toString();
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) throws Exception {
        var properties = new Properties();

        try (var fis = new FileInputStream("app.properties")) {
            properties.load(fis);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        var te = new TableEditor(properties);
        te.createTable("test");
        System.out.println(getTableScheme(te.connection, "test"));
        te.addColumn("test", "name", "varchar(100)");
        System.out.println(getTableScheme(te.connection, "test"));
        te.renameColumn("test", "name", "new_name");
        System.out.println(getTableScheme(te.connection, "test"));
        te.dropColumn("test", "new_name");
        System.out.println(getTableScheme(te.connection, "test"));
        te.dropTable("test");
        System.out.println("Table deleted");
    }
}