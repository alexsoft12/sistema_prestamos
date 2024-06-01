package pe.a3ya.msauth.domain.ports.in;

import pe.a3ya.msauth.domain.aggregates.requests.SignInRequest;
import pe.a3ya.msauth.domain.aggregates.response.AuthenticationResponse;

public interface AuthenticationServiceIn {

    AuthenticationResponse signin(SignInRequest signInRequest);
}
