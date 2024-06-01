package pe.a3ya.msloans.domain.aggregates.requests;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class PaymentInstallmentRequest {

    private Long id;
    private Long loanId;
    private Double amount;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}
