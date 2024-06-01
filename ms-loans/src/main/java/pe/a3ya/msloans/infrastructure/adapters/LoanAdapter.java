package pe.a3ya.msloans.infrastructure.adapters;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pe.a3ya.msloans.domain.aggregates.dto.LoanDto;
import pe.a3ya.msloans.domain.aggregates.requests.LoanRequest;
import pe.a3ya.msloans.domain.aggregates.requests.PaymentInstallmentRequest;
import pe.a3ya.msloans.domain.aggregates.requests.TokenRequest;
import pe.a3ya.msloans.domain.ports.out.LoanServiceOut;
import pe.a3ya.msloans.infrastructure.clients.SecurityClient;
import pe.a3ya.msloans.infrastructure.dao.GuarantiesRepository;
import pe.a3ya.msloans.infrastructure.dao.LoanRepository;
import pe.a3ya.msloans.infrastructure.entities.GuarantiesEntity;
import pe.a3ya.msloans.infrastructure.dao.PaymentInstallmentRepository;
import pe.a3ya.msloans.infrastructure.entities.LoanEntity;
import pe.a3ya.msloans.infrastructure.entities.PaymentInstallmentEntity;
import pe.a3ya.msloans.infrastructure.mappers.LoanMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanAdapter implements LoanServiceOut {

    final LoanRepository loanRepository;
    final GuarantiesRepository guarantiesRepository;
    final PaymentInstallmentRepository installmentRepository;
    final SecurityClient securityClient;
    private final SecurityValidator securityValidator;

    @Override
    @Transactional
    public LoanDto save(LoanRequest loanRequest) {
        Long userId = securityValidator.validateSecurity();
        LoanEntity loanEntity = new LoanEntity();
        loanEntity.setCreatedBy(userId);
        LoanEntity loanSaved = updateLoan(loanRequest, loanEntity);

        List<PaymentInstallmentEntity> paymentInstallmentEntities = loanRequest.getPaymentInstallments().stream().map(paymentInstallmentRequest -> {
            PaymentInstallmentEntity paymentInstallmentEntity = new PaymentInstallmentEntity();
            paymentInstallmentEntity.setAmount(paymentInstallmentRequest.getAmount());
            paymentInstallmentEntity.setStartDate(paymentInstallmentRequest.getStartDate());
            paymentInstallmentEntity.setEndDate(paymentInstallmentRequest.getEndDate());
            paymentInstallmentEntity.setStatus(paymentInstallmentRequest.getStatus());
            paymentInstallmentEntity.setCreatedBy(userId);
            paymentInstallmentEntity.setLoan(loanSaved);
            return paymentInstallmentEntity;
        }).collect(Collectors.toList());

        installmentRepository.saveAll(paymentInstallmentEntities);
        loanSaved.setPaymentInstallments(paymentInstallmentEntities);
        loanRepository.save(loanSaved);
        return LoanMapper.fromEntityToDto(loanSaved);
    }

    @Override
    public LoanDto getById(Long id) {
        securityValidator.validateSecurity();
        LoanEntity loanEntity = loanRepository.findById(id).orElse(null);
        if (loanEntity == null) {
            return null;
        }
        return LoanMapper.fromEntityToDto(loanEntity);
    }

    @Override
    public List<LoanDto> getAll() {
        securityValidator.validateSecurity();
        return LoanMapper.fromEntityToDtoList(loanRepository.findAll());
    }

    @Override
    public LoanDto update(Long id, LoanRequest loanRequest) {
        Long userId = securityValidator.validateSecurity();
        LoanEntity loanEntity = loanRepository.findById(id).orElse(null);
        if (loanEntity == null) {
            return null;
        }
        loanEntity.setUpdatedBy(userId);
        loanEntity.setId(id);
        LoanEntity loanUpdated = updateLoan(loanRequest, loanEntity);

        List<PaymentInstallmentEntity> installments = new ArrayList<>();
        for (int i = 0; i < loanRequest.getTerm(); i++) {
            PaymentInstallmentEntity paymentInstallmentEntity = getInstallment(loanRequest, i, loanUpdated);
            paymentInstallmentEntity.setCreatedBy(userId);
            installments.add(paymentInstallmentEntity);
        }
        // eliminar las cuotas que no se necesitan
        if (loanRequest.getTerm() < loanUpdated.getPaymentInstallments().size()) {
            for (int i = loanRequest.getTerm(); i < loanUpdated.getPaymentInstallments().size(); i++) {
                PaymentInstallmentEntity installment = loanUpdated.getPaymentInstallments().get(i);
                installment.setDeletedBy(userId);
                installment.onDeleted();
                installmentRepository.delete(installment);
            }
        }
        loanUpdated.setPaymentInstallments(installments);
        loanRepository.save(loanUpdated);

        return LoanMapper.fromEntityToDto(loanUpdated);
    }


    private static PaymentInstallmentEntity getInstallment(LoanRequest loanRequest, int i, LoanEntity loanUpdated) {
        PaymentInstallmentRequest paymentInstallmentRequest = loanRequest.getPaymentInstallments().get(i);
        PaymentInstallmentEntity installment;
        if (loanRequest.getTerm() > loanUpdated.getPaymentInstallments().size()) {
            installment = new PaymentInstallmentEntity();
            installment.setCreatedBy(loanUpdated.getCreatedBy());
        } else {
            installment = loanUpdated.getPaymentInstallments().get(i);
            installment.setUpdatedBy(loanUpdated.getUpdatedBy());
        }

        installment.setAmount(paymentInstallmentRequest.getAmount());
        installment.setStartDate(paymentInstallmentRequest.getStartDate());
        installment.setEndDate(paymentInstallmentRequest.getEndDate());
        installment.setStatus(paymentInstallmentRequest.getStatus());
        installment.setLoan(loanUpdated);
        return installment;
    }

    private LoanEntity updateLoan(LoanRequest loanRequest, LoanEntity loanEntity) {
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
                guarantiesEntity.setName(guarantiesRequest.getName());
                guarantiesEntity.setDescription(guarantiesRequest.getDescription());
                guarantiesEntity.setEstimatedValue(guarantiesRequest.getEstimatedValue());
                guarantiesEntity.setStatus(guarantiesRequest.getStatus());
                guarantiesEntity.setImageUrl(guarantiesRequest.getImageUrl());
                guarantiesEntity.setCreatedBy(savedLoan.getCreatedBy());
                guarantiesEntity.setLoan(savedLoan);
                return guarantiesEntity;

            }).collect(Collectors.toList());

            guarantiesRepository.saveAll(guarantiesEntities);
            savedLoan.setGuaranties(guarantiesEntities);
        }else{
            //manejo de guarantis con lista vacia
            savedLoan.setGuaranties(new ArrayList<>());
        }

        return savedLoan;
    }

    @Override
    public void delete(Long id) {
        Long userId = securityValidator.validateSecurity();
        LoanEntity loanEntity = loanRepository.findById(id).orElse(null);
        if (loanEntity != null) {
            loanEntity.onDeleted();
            loanEntity.setDeletedBy(userId);
            loanRepository.delete(loanEntity);
        }
    }


}
