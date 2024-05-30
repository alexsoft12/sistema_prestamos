package pe.a3ya.mspayments.domain.aggregates.dto;


import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDate;


@Builder
@Getter
public class PayDto {
    private Long id;
    private Long installments_id;
    private LocalDate day;
    private String modality;
    private String method;
    private double amount;
    private Long createdBy;
    private Timestamp createdAt;
    private Long updatedBy;
    private Timestamp updatedAt;

}
