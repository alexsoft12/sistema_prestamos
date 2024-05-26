package pe.a3ya.msloans.domain.ports.in;

import pe.a3ya.msloans.domain.aggregates.dto.LoanDto;
import pe.a3ya.msloans.domain.aggregates.requests.LoanRequest;

import java.util.List;

public interface LoanServiceIn {
    LoanDto save(LoanRequest loanRequest);

    LoanDto getById(Long id);

    List<LoanDto> getAll();

    LoanDto update(Long id, LoanRequest loanRequest);

    void delete(Long id);
}
