package pe.a3ya.msauth.domain.ports.out;

import pe.a3ya.msauth.domain.aggregates.dto.UserDto;
import pe.a3ya.msauth.domain.aggregates.requests.SignInRequest;
import pe.a3ya.msauth.domain.aggregates.requests.SignUpRequest;
import pe.a3ya.msauth.domain.aggregates.response.AuthenticationResponse;

public interface AuthenticationServiceOut {
    UserDto signUpUser(SignUpRequest signUpRequest);
    UserDto signUpAdmin(SignUpRequest signUpRequest);
    AuthenticationResponse signin(SignInRequest signInRequest);
}
