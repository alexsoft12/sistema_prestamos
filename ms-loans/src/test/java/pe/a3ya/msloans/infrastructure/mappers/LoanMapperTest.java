package pe.a3ya.msloans.infrastructure.mappers;


import org.junit.jupiter.api.Test;
import pe.a3ya.msloans.domain.aggregates.dto.LoanDto;
import pe.a3ya.msloans.infrastructure.entities.GuarantiesEntity;
import pe.a3ya.msloans.infrastructure.entities.LoanEntity;
import pe.a3ya.msloans.infrastructure.entities.PaymentInstallmentEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoanMapperTest {

    @Test
    void testFromEntityToDto() {
        LoanEntity loanEntity = new LoanEntity();
        loanEntity.setId(1L);
        loanEntity.setCustomerId(Long.valueOf(1));
        loanEntity.setAmount(100.0);
        loanEntity.setPaymentMethod("monthly");
        loanEntity.setPaymentType("Efectivo");
        loanEntity.setContractDate(LocalDate.parse("2021-06-01"));
        loanEntity.setInterestRate(0.1);
        loanEntity.setStatus("Prestado");
        loanEntity.setTerm(5);
        List<GuarantiesEntity> guaranties = new ArrayList<>();
        GuarantiesEntity guarantiesEntity = new GuarantiesEntity();
        guarantiesEntity.setId(1L);
        guarantiesEntity.setName("cosa 1");
        guarantiesEntity.setDescription("cosa 1");
        guarantiesEntity.setEstimatedValue(BigDecimal.valueOf(34.56));
        guarantiesEntity.setStatus("1");
        guarantiesEntity.setImageUrl("imagen1");
        guaranties.add(guarantiesEntity);
        loanEntity.setGuaranties(guaranties);

        List<PaymentInstallmentEntity> paymentInstallment = new ArrayList<>();
        PaymentInstallmentEntity paymentInstallmentEntity = new PaymentInstallmentEntity();
        paymentInstallmentEntity.setId(1L);
        paymentInstallmentEntity.setAmount(160.0);
        paymentInstallmentEntity.setStartDate(LocalDate.parse("2021-06-01"));
        paymentInstallmentEntity.setEndDate(LocalDate.parse("2021-06-01"));
        paymentInstallmentEntity.setStatus("activo");
        paymentInstallment.add(paymentInstallmentEntity);
        loanEntity.setPaymentInstallments(paymentInstallment);

        LoanDto loanDto = LoanMapper.fromEntityToDto(loanEntity);
        assertNotNull(loanDto);
        assertEquals(loanDto.getId(), loanEntity.getId());
        assertEquals(loanDto.getCustomerId(), loanEntity.getCustomerId());
        assertEquals(loanDto.getAmount(), loanEntity.getAmount());
        assertEquals(loanDto.getPaymentMethod(), loanEntity.getPaymentMethod());
        assertEquals(loanDto.getPaymentType(), loanEntity.getPaymentType());
        assertEquals(loanDto.getContractDate(), loanEntity.getContractDate());
        assertEquals(loanDto.getInterestRate(), loanEntity.getInterestRate());
        assertEquals(loanDto.getStatus(), loanEntity.getStatus());
        assertEquals(loanDto.getTerm(), loanEntity.getTerm());
        assertEquals(loanDto.getGuaranties().size(), loanEntity.getGuaranties().size());
        assertEquals(loanDto.getPaymentInstallments().size(), loanEntity.getPaymentInstallments().size());

    }

    @Test
    void testFromDtoToEntity() {

        List<GuarantiesEntity> guaranties = new ArrayList<>();
        GuarantiesEntity guarantiesEntity = new GuarantiesEntity();
        guarantiesEntity.setId(1L);
        guarantiesEntity.setName("cosa 1");
        guarantiesEntity.setDescription("cosa 1");
        guarantiesEntity.setEstimatedValue(BigDecimal.valueOf(34.56));
        guarantiesEntity.setStatus("1");
        guarantiesEntity.setImageUrl("imagen1");
        guaranties.add(guarantiesEntity);

        List<PaymentInstallmentEntity> paymentInstallment = new ArrayList<>();
        PaymentInstallmentEntity paymentInstallmentEntity = new PaymentInstallmentEntity();
        paymentInstallmentEntity.setId(1L);
        paymentInstallmentEntity.setAmount(160.0);
        paymentInstallmentEntity.setStartDate(LocalDate.parse("2021-06-01"));
        paymentInstallmentEntity.setEndDate(LocalDate.parse("2021-06-01"));
        paymentInstallmentEntity.setStatus("activo");
        paymentInstallment.add(paymentInstallmentEntity);

        LoanEntity loanEntity1 = new LoanEntity();
        loanEntity1.setGuaranties(guaranties);
        loanEntity1.setPaymentInstallments(paymentInstallment);

        LoanEntity loanEntity2 = new LoanEntity();
        loanEntity2.setGuaranties(guaranties);
        loanEntity2.setPaymentInstallments(paymentInstallment);

        List<LoanEntity> loanEntities = Arrays.asList(loanEntity1, loanEntity2);
        List<LoanDto> loanDtos = LoanMapper.fromEntityToDtoList(loanEntities);

        assertNotNull(loanDtos);
        assertEquals(loanEntities.size(), loanDtos.size());

    }
}