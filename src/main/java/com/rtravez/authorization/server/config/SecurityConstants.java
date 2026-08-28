package com.rtravez.authorization.server.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityConstants {
    public static final String CLIENT_ID = "rtravez-web";
    public static final String CLIENT_SECRET = "$2a$10$itMT/hPoLNo/FqNuzh69LeNMMUsca/j2WIdOn7P0ZYhjs6rKK/way";
    public static final String ISSUER = "http://localhost:8080/authorizationServer";
}
