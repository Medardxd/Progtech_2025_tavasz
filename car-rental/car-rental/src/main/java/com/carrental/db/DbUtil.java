package com.carrental.db;

import java.nio.file.Paths;
import java.sql.*;

public final class DbUtil {

    /** single, absolute JDBC URL that everyone in the app should use */
    public static final String URL =
            "jdbc:sqlite:" +                          // driver prefix
                    Paths.get("car-rental.db")                // relative → module dir
                            .toAbsolutePath()                    // turn into full path
                            .normalize()                         // tidy up “..” etc.
                            .toString();

    /* ------------------------------------------------------------------ */
    /*  initialise schema once (runs the first time the class is touched) */
    /* ------------------------------------------------------------------ */
    static {
        try (Connection conn = DriverManager.getConnection(URL);
             Statement  stmt = conn.createStatement()) {

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS users (
                    id       INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT UNIQUE NOT NULL,
                    password TEXT NOT NULL
                )
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS cars (
                    carID          INTEGER PRIMARY KEY AUTOINCREMENT,
                    licensePlate   TEXT UNIQUE NOT NULL,
                    pricePerDay    REAL  NOT NULL,
                    brand          TEXT  NOT NULL,
                    model          TEXT  NOT NULL,
                    yearOfCreation INTEGER NOT NULL,
                    type           TEXT  NOT NULL              -- <-- added !
                )
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS rentals (
                    id         INTEGER PRIMARY KEY AUTOINCREMENT,
                    startDate  TEXT NOT NULL,
                    endDate    TEXT NOT NULL,
                    carID      INTEGER NOT NULL,
                    userID     INTEGER NOT NULL,
                    fullPrice  REAL    NOT NULL,
                    FOREIGN KEY(carID) REFERENCES cars(carID),
                    FOREIGN KEY(userID) REFERENCES users(id)
                )
            """);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /* ------------------------------------------------------------------ */
    /*  helper functions                                                  */
    /* ------------------------------------------------------------------ */

    public static boolean register(String user, String pass) {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps =
                     conn.prepareStatement("""
                         INSERT INTO users(username,password)
                         VALUES(?,?)
                     """)) {

            ps.setString(1, user);
            ps.setString(2, pass);
            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            return false;   // most likely “username already taken”
        }
    }

    public static boolean checkCredentials(String user, String pass) {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps =
                     conn.prepareStatement("""
                         SELECT password
                         FROM users
                         WHERE username = ?
                     """)) {

            ps.setString(1, user);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getString(1).equals(pass);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static int getUserIdByUsername(String username) {
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement ps =
                     conn.prepareStatement("""
                         SELECT id
                         FROM users
                         WHERE username = ?
                     """)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("id") : -1;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /* utility – for callers that need a raw Connection */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    /* no instances */
    private DbUtil() {}
}
