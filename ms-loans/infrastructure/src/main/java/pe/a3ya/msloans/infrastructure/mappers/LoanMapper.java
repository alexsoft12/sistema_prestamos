package pe.a3ya.msloans.infrastructure.mappers;

import pe.a3ya.msloans.domain.aggregates.dto.LoanDto;
import pe.a3ya.msloans.infrastructure.entities.LoanEntity;

import java.util.List;
import java.util.stream.Collectors;

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
                .guaranties(GuarantiesMapper.fromEntityToDtoList(loanEntity.getGuaranties()))
                .createdBy(loanEntity.getCreatedBy())
                .createdAt(loanEntity.getCreatedAt())
                .updatedBy(loanEntity.getUpdatedBy())
                .updatedAt(loanEntity.getUpdatedAt())
                .build();
    }

    public static List<LoanDto> fromEntityToDtoList(List<LoanEntity> loans) {
        return loans.stream().map(LoanMapper::fromEntityToDto).collect(Collectors.toList());
    }
}
