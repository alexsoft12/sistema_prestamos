package pe.a3ya.msloans.infrastructure.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.a3ya.msloans.domain.aggregates.dto.LoanDto;
import pe.a3ya.msloans.domain.aggregates.requests.LoanRequest;
import pe.a3ya.msloans.domain.ports.out.LoanServiceOut;
import pe.a3ya.msloans.infrastructure.dao.GuarantiesRepository;
import pe.a3ya.msloans.infrastructure.dao.LoanRepository;
import pe.a3ya.msloans.infrastructure.entities.GuarantiesEntity;
import pe.a3ya.msloans.infrastructure.entities.LoanEntity;
import pe.a3ya.msloans.infrastructure.mappers.LoanMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanAdapter implements LoanServiceOut {

    final LoanRepository loanRepository;
    final GuarantiesRepository guarantiesRepository;

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
        //aqui iria el if
        //if, si puede o no traer garantias
        LoanEntity savedLoan = loanRepository.save(loanEntity);
        if(loanRequest.getGuaranties() != null && !loanRequest.getGuaranties().isEmpty()) {
            List<GuarantiesEntity> guarantiesEntities = loanRequest.getGuaranties().stream().map(guarantiesRequest -> {
                GuarantiesEntity guarantiesEntity = new GuarantiesEntity();
                //guarantiesEntity.setId(guarantiesRequest.getId());
                guarantiesEntity.setName(guarantiesRequest.getName());
                guarantiesEntity.setDescription(guarantiesRequest.getDescription());
                guarantiesEntity.setEstimated_value(guarantiesRequest.getEstimated_value());
                guarantiesEntity.setStatus(guarantiesRequest.getStatus());
                guarantiesEntity.setImage_url(guarantiesRequest.getImage_url());
                guarantiesEntity.setLoan(savedLoan);
                return guarantiesEntity;

            }).collect(Collectors.toList());

            guarantiesRepository.saveAll(guarantiesEntities);
            savedLoan.setGuaranties(guarantiesEntities);
            loanRepository.save(savedLoan);
        }else{
            //manejo de guarantis con lista vacia
            savedLoan.setGuaranties(new ArrayList<>());
        }

        return LoanMapper.fromEntityToDto(savedLoan);
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
