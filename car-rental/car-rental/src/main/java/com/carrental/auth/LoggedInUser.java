package com.carrental.auth;

public final class LoggedInUser {

    private static User current;

    private LoggedInUser() {}

    public static void set(User u)  { current = u; }
    public static User  get()       { return current; }

    /* new helpers */
    public static int  id()         { return current == null ? -1 : current.id(); }
    public static String name()     { return current == null ? "" : current.username(); }
    public static boolean isAdmin() { return current != null && current.admin(); }

    /* compatibility with older code */
    public static int getUserId() { return id(); }
}
