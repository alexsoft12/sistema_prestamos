package pe.a3ya.msloans.domain.implement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pe.a3ya.msloans.domain.aggregates.dto.LoanDto;
import pe.a3ya.msloans.domain.aggregates.requests.LoanRequest;
import pe.a3ya.msloans.domain.ports.in.LoanServiceIn;
import pe.a3ya.msloans.domain.ports.out.LoanServiceOut;

import java.util.List;

@Service
@AllArgsConstructor
public class LoanServiceImplement implements LoanServiceIn {
    private final LoanServiceOut loanServiceOut;


    @Override
    public LoanDto save(LoanRequest loanRequest) {
        return loanServiceOut.save(loanRequest);
    }

    @Override
    public LoanDto getById(Long id) {
        return loanServiceOut.getById(id);
    }

    @Override
    public List<LoanDto> getAll() {
        return loanServiceOut.getAll();
    }

    @Override
    public LoanDto update(Long id, LoanRequest loanRequest) {
        return loanServiceOut.update(id, loanRequest);
    }

    @Override
    public void delete(Long id) {
        loanServiceOut.delete(id);
    }
}
