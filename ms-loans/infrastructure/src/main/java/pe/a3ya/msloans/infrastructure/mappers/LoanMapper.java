package pe.a3ya.msloans.infrastructure.mappers;

import pe.a3ya.msloans.domain.aggregates.dto.LoanDto;
import pe.a3ya.msloans.infrastructure.entities.LoanEntity;

import java.util.List;
import java.util.stream.Collectors;

public class LoanMapper {

    public static LoanDto fromEntityToDto(LoanEntity loan) {
        return LoanDto.builder()
                .id(loan.getId())
                .customerId(loan.getCustomerId())
                .amount(loan.getAmount())
                .paymentMethod(loan.getPaymentMethod())
                .paymentType(loan.getPaymentType())
                .contractDate(loan.getContractDate())
                .startDate(loan.getStartDate())
                .endDate(loan.getEndDate())
                .interestRate(loan.getInterestRate())
                .status(loan.getStatus())
                .term(loan.getTerm())
                .fee(loan.getFee())
                .createdBy(loan.getCreatedBy())
                .createdAt(loan.getCreatedAt())
                .updatedBy(loan.getUpdatedBy())
                .updatedAt(loan.getUpdatedAt())
                .build();
    }

    public static List<LoanDto> fromEntityToDtoList(List<LoanEntity> loans) {
        return loans.stream().map(LoanMapper::fromEntityToDto).collect(Collectors.toList());
    }
}
