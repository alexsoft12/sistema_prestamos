package pe.a3ya.msloans.domain.ports.in;

import pe.a3ya.msloans.domain.aggregates.dto.LoanDto;
import pe.a3ya.msloans.domain.aggregates.requests.LoanRequest;

public interface LoanServiceIn {
    LoanDto save(LoanRequest loanRequest);

    LoanDto getById(Long id);

    LoanDto update(Long id, LoanRequest loanRequest);

    void delete(Long id);
}
