package de.leander.main;

import de.leander.utils.MySQL;

import javax.sound.midi.Soundbank;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Scanner;

public class UserScript {

    static Connection connection;
    static String database, address, username, password, kurs, table;
    static Integer usercount;
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // -----------------------------------------------------------------------
        System.out.println("Bitte Adresse eingeben: ");
        setAddress(scanner.next());
        System.out.println("Adresse: " + getAddress());

        System.out.println("Bitte Datenbank eingeben: ");
        setDatabase(scanner.next());
        System.out.println("Datenbank: " + getDatabase());

        System.out.println("Bitte Nutzernamen eingeben: ");
        setUsername(scanner.next());
        System.out.println("Nutzernamen: " + getUsername());

        System.out.println("Bitte Passwort eingeben: ");
        setPassword(scanner.next());
        System.out.println("Passwort: " + getPassword());

        // Verbindung mit der Datenbank aufbauen
        // -----------------------------------------------------------------------
        connection = MySQL.connect();
        MySQL.setup();
        System.out.println("Verbindung mit der Datenbank hergestellt!");
        // -----------------------------------------------------------------------
        // Kriterien abfragen

        System.out.println("Wie viele Nutzer?: ");
        setUsercount(scanner.nextInt());
        System.out.println("Nutzer: " + getUsercount());

        System.out.println("LK oder GK (LK oder GK schreiben)?: ");
        setKurs(scanner.next());
        System.out.println("Kurs: " + getKurs());

        System.out.println("In welche Tabelle einfuegen?: ");
        setTable(scanner.next());
        System.out.println("Tabelle: " + getTable());

        // -----------------------------------------------------------------------
        // Nutzer erstellen

        for (int i=1; i<getUsercount()+1; i++) {
            MySQL.createUser(kurs + new DecimalFormat("00").format(i), String.format("%04d", new Random().nextInt(10000)), getAddress(), getTable());
        }
        // -----------------------------------------------------------------------

        System.out.println("Erfolgreich!");

    }

    public static String getKurs() {
        return kurs;
    }

    public static void setKurs(String kurs) {
        UserScript.kurs = kurs;
    }

    public static String getTable() {
        return table;
    }

    public static void setTable(String table) {
        UserScript.table = table;
    }

    public static Integer getUsercount() {
        return usercount;
    }

    public static void setUsercount(Integer usercount) {
        UserScript.usercount = usercount;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static String getDatabase() {
        return database;
    }

    public static void setDatabase(String database) {
        UserScript.database = database;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        UserScript.address = address;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        UserScript.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UserScript.password = password;
    }
}
