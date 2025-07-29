package Wallapick.Utils;

import Wallapick.Modelos.Usuario;
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

    public String GenerateToken(Usuario u) {
        return Jwts.builder()
                .claim("id", u.getId())
                .claim("username", u.getUsername())
                .claim("role", u.getRole())
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public Usuario ObtenerUsuario(String token) throws Exception {
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        Usuario u = new Usuario();
        u.setId(claims.get("id", Long.class));
        u.setUsername(claims.get("username", String.class));
        u.setRole(claims.get("role", String.class));
        return u;
    }
}
