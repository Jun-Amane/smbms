/**
 * Package: moe.zzy040330.smbms.service
 * File: JwtService.java
 * Author: Ziyu ZHOU
 * Date: 04/01/2025
 * Time: 14:33
 * Description: The JwtService class is responsible for handling JSON Web Tokens (JWTs)
 * within the application. It provides functionalities for generating, validating,
 * and parsing JWTs, as well as managing blacklisted tokens for enhanced security.
 */
package moe.zzy040330.smbms.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import moe.zzy040330.smbms.entity.SecurityUser;
import moe.zzy040330.smbms.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;


/**
 * Manages operations related to JWTs including token creation,
 * validation, and role extraction. It provides security features
 * such as token blacklisting and expiration handling.
 */
@Service
public class JwtService {
    @Value("YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFh")
    private String secretKey;

    @Value("2147483648")
    private long jwtExpiration;

    private final Set<String> blacklistedTokens = ConcurrentHashMap.newKeySet();

    /**
     * Extracts the username from a JWT token.
     *
     * @param token the JWT token
     * @return the username contained in the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, claims -> claims.get("userCode", String.class));
    }

    /**
     * Extracts the user ID from a JWT token.
     *
     * @param token the JWT token
     * @return the user ID contained in the token
     */
    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", Long.class));
    }

    /**
     * Extracts a specific claim from the JWT token.
     *
     * @param token          the JWT token
     * @param claimsResolver a function to extract the claim
     * @param <T>            the type of the claim
     * @return the extracted claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a JWT token for the given user details.
     *
     * @param userDetails the user details for which the token is generated
     * @return the generated JWT token
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token with extra claims.
     *
     * @param extraClaims extra claims to include in the token
     * @param userDetails the user details for which the token is generated
     * @return the generated JWT token
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        User user = ((SecurityUser) userDetails).user();
        Map<String, Object> claims = new HashMap<>(extraClaims);
        claims.put("userId", user.getId());
        claims.put("role", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        claims.put("userCode", user.getCode());
        claims.put("userName", user.getName());
        return buildToken(claims, userDetails, jwtExpiration);
    }

    /**
     * Retrieves the JWT expiration time in milliseconds.
     *
     * @return the expiration time of the JWT
     */
    public long getExpirationTime() {
        return jwtExpiration;
    }

    /**
     * Creates a JWT token.
     *
     * @param extraClaims extra claims to include in the token
     * @param userDetails the user details for which the token is created
     * @param expiration  the expiration time of the token
     * @return the created JWT token
     */
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    /**
     * Checks if the JWT token has expired.
     *
     * @param token the JWT token
     * @return true if the token has expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token the JWT token
     * @return the expiration date of the token
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts roles from the JWT token.
     *
     * @param token the JWT token
     * @return a list of roles contained in the token
     */
    public List<String> extractRoles(String token) {
        return extractClaim(token, claims -> {
            @SuppressWarnings("unchecked")
            List<String> roles = (List<String>) claims.get("role");
            return roles;
        });
    }

    /**
     * Parses and validates all claims from the JWT token.
     *
     * @param token the JWT token
     * @return the claims contained in the token
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Generates the secret key for signing JWTs.
     *
     * @return the secret key
     */
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Invalidates a JWT token by adding it to the blacklist.
     *
     * @param token the JWT token to invalidate
     */
    public void invalidateToken(String token) {
        blacklistedTokens.add(token);
    }

    /**
     * Validates a JWT token against user details.
     *
     * @param token       the JWT token
     * @param userDetails the user details to validate against
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        if (blacklistedTokens.contains(token)) {
            return false;
        }
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Periodically cleans up expired tokens from the blacklist.
     */
    @Scheduled(fixedRate = 3600000)
    public void cleanupBlacklist() {
        blacklistedTokens.removeIf(this::isTokenExpired);
    }
}