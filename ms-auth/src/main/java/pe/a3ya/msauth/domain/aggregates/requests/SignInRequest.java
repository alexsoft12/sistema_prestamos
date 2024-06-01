package pe.a3ya.msauth.domain.aggregates.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {

    @Valid
    @NotNull(message = "email is mandatory")
    @NotBlank(message = "email is mandatory")
    @Email(message = "incorrect email format")
    @Size(max = 100,message = "email must have a maximum of 100 chars")
    private String email;
    @NotNull(message = "password is mandatory")
    @NotBlank(message = "pasword is mandatory")
    @Size(min = 3, max = 100,message = "password must be between 3 and 100 chars")
    private String password;

}
