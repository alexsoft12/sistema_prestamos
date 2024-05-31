package pe.a3ya.msloans.domain.aggregates.dto;



import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@Builder

public class GuarantiesDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal estimated_value;
    private String status;
    private String image_url;
}
