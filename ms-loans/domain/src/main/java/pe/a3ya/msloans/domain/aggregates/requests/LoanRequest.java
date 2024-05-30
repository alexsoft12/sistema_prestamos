package pe.a3ya.msloans.domain.aggregates.requests;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
public class LoanRequest {
    private Long id;
    private Long customerId;
    private Double amount;
    private String paymentMethod;
    private String paymentType;
    private LocalDate contractDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double interestRate;
    private String status;
    private Integer term;
    private Double fee;
    private List<GuarantiesRequest> guaranties;
    private List<PaymentInstallmentRequest> paymentInstallments;

}
