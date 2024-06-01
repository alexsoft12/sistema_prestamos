package pe.a3ya.mspayments.infrastructure.adapters;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pe.a3ya.mspayments.domain.aggregates.dto.PayDto;
import pe.a3ya.mspayments.domain.aggregates.request.PayRequest;
import pe.a3ya.mspayments.domain.aggregates.request.TokenRequest;
import pe.a3ya.mspayments.domain.ports.out.PayServiceOut;
import pe.a3ya.mspayments.infrastructure.clients.SecurityClient;
import pe.a3ya.mspayments.infrastructure.dao.PayRepository;
import pe.a3ya.mspayments.infrastructure.entities.PayEntity;
import pe.a3ya.mspayments.infrastructure.mapers.PayMapper;

import java.sql.Timestamp;
import java.util.List;
@Service
@RequiredArgsConstructor
public class PayAdapter implements PayServiceOut {
    private final PayRepository payRepository;
    private final SecurityClient securityClient;

    @Override
    public PayDto save(PayRequest payRequest) {
        validateSecurity();
        PayEntity payEntity = new PayEntity();
        PayEntity paySaved = getPayEntity(payRequest, payEntity);
        return PayMapper.fromEntityToDto(payRepository.save(paySaved));
    }

    @Override
    public PayDto getById(Long id) {
        validateSecurity();
        PayEntity payEntity = payRepository.findById(id).orElse(null);
        if (payEntity == null) {
            return null;
        }
        return PayMapper.fromEntityToDto(payEntity);
    }

    @Override
    public List<PayDto> getAll() {
        validateSecurity();
        return PayMapper.fromEntityToDtoList(payRepository.findAll());
    }

    @Override
    public PayDto update(Long id, PayRequest payRequest) {
        validateSecurity();
        PayEntity payEntity = payRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        getPayEntity(payRequest, payEntity);
        PayEntity updatePay = payRepository.save(payEntity);
        return PayMapper.fromEntityToDto(updatePay);
    }

    @Override
    public void delete(Long id) {
        validateSecurity();
        PayEntity payEntity = payRepository.findById(id).orElse(null);
        if (payEntity != null) {
            payEntity.onDeleted();
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

    private void validateSecurity() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        final String autHeader = request.getHeader("Authorization");
        final String jwt ;
        if(StringUtils.isEmpty(autHeader) || !StringUtils.startsWithIgnoreCase(autHeader, "Bearer ") ){
            throw new RuntimeException("REQUIRED LOGIN");
        }
        jwt = autHeader.substring(7);

        TokenRequest bodyToken = new TokenRequest();
        bodyToken.setToken(jwt);

        boolean res = securityClient.getSecurityToken(bodyToken);
        if(!res){
            throw new RuntimeException("REQUIRED LOGIN");
        }
    }
}
