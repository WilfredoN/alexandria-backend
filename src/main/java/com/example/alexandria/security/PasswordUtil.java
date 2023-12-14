package com.example.alexandria.security;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtil {
    private PasswordUtil() {
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}