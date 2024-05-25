package pe.a3ya.mscustomers.domain.aggregates.request;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class CustomerRequest {
    private String documentNumber;
    private String email;
    private String phone;
    private Date birthDate;
}
