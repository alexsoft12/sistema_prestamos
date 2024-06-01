package pe.a3ya.msauth.domain.ports.out;

import pe.a3ya.msauth.domain.aggregates.requests.SignInRequest;
import pe.a3ya.msauth.domain.aggregates.response.AuthenticationResponse;

public interface AuthenticationServiceOut {

    AuthenticationResponse signin(SignInRequest signInRequest);
}
