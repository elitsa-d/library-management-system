package com.bosch.library.library.security;

public class JWTTokenHolder {
    private static final InheritableThreadLocal<String> inheritableThread = new InheritableThreadLocal<>();

    private JWTTokenHolder() {
    }

    public static void setToken(final String jwt) {
        inheritableThread.set(jwt);
    }

    public static String getToken() {
        return inheritableThread.get();
    }
}
