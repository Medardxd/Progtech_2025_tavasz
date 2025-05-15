package com.carrental.auth;


public class LoggedInUser {
    private static int userId;

    public static void setUserId(int id) {
        userId = id;
    }

    public static int getUserId() {
        return userId;
    }
}

