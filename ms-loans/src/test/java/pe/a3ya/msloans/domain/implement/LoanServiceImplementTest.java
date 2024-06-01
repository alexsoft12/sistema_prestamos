package pe.a3ya.msloans.domain.implement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import pe.a3ya.msloans.domain.aggregates.dto.GuarantiesDto;
import pe.a3ya.msloans.domain.aggregates.dto.LoanDto;
import pe.a3ya.msloans.domain.aggregates.dto.PaymentInstallmentDto;
import pe.a3ya.msloans.domain.aggregates.requests.LoanRequest;
import pe.a3ya.msloans.domain.ports.out.LoanServiceOut;
import pe.a3ya.msloans.infrastructure.mappers.GuarantiesMapper;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceImplementTest {

    @Mock
    private LoanServiceOut loanServiceOut;

    @InjectMocks
    private LoanServiceImplement loanServiceImplement;
    private LoanRequest loanRequest;
    private LoanDto loanDto;

    @BeforeEach
    void setUp() {
        loanRequest = new LoanRequest();
        loanRequest.setAmount(1000.0);
        loanRequest.setPaymentMethod("monthly");
        loanRequest.setContractDate(LocalDate.parse("2021-01-01"));
        loanRequest.setInterestRate(0.1);
        loanRequest.setTerm(12);

        loanDto = LoanDto.builder()
                .id(1L)
                .amount(1000.0)
                .interestRate(0.1)
                .term(12)
                .build();
    }

    @Test
    void testCreateLoan() {
        when(loanServiceOut.save(any(LoanRequest.class))).thenReturn(loanDto);
        LoanDto result = loanServiceImplement.save(loanRequest);
        verify(loanServiceOut, times(1)).save(any(LoanRequest.class));
        assertNotNull(result);
        assertEquals(loanDto.getId(), result.getId());
        assertEquals(loanDto.getAmount(), result.getAmount());
    }

    @Test
    void testGetAllLoans(){
        List<LoanDto> loansDtoList = new ArrayList<>();
        loansDtoList.add(loanDto);
        when(loanServiceOut.getAll()).thenReturn(loansDtoList);
        List<LoanDto> result = loanServiceImplement.getAll();
        verify(loanServiceOut, times(1)).getAll();
        assertNotNull(result);
        assertEquals(loansDtoList.size(), result.size());
    }

    @Test
    void testGetLoanById(){
        when(loanServiceOut.getById(1L)).thenReturn(loanDto);
        LoanDto result = loanServiceImplement.getById(loanDto.getId());
        verify(loanServiceOut, times(1)).getById(1L);
        assertNotNull(result);
        assertEquals(loanDto.getId(), result.getId());
        assertEquals(loanDto.getAmount(), result.getAmount());

    }
    @Test
    void testUpdateLoan(){
        when(loanServiceOut.update(1L,loanRequest)).thenReturn(loanDto);
        LoanDto result = loanServiceImplement.update(1L,loanRequest);
        verify(loanServiceOut, times(1)).update(1L,loanRequest);
        assertNotNull(result);
        assertEquals(loanDto.getId(), result.getId());
        assertEquals(loanDto.getAmount(), result.getAmount());
    }
    @Test
    void testDeleteLoan(){
        doNothing().when(loanServiceOut).delete(1L);
        loanServiceImplement.delete(1L);
        verify(loanServiceOut, times(1)).delete(1L);
    }
}
