package pe.a3ya.mspayments.infrastructure.mapers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import pe.a3ya.mspayments.domain.aggregates.dto.PayDto;
import pe.a3ya.mspayments.infrastructure.entities.PayEntity;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PayMapper {
    public static PayDto fromEntityToDto(PayEntity payEntity) {
        return PayDto.builder()
                .id(payEntity.getId())
                .installmentsId(payEntity.getInstallmentsId())
                .day(payEntity.getDay())
                .modality(payEntity.getModality())
                .method(payEntity.getMethod())
                .amount(payEntity.getAmount())
                .createdBy(payEntity.getCreatedBy())
                .createdAt(payEntity.getCreatedAt())
                .updatedBy(payEntity.getUpdatedBy())
                .updatedAt(payEntity.getUpdatedAt())
                .build();
    }
    public static List<PayDto> fromEntityToDtoList(List<PayEntity> pays) {
        return pays.stream().map(PayMapper::fromEntityToDto).toList();
    }

}
