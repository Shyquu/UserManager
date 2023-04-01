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

            PreparedStatement drop_stmt = UserScript.getConnection().prepareStatement("DROP TABLE ?");
            drop_stmt.setString(1, tableName);
            drop_stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createUser(String user, String password, String address, String table) {
        try {

            // PreparedStatement benutzen, damit man SQL immun gegen eine MITM-Attack macht

            PreparedStatement user_stmt = UserScript.getConnection().prepareStatement("CREATE USER ?@'%' IDENTIFIED BY ?");
            user_stmt.setString(1, user);
            user_stmt.setString(2, password);
            user_stmt.execute();

            PreparedStatement database_stmt = UserScript.getConnection().prepareStatement("CREATE DATABASE IF NOT EXISTS ?");
            database_stmt.setString(1, user + "-DB");
            database_stmt.execute();

            PreparedStatement grant_stmt = UserScript.getConnection().prepareStatement("GRANT ALL PRIVILEGES ON ?.* TO ?@?");
            grant_stmt.setString(1, user + "-DB");
            grant_stmt.setString(2, user);
            grant_stmt.setString(3, address);
            grant_stmt.execute();

            PreparedStatement insert_stmt = UserScript.getConnection().prepareStatement("INSERT INTO ?(`username`, `password`, `databasename`) VALUES(?, ?, ?)");
            insert_stmt.setString(1, table);
            insert_stmt.setString(2, user);
            insert_stmt.setString(3, password);
            insert_stmt.setString(4, user + "-DB");
            insert_stmt.execute();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}