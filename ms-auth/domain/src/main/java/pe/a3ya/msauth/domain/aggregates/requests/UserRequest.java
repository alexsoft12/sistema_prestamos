package pe.a3ya.msauth.domain.aggregates.requests;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class UserRequest {
    private String documentNumber;
    private String email;
    private String phone;
    private LocalDate birthDate;

}
