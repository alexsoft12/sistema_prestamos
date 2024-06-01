package pe.a3ya.mspayments.domain.aggregates.request;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class PayRequest {

    private Long installments_id;
    private String modality;
    private String method;
    private double amount;
}
