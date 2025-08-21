package com.solvd.taxi.util;

import java.util.regex.Pattern;

public class Validator {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\+?[0-9]{10,15}$");
    private static final Pattern LICENSE_PATTERN =
            Pattern.compile("^[A-Z]{2}[0-9]{6}$");

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean isValidLicense(String license) {
        return license != null && LICENSE_PATTERN.matcher(license).matches();
    }

    public static boolean isValidRating(double rating) {
        return rating >= 1.0 && rating <= 5.0;
    }

    public static boolean isPositiveNumber(double number) {
        return number > 0;
    }
}