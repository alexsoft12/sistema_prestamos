package pe.a3ya.msloans.domain.aggregates.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class GuarantiesRequest {
    @Valid
    private Long id;
    @NotNull(message = "name is mandatory")
    @NotBlank(message = "name is mandatory")
    @Size(max = 100,message = "name must have a maximum of 100 chars")
    private String name;
    private String description;
    @NotNull(message = "estimated Value is mandatory")
    @NotBlank(message = "estimated Value is mandatory")
    private BigDecimal estimated_value;
    @NotNull(message = "status is mandatory")
    @NotBlank(message = "status is mandatory")
    @Size(max = 1,message = "status must have a maximum of 1 chars")
    private String status;
    private String image_url;
}
