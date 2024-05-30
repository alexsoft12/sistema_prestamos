package pe.a3ya.msauth.domain.aggregates.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {
    private String email;
    private String password;

}
