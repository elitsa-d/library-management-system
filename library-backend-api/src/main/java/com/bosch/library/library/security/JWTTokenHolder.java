package com.bosch.library.library.security;

public class JWTTokenHolder {

    //TODO Make it thread-safe
    private static String token;

    private JWTTokenHolder() {
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(final String jwt) {
        token = jwt;
    }
}
