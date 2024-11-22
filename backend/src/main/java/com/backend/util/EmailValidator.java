package com.backend.util;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class to validate email addresses using a regular expression pattern.
 * This class checks if the given email matches a predefined pattern of a valid email format.
 */
@Component
public class EmailValidator {

    // Regular expression pattern for matching valid email addresses.
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    // Compile the regular expression into a Pattern object.
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    /**
     * Validates the format of the given email address using a regular expression.
     *
     * @param email the email address to validate.
     * @return true if the email matches the pattern, false otherwise.
     */
    public static boolean validate(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}