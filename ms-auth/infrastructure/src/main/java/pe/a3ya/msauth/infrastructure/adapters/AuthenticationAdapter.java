package pe.a3ya.msauth.infrastructure.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import pe.a3ya.msauth.domain.aggregates.requests.SignInRequest;
import pe.a3ya.msauth.domain.aggregates.response.AuthenticationResponse;
import pe.a3ya.msauth.domain.ports.out.AuthenticationServiceOut;
import pe.a3ya.msauth.domain.ports.out.JWTServiceOut;
import pe.a3ya.msauth.infrastructure.dao.RolRepository;
import pe.a3ya.msauth.infrastructure.dao.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationAdapter implements AuthenticationServiceOut {

    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    private final JWTServiceOut jwtServiceOut;
    private final AuthenticationManager authenticationManager;




    @Override
    public AuthenticationResponse signin(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getEmail(),signInRequest.getPassword()));
        var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("Invalid email"));

        var jwt = jwtServiceOut.generateToken(user);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(jwt);
        return authenticationResponse;
    }

}
