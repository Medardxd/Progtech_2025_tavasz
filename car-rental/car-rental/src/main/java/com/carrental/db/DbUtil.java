package com.carrental.db;

import java.sql.*;

public final class DbUtil {
    private static final String URL = "jdbc:sqlite:car-rental.db";

    static {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS users(
                  id INTEGER PRIMARY KEY AUTOINCREMENT,
                  username TEXT UNIQUE NOT NULL,
                  password TEXT NOT NULL
                )
            """);
            stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS cars(
                carID INTEGER PRIMARY KEY AUTOINCREMENT,
                licensePlate TEXT UNIQUE NOT NULL,
                pricePerDay REAL NOT NULL,
                brand TEXT NOT NULL,
                model TEXT NOT NULL,
                yearOfCreation INTEGER NOT NULL
            )
        """);
            stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS rentals(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                startDate TEXT NOT NULL,
                endDate TEXT NOT NULL,
                carID INTEGER NOT NULL,
                userID INTEGER NOT NULL,
                fullPrice REAL NOT NULL,
                FOREIGN KEY(carID) REFERENCES cars(carID),
                FOREIGN KEY(userID) REFERENCES users(id)
            )
        """);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean register(String user, String pass) {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO users(username,password) VALUES(?,?)")) {
            ps.setString(1, user);
            ps.setString(2, pass);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean checkCredentials(String user, String pass) {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT password FROM users WHERE username=?")) {
            ps.setString(1, user);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getString(1).equals(pass);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static int getUserIdByUsername(String username) {
        int userId = -1;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM users WHERE username = ?")) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                userId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
