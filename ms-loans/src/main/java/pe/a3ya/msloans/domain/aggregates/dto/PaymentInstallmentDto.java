package pe.a3ya.msloans.domain.aggregates.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class PaymentInstallmentDto {
    private Long id;
    private Double amount;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}
