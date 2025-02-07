package org.example.finalproject.constants;

public class EndPointPaths {
    public static final String BASE_PATH = "/";
    public static final String API = "/api";
    public static final String STOCK = "/stock";
    public static final String API_STOCK = API + STOCK;
    public static final String USER = "/user";
    public static final String API_USER = API + USER;
    public static final String REGISTER = "/register";
    public static final String API_USER_REGISTER = API_USER + REGISTER;
    public static final String LOGIN = "/login";
    public static final String API_USER_LOGIN = API_USER + LOGIN;
    public static final String HISTORY = "/history";
    public static final String TICKER = "ticker";
    public static final String HISTORY_TICKER = HISTORY + "/{" + TICKER + "}";
    public static final String SAVE = "/save";
    public static final String SWAGGER_UI = "/swagger-ui/**";
    public static final String API_DOCS = "/api-docs/**";
}
