package pe.a3ya.msloans.infrastructure.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pe.a3ya.msloans.domain.aggregates.dto.LoanDto;
import pe.a3ya.msloans.infrastructure.entities.LoanEntity;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoanMapper {

    public static LoanDto fromEntityToDto(LoanEntity loanEntity) {
        return LoanDto.builder()
                .id(loanEntity.getId())
                .customerId(loanEntity.getCustomerId())
                .amount(loanEntity.getAmount())
                .paymentMethod(loanEntity.getPaymentMethod())
                .paymentType(loanEntity.getPaymentType())
                .contractDate(loanEntity.getContractDate())
                .startDate(loanEntity.getStartDate())
                .endDate(loanEntity.getEndDate())
                .interestRate(loanEntity.getInterestRate())
                .status(loanEntity.getStatus())
                .term(loanEntity.getTerm())
                .fee(loanEntity.getFee())
                .createdBy(loanEntity.getCreatedBy())
                .createdAt(loanEntity.getCreatedAt())
                .updatedBy(loanEntity.getUpdatedBy())
                .updatedAt(loanEntity.getUpdatedAt())
                .guaranties(GuarantiesMapper.fromEntityToDtoList(loanEntity.getGuaranties()))
                .paymentInstallments(PaymentInstallmentMapper.fromEntityToDtoList(loanEntity.getPaymentInstallments()))
                .build();
    }

    public static List<LoanDto> fromEntityToDtoList(List<LoanEntity> loans) {
        return loans.stream().map(LoanMapper::fromEntityToDto).toList();
    }
}
