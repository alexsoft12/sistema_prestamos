package pe.a3ya.msloans.infrastructure.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pe.a3ya.msloans.domain.aggregates.dto.PaymentInstallmentDto;
import pe.a3ya.msloans.infrastructure.entities.PaymentInstallmentEntity;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentInstallmentMapper {
    public static PaymentInstallmentDto fromEntityToDto(PaymentInstallmentEntity paymentInstallment) {
        return PaymentInstallmentDto.builder()
                .id(paymentInstallment.getId())
                .amount(paymentInstallment.getAmount())
                .startDate(paymentInstallment.getStartDate())
                .endDate(paymentInstallment.getEndDate())
                .status(paymentInstallment.getStatus())
                .build();
    }
    public static List<PaymentInstallmentDto> fromEntityToDtoList(List<PaymentInstallmentEntity> paymentInstallmentEntities) {
        return paymentInstallmentEntities.stream().map(PaymentInstallmentMapper::fromEntityToDto).toList();
    }
}
