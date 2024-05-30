package pe.a3ya.mspayments.domain.aggregates.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PayRequest {
    private Long id;
    private Long installments_id;
    private LocalDate day;
    private String modality;
    private String method;
    private double amount;
}
