package ru.javaops.topjava2.web;

public class SecurityUtilTemp {
    private static int id = 1;

    private SecurityUtilTemp() {
    }

    public static int authUserId() {
        return id;
    }

    public static void setAuthUserId(int id) {
        SecurityUtilTemp.id = id;
    }

}
