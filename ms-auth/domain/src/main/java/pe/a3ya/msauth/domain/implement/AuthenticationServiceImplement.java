package pe.a3ya.msauth.domain.implement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pe.a3ya.msauth.domain.aggregates.dto.UserDto;
import pe.a3ya.msauth.domain.aggregates.requests.SignInRequest;
import pe.a3ya.msauth.domain.aggregates.requests.SignUpRequest;
import pe.a3ya.msauth.domain.aggregates.response.AuthenticationResponse;
import pe.a3ya.msauth.domain.ports.in.AuthenticationServiceIn;
import pe.a3ya.msauth.domain.ports.out.AuthenticationServiceOut;

@Service
@AllArgsConstructor
public class AuthenticationServiceImplement implements AuthenticationServiceIn {

    private final AuthenticationServiceOut authenticationServiceOut;

    @Override
    public UserDto signUpUser(SignUpRequest signUpRequest) {
        return authenticationServiceOut.signUpUser(signUpRequest);
    }

    @Override
    public UserDto signUpAdmin(SignUpRequest signUpRequest) {
        return authenticationServiceOut.signUpAdmin(signUpRequest);
    }

    @Override
    public AuthenticationResponse signin(SignInRequest signInRequest) {
        return authenticationServiceOut.signin(signInRequest);
    }
}
