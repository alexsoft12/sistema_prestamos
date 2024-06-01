package pe.a3ya.mspayments.infrastructure.adapters;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.a3ya.mspayments.domain.aggregates.dto.PayDto;
import pe.a3ya.mspayments.domain.aggregates.request.PayRequest;
import pe.a3ya.mspayments.domain.ports.out.PayServiceOut;
import pe.a3ya.mspayments.infrastructure.dao.PayRepository;
import pe.a3ya.mspayments.infrastructure.entities.PayEntity;
import pe.a3ya.mspayments.infrastructure.mapers.PayMapper;

import java.sql.Timestamp;
import java.util.List;
@Service
@RequiredArgsConstructor
public class PayAdapter implements PayServiceOut {
    private final PayRepository payRepository;
    private final SecurityValidator securityValidator;

    @Override
    public PayDto save(PayRequest payRequest) {
        Long userId = securityValidator.validateSecurity();
        PayEntity payEntity = new PayEntity();
        PayEntity paySaved = getPayEntity(payRequest, payEntity);
        paySaved.setCreatedBy(userId);
        return PayMapper.fromEntityToDto(payRepository.save(paySaved));
    }

    @Override
    public PayDto getById(Long id) {
        securityValidator.validateSecurity();
        PayEntity payEntity = payRepository.findById(id).orElse(null);
        if (payEntity == null) {
            return null;
        }
        return PayMapper.fromEntityToDto(payEntity);
    }

    @Override
    public List<PayDto> getAll() {
        securityValidator.validateSecurity();
        return PayMapper.fromEntityToDtoList(payRepository.findAll());
    }

    @Override
    public PayDto update(Long id, PayRequest payRequest) {
        Long userId = securityValidator.validateSecurity();
        PayEntity payEntity = payRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        getPayEntity(payRequest, payEntity);
        payEntity.setUpdatedBy(userId);
        PayEntity updatePay = payRepository.save(payEntity);
        return PayMapper.fromEntityToDto(updatePay);
    }

    @Override
    public void delete(Long id) {
        Long userId = securityValidator.validateSecurity();
        PayEntity payEntity = payRepository.findById(id).orElse(null);
        if (payEntity != null) {
            payEntity.onDeleted();
            payEntity.setDeletedBy(userId);
            payRepository.save(payEntity);
        }
    }

    private PayEntity getPayEntity(PayRequest payRequest, PayEntity payEntity) {
        payEntity.setInstallmentsId(payRequest.getInstallmentsId());
        payEntity.setDay(getTimestamp());
        payEntity.setModality(payRequest.getModality());
        payEntity.setMethod(payRequest.getMethod());
        payEntity.setAmount(payRequest.getAmount());
        return payEntity;
    }
    private Timestamp getTimestamp(){
        long currenTIme = System.currentTimeMillis();
        return new Timestamp(currenTIme);
    }

}
