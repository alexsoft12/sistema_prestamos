package pe.a3ya.msloans.domain.aggregates.requests;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class GuarantiesRequest {
    //private Long id;
    private String name;
    private String description;
    private BigDecimal estimated_value;
    private String status;
    private String image_url;
}
