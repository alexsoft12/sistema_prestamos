package pe.a3ya.mscustomers.domain.aggregates.dto;


import lombok.*;

import java.sql.Timestamp;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private List<AddressDto> addresses;
    private Long createdBy;
    private Timestamp createdAt;
    private Long updatedBy;
    private Timestamp updatedAt;
}
