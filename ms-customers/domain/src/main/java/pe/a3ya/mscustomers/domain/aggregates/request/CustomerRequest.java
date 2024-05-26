package pe.a3ya.mscustomers.domain.aggregates.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CustomerRequest {
    private String documentNumber;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private List<AddressRequest> addresses;
}
