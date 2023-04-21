package org.bfsi.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.bfsi.auth.config.SecurityConstants;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.*;

public class JWTUtils {
    private static long accessTokenExpiration = 600000;

    private static long refreshTokenExpiration = 1200000;

    public static Claims getClaims(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSecret())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
    public static String generateAccessToken(String subject, String username, String authorities) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, subject, accessTokenExpiration, username, authorities);
    }

    public static String generateRefreshToken(String subject, String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, subject, refreshTokenExpiration, username, null);
    }

    private static String createToken(Map<String, Object> claims, String subject, long expiration, String username, String authorities) {
        // Generate JWT Token :::
        String jwt = Jwts.builder()
                .setIssuer("JAVA POC").setSubject(subject)
                .claim("username", username)
                .claim("authorities", authorities)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expiration))
                .signWith(getSecret()).compact();

        return jwt;
    }
    public static String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> authoritiesSet = new HashSet<>();
        for(GrantedAuthority authority: authorities)
        {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }

    public static SecretKey getSecret() {
        byte[] keyBytes = Decoders.BASE64.decode(SecurityConstants.JWT_SECRET);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        // When Secret key is not base 64 encoded
        //SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        // Random secret key creation using Spring Security provided methods
        //SecretKey key = SecurityConstants.JWT_SECRET;

        return key;
    }
}
