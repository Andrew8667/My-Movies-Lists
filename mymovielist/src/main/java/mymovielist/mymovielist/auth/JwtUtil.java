package mymovielist.mymovielist.auth;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import mymovielist.mymovielist.entities.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Provides methods for:
 * -generating jwt token
 * -extracting the email associated with a token
 * -checking if a token corresponds to an email
 * @author Andrew Gee
 */
@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String SECRET; //secret used to generate the token form email

    /**
     * Generates a token given email of user
     * @param email of user
     * @return a jwt token
     */
    public String generateToken(String email){
        return Jwts.builder()
        .setSubject(email)
        .signWith(SignatureAlgorithm.HS256, SECRET)
        .compact();
    }

    /**
     * Takes a jwt token and uses secret to return the email associated with it
     * @param token to extract email from
     * @return email associated with token
     */
    public String extractUsername(String token){
        return Jwts.parser()
        .setSigningKey(SECRET)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
    }

    /**
     * Checks if a token is equal to a
     * @param email being checked against token
     * @param token checked against email
     * @return true if email corresponds to token, false otherwise
     */
    public boolean validateToken(String email,String token){
        return extractUsername(token).equals(email);
    }
}
