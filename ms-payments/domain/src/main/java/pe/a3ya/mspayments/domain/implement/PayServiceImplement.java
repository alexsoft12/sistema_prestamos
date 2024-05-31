package pe.a3ya.mspayments.domain.implement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pe.a3ya.mspayments.domain.aggregates.dto.PayDto;
import pe.a3ya.mspayments.domain.aggregates.request.PayRequest;
import pe.a3ya.mspayments.domain.ports.in.PayServiceIn;
import pe.a3ya.mspayments.domain.ports.out.PayServiceOut;

import java.util.List;
@Service
@AllArgsConstructor
public class PayServiceImplement implements PayServiceIn {

    private final PayServiceOut payServiceOut;


    @Override
    public PayDto save(PayRequest payRequest) {
        return payServiceOut.save(payRequest);
    }

    @Override
    public PayDto getById(Long id) {
        return payServiceOut.getById(id);
    }

    @Override
    public List<PayDto> getAll() {
        return payServiceOut.getAll();
    }

    @Override
    public PayDto update(Long id, PayRequest payRequest) {
        return payServiceOut.update(id, payRequest);
    }

    @Override
    public void delete(Long id) {
        payServiceOut.delete(id);
    }
}
