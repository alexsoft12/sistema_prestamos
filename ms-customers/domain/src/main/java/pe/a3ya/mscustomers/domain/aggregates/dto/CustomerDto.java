package pe.a3ya.mscustomers.domain.aggregates.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerDto {
    private Long id;
    private char documentType;
    private String documentNumber;
    private String name;
    private String lastName;
    private String motherLastName;
    private String email;
    private String phone;
    private String dateBirth;

}
