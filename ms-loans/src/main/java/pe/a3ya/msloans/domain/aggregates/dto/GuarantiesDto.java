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
    private BigDecimal estimatedValue;
    private String status;
    private String imageUrl;
}
