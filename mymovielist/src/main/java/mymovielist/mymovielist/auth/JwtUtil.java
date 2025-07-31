package mymovielist.mymovielist.auth;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import mymovielist.mymovielist.entities.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String SECRET;
    public String generateToken(String email){
        return Jwts.builder()
        .setSubject(email)
        .signWith(SignatureAlgorithm.HS256, SECRET)
        .compact();
    }

    public String extractUsername(String token){
        return Jwts.parser()
        .setSigningKey(SECRET)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
    }

    public boolean validateToken(String username,String token){
        return extractUsername(token).equals(username);
    }
}
