package org.swe266.bankappbackend.utils;

public class ValidationUtils {
    public static boolean isValidPasswordOrUsername(String input) {
        if (input == null){
            return false;
        }
        String pattern = "^[_\\-.0-9a-z]{1,127}$";
        return input.matches(pattern);
    }

    public static boolean isValidAmount(double input) {
        return !(input < 0.00) && !(input > 4294967295.99);
    }
}
