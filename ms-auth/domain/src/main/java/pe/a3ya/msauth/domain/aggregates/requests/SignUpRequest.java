package pe.a3ya.msauth.domain.aggregates.requests;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String names;
    private String lastName;
    private String motherLastName;
    private String email;
    private String password;
}
