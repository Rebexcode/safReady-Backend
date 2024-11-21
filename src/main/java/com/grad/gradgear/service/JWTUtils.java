package com.grad.gradgear.service;

import com.grad.gradgear.entity.OurUsers;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JWTUtils {

    // Define the secret key
    private final SecretKey secretKey;

    // Token expiration time in milliseconds (24 hours)
    private static final long EXPIRATION_TIME = 86400000; // 24 hours

    // Initialize the secret key in the constructor using HMAC-SHA256
    public JWTUtils() {
        String secret = "mySuperSecretKeyThatIsAtLeast32CharactersLong"; // This must be at least 256 bits
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // Generate token based on user details
    public String generateToken(UserDetails userDetails) {
        OurUsers ourUser = (OurUsers) userDetails; // Cast to get access to the role field
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", ourUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())) // Token issued time
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Token expiration time
                .signWith(secretKey, SignatureAlgorithm.HS256) // Sign with secret key using HMAC-SHA256
                .compact();
    }

    // Generate a refresh token (optional, similar to generateToken but with different expiration time)
    public String generateRefreshToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Custom expiration for refresh token
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract username (subject) from the token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract any specific claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey) // Provide the same key for validation
                .build()
                .parseClaimsJws(token) // Parse the signed JWT
                .getBody(); // Get the claims body
    }

    // Check if the token is valid (username and expiration)
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Check if the token has expired
    public boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
