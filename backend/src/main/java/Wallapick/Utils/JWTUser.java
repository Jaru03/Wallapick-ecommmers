package Wallapick.Utils;

import Wallapick.Models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUser {

    private SecretKey key;

    public JWTUser(@Value("${jwt.secret}") String secret) {
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(User user) {

        return Jwts.builder()
                .claim("id", user.getId())
                .claim("username", user.getUsername())
                .claim("role", user.getRole())
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public User getUser(String token) throws Exception {

        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        User user = new User();
        user.setId(claims.get("id", Long.class));
        user.setUsername(claims.get("username", String.class));
        user.setRole(claims.get("role", String.class));

        return user;
    }

   // Returns the token's expiration date
    public Date getExpirationDate(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getExpiration();
    }
}