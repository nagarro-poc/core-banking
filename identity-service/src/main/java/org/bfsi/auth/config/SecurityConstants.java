package org.bfsi.auth.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class SecurityConstants {
    // When Secret key is base 64 encoded
    public static final String JWT_SECRET = "UE9DIFNlY3JldCBLZXkgRm9yIEF1dGhlbnRpY2F0aW9u";

    // When Secret key is not base 64 encoded
    //public static final String JWT_SECRET = "POC Secret Key For Authentication";

    // Random secret key creation using Spring Security provided methods
    //public static final SecretKey JWT_SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static final String JWT_HEADER = "Authorization";
    public static final String JWT_REFRESH_TOKEN_HEADER = "Refresh-Token";
}
