package pe.a3ya.msauth.domain.aggregates.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import pe.a3ya.msauth.domain.aggregates.validations.ValidBirthday;

import java.time.LocalDate;


@Getter
@Setter
public class UserRequest {

    @Valid
    @NotNull(message = "documentNumber is mandatory")
    @NotBlank(message = "documentNumber is mandatory")
    @Size(max = 20,message = "documentNumber must have a maximum of 20 chars")
    private String documentNumber;
    @NotNull(message = "email is mandatory")
    @NotBlank(message = "email is mandatory")
    @Email(message = "incorrect email format")
    @Size(max = 100,message = "email must have a maximum of 100 chars")
    private String email;
    @NotNull(message = "password is mandatory")
    @NotBlank(message = "pasword is mandatory")
    @Size(min = 3, max = 100,message = "password must be between 3 and 100 chars")
    private String password;
    @NotNull(message = "phone is mandatory")
    @NotBlank(message = "phone is mandatory")
    @Size( max = 10, message = "phone must have a maximum of 10 chars")
    private String phone;
    @ValidBirthday
    private LocalDate birthDate;

}
