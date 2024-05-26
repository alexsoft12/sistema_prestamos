package pe.a3ya.msloans.infrastructure.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.a3ya.msloans.domain.aggregates.dto.LoanDto;
import pe.a3ya.msloans.domain.aggregates.requests.LoanRequest;
import pe.a3ya.msloans.domain.ports.out.LoanServiceOut;

@Service
@RequiredArgsConstructor
public class LoanAdapter implements LoanServiceOut {
    @Override
    public LoanDto save(LoanRequest loanRequest) {
        return null;
    }

    @Override
    public LoanDto getById(Long id) {
        return null;
    }

    @Override
    public LoanDto update(Long id, LoanRequest loanRequest) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
