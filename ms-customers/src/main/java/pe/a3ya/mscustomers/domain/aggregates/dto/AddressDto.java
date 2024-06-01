package pe.a3ya.mscustomers.domain.aggregates.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {
    private Long id;
    private String department;
    private String province;
    private String district;
    private String address;
    private String street;
    private String number;
    private String reference;
    private String postalCode;
    private String latitude;
    private String longitude;
}
