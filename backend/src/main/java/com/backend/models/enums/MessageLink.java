package com.backend.models.enums;

/**
 * Enum representing various types of messages or error codes.
 *
 * This enum contains predefined constants that are used to define error messages, status codes, and other
 * related messages in the application. Each constant corresponds to a specific scenario such as a bad request,
 * an invalid email or password, permission issues, or internal system errors.
 * These messages are useful for providing consistent feedback to the user and for handling exceptions
 * throughout the application.
 */
public enum MessageLink {
    BAD_REQUEST,
    NOT_FOUND,
    NAME_TAKEN,
    NAME_INCORRECT,
    EMAIL_TAKEN,
    EMAIL_INCORRECT,
    WRONG_LOGIN_NAME,
    WRONG_LOGIN_EMAIL,
    WRONG_LOGIN_PASSWORD,
    PERMISSION_NOT_ALLOWED,
    NAME_CHANGE_FORBIDDEN,
    EMAIL_CHANGE_FORBIDDEN,
    ALREADY_EXISTS,
    INTERNAL_ERROR,
    UNKNOWN_ERROR,
    PASSWORD_TOO_WEAK,
    TOKEN_EXPIRED,
    FIELD_REQUIRED,
    DATA_CONFLICT,
    OPERATION_FAILED,
    ITEM_NOT_FOUND,
}

