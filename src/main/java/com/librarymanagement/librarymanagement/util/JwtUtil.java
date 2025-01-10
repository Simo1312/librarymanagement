package com.librarymanagement.librarymanagement.util;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {


    @Value("${app.jwt.secret:MY_DEFAULT_SECRET_KEY}")
    private String secretKey;

    // 10 hours expiration (in milliseconds)
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;


    public String generateToken(String username, String email, String role) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withSubject(username)
                .withClaim("role", role)
                .withClaim("email", email)  // Add email
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }


    private DecodedJWT verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }


    public String extractUsername(String token) {
        return verifyToken(token).getSubject();
    }


    public String extractRole(String token) {
        return verifyToken(token).getClaim("role").asString();
    }


    public boolean isTokenValid(String token, UserDetails userDetails) {
        String usernameInToken = extractUsername(token);
        return (usernameInToken.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expiration = verifyToken(token).getExpiresAt();
        return expiration.before(new Date());
    }

    private DecodedJWT decodeToken(String token) {
        return JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token);
    }
    public <T> T extractClaim(String token, Function<DecodedJWT, T> claimsResolver) {
        DecodedJWT decodedJWT = decodeToken(token);
        return claimsResolver.apply(decodedJWT);
    }
    public String extractEmail(String token) {
        return extractClaim(token, jwt -> jwt.getClaim("email").asString());
    }
}
