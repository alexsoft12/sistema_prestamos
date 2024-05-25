package pe.a3ya.mscustomers.domain.aggregates.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {
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
