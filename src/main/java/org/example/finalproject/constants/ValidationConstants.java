package org.example.finalproject.constants;

public class ValidationConstants {

    public static final int MIN = 1;
    public static final int PASSWORD_MIN_LENGTH = 4;
    public static final int PASSWORD_MAX_LENGTH = 60;
    public static final int EMAIL_MAX_LENGTH = 255;
    public static final int USERNAME_MAX_LENGTH = 255;
    public static final String EMAIL_PATTERN = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String PASSWORD_PATTERN = "[\\w]+";
    public static final String USERNAME_PATTERN = "[a-zA-Z]+";

    public static final int TICKER_SYMBOL_MAX_LENGTH = 30;

    public static final String START_DATE_GREATER_THAN_END_DATE = "start date greater than end date";

}
