package pe.a3ya.msauth.domain.ports.out;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTServiceOut {
    String extractUsername(String token);
    String generateToken(UserDetails userDetails);
    boolean validateToken(String token, UserDetails userDetails);
    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
}
