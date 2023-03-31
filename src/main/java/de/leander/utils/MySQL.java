package de.leander.utils;

import de.leander.main.UserScript;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQL {
    public static Connection connect() {
        final Connection connection;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://" + UserScript.getAddress() + ":3306/" + UserScript.getDatabase(), UserScript.getUsername(), UserScript.getPassword());
        } catch (SQLException | ClassNotFoundException e) {

            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void setup() {
        try {
            UserScript.getConnection().createStatement().execute(
                    ("CREATE TABLE IF NOT EXISTS LK01(`LK-ID` INTEGER primary key AUTO_INCREMENT, username text not null, password text not null, databasename text not null)"));
            UserScript.getConnection().createStatement().execute(
                    ("CREATE TABLE IF NOT EXISTS GK01(`GK-ID` INTEGER primary key AUTO_INCREMENT, username text not null, password text not null, databasename text not null)"));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public static void deleteDatabse(String tableName) {
        try {
            UserScript.getConnection().createStatement().execute("DROP TABLE `" + tableName + "``");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createUser(String user, String password, String address, String table) {
        try {

            UserScript.getConnection().createStatement().execute("CREATE USER '" + user + "'@'" + address + "' IDENTIFIED BY '" + password + "'");
            UserScript.getConnection().createStatement().execute("CREATE DATABASE IF NOT EXISTS `" + user + "-DB`");
            UserScript.getConnection().createStatement().execute("GRANT ALL PRIVILEGES ON `" + user + "-DB`.* TO " + user + "@" + address + "");
            PreparedStatement stmt = UserScript.getConnection().prepareStatement("INSERT INTO `" + table + "`(`username`, `password`, `databasename`) VALUES(?, ?, ?)");
            stmt.setString(1, user);
            stmt.setString(2, password);
            stmt.setString(3, user + "-DB");
            stmt.execute();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

}

