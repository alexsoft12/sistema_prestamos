package pe.a3ya.msloans.domain.aggregates.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
public class LoanRequest {
    @Valid
    private Long id;
    private Long customerId;
    @NotNull(message = "amount cannot be null")
    @Digits(integer = 24, fraction = 6, message = "The double field must have up to 3 integer digits and 2 fractional digits")
    private Double amount;
    @NotNull(message = "paymentMethod is mandatory")
    @NotBlank(message = "paymentMethod is mandatory")
    @Size(max = 50,message = "paymentMethod must have a maximum of 50 chars")
    private String paymentMethod;
    @NotNull(message = "payment Type is mandatory")
    @NotBlank(message = "payment Type is mandatory")
    @Size(max = 50,message = "payment Type must have a maximum of 50 chars")
    private String paymentType;
    @NotNull(message = "contract Date is mandatory")
    private LocalDate contractDate;
    private LocalDate startDate;
    private LocalDate endDate;
    @NotNull(message = "interestRate cannot be null")
    @Digits(integer = 24, fraction = 6, message = "The double field must have up to 3 integer digits and 2 fractional digits")
    private Double interestRate;
    @NotNull(message = "status Type is mandatory")
    @NotBlank(message = "status Type is mandatory")
    @Size(max = 50,message = "status Type must have a maximum of 50 chars")
    private String status;
    @NotNull(message = "term Type is mandatory")
    @Min(value = 1, message = "term must be at least 1")
    @Max(value = 100, message = "term must be at most 100")
    private Integer term;
    private Double fee;
    private List<GuarantiesRequest> guaranties;
    private List<PaymentInstallmentRequest> paymentInstallments;

}
