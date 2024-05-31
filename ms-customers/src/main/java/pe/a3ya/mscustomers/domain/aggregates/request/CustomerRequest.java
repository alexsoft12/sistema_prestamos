package pe.a3ya.mscustomers.domain.aggregates.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CustomerRequest {
    @Valid
    @NotNull(message = "Document Number is mandatory")
    @NotBlank(message = "Document Number is mandatory")
    private String documentNumber;
    @NotNull(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    private String email;
    @NotNull(message = "Phone is mandatory")
    @NotBlank(message = "Phone is mandatory")
    private String phone;
    @NotNull(message = "BirthDate is mandatory")
    @PastOrPresent(message = "BirthDate is mandatory")
    private LocalDate birthDate;
    @NotNull(message = "Address is mandatory")
    @NotEmpty(message = "Address is mandatory")
    @Valid
    private List<AddressRequest> addresses;
}
