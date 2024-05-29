package pe.a3ya.msloans.domain.aggregates.dto;

import lombok.Builder;
import lombok.Getter;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;


@Builder
@Getter
public class LoanDto {
    private Long id;
    private Long customerId;
    private Double amount;
    private String paymentMethod;
    private String paymentType;
    private LocalDate contractDate;
    private LocalDate  startDate;
    private LocalDate  endDate;
    private Double interestRate;
    private String status;
    private Integer term;
    private Double fee;
    private List<GuarantiesDto> guaranties;
    private Long createdBy;
    private Timestamp createdAt;
    private Long updatedBy;
    private Timestamp updatedAt;
}
