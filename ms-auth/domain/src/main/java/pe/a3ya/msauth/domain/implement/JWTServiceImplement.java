package pe.a3ya.msauth.domain.implement;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pe.a3ya.msauth.domain.ports.in.JWTServiceIn;
import pe.a3ya.msauth.domain.ports.out.JWTServiceOut;

import java.util.Map;

@Service
@AllArgsConstructor
public class JWTServiceImplement implements JWTServiceIn {

    private final JWTServiceOut jwtServiceOut;

    @Override
    public String extractUsername(String token) {
        return jwtServiceOut.extractUsername(token);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return jwtServiceOut.generateToken(userDetails);
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        return jwtServiceOut.validateToken(token,userDetails);
    }

    @Override
    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return jwtServiceOut.generateRefreshToken(extraClaims, userDetails);
    }
}
