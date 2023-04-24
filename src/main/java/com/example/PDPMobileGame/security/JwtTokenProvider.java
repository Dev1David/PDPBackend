package com.example.PDPMobileGame.security;

import com.example.PDPMobileGame.error_response.TokenValidationResponse;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;

@Component
public class JwtTokenProvider {
    @Value("${SECRET_KEY}")
    private String SECRET_KEY;
    private static final long EXPIRATION_TIME = 2_592_000_000L;

    public String createToken() {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
        return Jwts.builder()
                //.claim("id", id.toString()) // додавання id в тіло токену
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // валідація та отримання id з токену
    public Long getUserIdFromToken(String token) {
        return Long.parseLong(Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getId());
    }

    public TokenValidationResponse isTokenExpired (String token) {
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            Claims claims = Jwts
                .parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

            Date expDate = claims.getExpiration();
            Date currentDate = new Date();
            if (expDate.before(currentDate)) {
                return new TokenValidationResponse(false);
            } else {
                return new TokenValidationResponse(true);
            }
        } catch (ExpiredJwtException e) {
            return new TokenValidationResponse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token has expired"));
        } catch (MalformedJwtException e) {
            return new TokenValidationResponse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token"));
        } catch (Exception e) {
            return new TokenValidationResponse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No users found"));
        }
    }
}
