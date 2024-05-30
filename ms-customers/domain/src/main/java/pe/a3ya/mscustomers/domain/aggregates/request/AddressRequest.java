package pe.a3ya.mscustomers.domain.aggregates.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {
    private Long id;
    @NotNull(message = "department is mandatory")
    @NotBlank(message = "department is mandatory")
    private String department;
    @NotNull(message = "province is mandatory")
    @NotBlank(message = "province is mandatory")
    private String province;
    @NotNull(message = "district is mandatory")
    @NotBlank(message = "district is mandatory")
    private String district;
    @NotNull(message = "address is mandatory")
    @NotBlank(message = "address Number is mandatory")
    private String address;
    @NotNull(message = "street is mandatory")
    @NotBlank(message = "street is mandatory")
    private String street;
    @NotNull(message = "number is mandatory")
    @NotBlank(message = "number is mandatory")
    private String number;
    @NotNull(message = "reference is mandatory")
    @NotBlank(message = "reference is mandatory")
    private String reference;
    @NotNull(message = "postalCode is mandatory")
    @NotBlank(message = "postalCode is mandatory")
    private String postalCode;
    @NotNull(message = "latitude is mandatory")
    @NotBlank(message = "latitude is mandatory")
    private String latitude;
    @NotNull(message = "longitude is mandatory")
    @NotBlank(message = "longitude is mandatory")
    private String longitude;
}
