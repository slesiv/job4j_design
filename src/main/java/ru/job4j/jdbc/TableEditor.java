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
        try (var fis = new FileInputStream("app.properties")) {
            properties.load(fis);
            String url = properties.getProperty("postgres.url");
            String login = properties.getProperty("postgres.user");
            String password = properties.getProperty("postgres.password");
            connection = DriverManager.getConnection(url, login, password);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }

    public void createTable(String tableName) {
        final String CREATE_TABLE = "CREATE TABLE " + tableName + "();";
        buildSqlRequest(CREATE_TABLE);
    }

    public void dropTable(String tableName) {
        final String DROP_TABLE = "DROP TABLE " + tableName + ";";
        buildSqlRequest(DROP_TABLE);
    }

    public void addColumn(String tableName, String columnName, String type) {
        final String ADD_COLUMN = "ALTER TABLE " + tableName
                + " ADD COLUMN " + columnName + " " + type + ";";
        buildSqlRequest(ADD_COLUMN);
    }

    public void dropColumn(String tableName, String columnName) {
        final String DROP_COLUMN = "ALTER TABLE " + tableName
                + " DROP COLUMN " + columnName + ";";
        buildSqlRequest(DROP_COLUMN);
    }

    public void renameColumn(String tableName, String columnName, String newColumnName) {
        final String RENAME_COLUMN = "ALTER TABLE " + tableName
                + " RENAME COLUMN " + columnName + " TO " + newColumnName + ";";
        buildSqlRequest(RENAME_COLUMN);
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