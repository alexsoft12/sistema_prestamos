package pe.a3ya.msloans.infrastructure.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.a3ya.msloans.domain.aggregates.dto.LoanDto;
import pe.a3ya.msloans.domain.aggregates.requests.LoanRequest;
import pe.a3ya.msloans.domain.ports.out.LoanServiceOut;
import pe.a3ya.msloans.infrastructure.dao.LoanRepository;
import pe.a3ya.msloans.infrastructure.entities.LoanEntity;
import pe.a3ya.msloans.infrastructure.mappers.LoanMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanAdapter implements LoanServiceOut {

    final LoanRepository loanRepository;

    @Override
    public LoanDto save(LoanRequest loanRequest) {
        LoanEntity loanEntity = new LoanEntity();
        return updateLoan(loanRequest, loanEntity);

    }

    @Override
    public LoanDto getById(Long id) {
        LoanEntity loanEntity = loanRepository.findById(id).orElse(null);
        if (loanEntity == null) {
            return null;
        }
        return LoanMapper.fromEntityToDto(loanEntity);
    }

    @Override
    public List<LoanDto> getAll() {
        return LoanMapper.fromEntityToDtoList(loanRepository.findAll());
    }

    @Override
    public LoanDto update(Long id, LoanRequest loanRequest) {
        LoanEntity loanEntity = loanRepository.findById(id).orElse(null);
        if (loanEntity == null) {
            return null;
        }
        loanEntity.setId(loanRequest.getId());
        return updateLoan(loanRequest, loanEntity);
    }

    private LoanDto updateLoan(LoanRequest loanRequest, LoanEntity loanEntity) {
        loanEntity.setCustomerId(loanRequest.getCustomerId());
        loanEntity.setAmount(loanRequest.getAmount());
        loanEntity.setPaymentMethod(loanRequest.getPaymentMethod());
        loanEntity.setPaymentType(loanRequest.getPaymentType());
        loanEntity.setContractDate(loanRequest.getContractDate());
        loanEntity.setStartDate(loanRequest.getStartDate());
        loanEntity.setEndDate(loanRequest.getEndDate());
        loanEntity.setInterestRate(loanRequest.getInterestRate());
        loanEntity.setStatus(loanRequest.getStatus());
        loanEntity.setTerm(loanRequest.getTerm());
        loanEntity.setFee(loanRequest.getFee());
        return LoanMapper.fromEntityToDto(loanRepository.save(loanEntity));
    }

    @Override
    public void delete(Long id) {
        LoanEntity loanEntity = loanRepository.findById(id).orElse(null);
        if (loanEntity != null) {
            loanEntity.onDeleted();
            loanRepository.delete(loanEntity);
        }
    }
}
