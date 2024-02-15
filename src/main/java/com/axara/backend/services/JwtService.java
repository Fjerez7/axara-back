package com.axara.backend.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "5c3199f7ade13ed95b88076299ab70412b70c456b57cfc57225139ec0ac7ac40";

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    // Method that generates a JWT token with additional claims and user information.
    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
        // Use the JWT library to construct the token with the secret key and set the issue date, expiration date, and additional claims.
        return Jwts.builder().setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Method that extracts the username from the JWT token.
    public String getUserName(String token){
        return getClaim(token, Claims::getSubject);
    }

    // Generic method that extracts a specific claim from the JWT token
    public <T> T getClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Method that parses the JWT token and returns all claims contained in it
    private Claims getAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Method that converts the secret key into a cryptographic key that can be used to sign and verify tokens
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Method that validates a token
    public boolean validateToken(String token, UserDetails userDetails){
        final String username = getUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Method that checks if a token has expired.
    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    // Method that extracts the token expiration date
    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }
}
