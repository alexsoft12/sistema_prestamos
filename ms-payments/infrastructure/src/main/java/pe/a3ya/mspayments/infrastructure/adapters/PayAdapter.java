package pe.a3ya.mspayments.infrastructure.adapters;

import pe.a3ya.mspayments.domain.aggregates.dto.PayDto;
import pe.a3ya.mspayments.domain.aggregates.request.PayRequest;
import pe.a3ya.mspayments.domain.ports.out.PayServiceOut;

import java.util.List;

public class PayAdapter implements PayServiceOut {
    @Override
    public PayDto save(PayRequest payRequest) {
        return null;
    }

    @Override
    public PayDto findById(Long id) {
        return null;
    }

    @Override
    public List<PayDto> getAll() {
        return List.of();
    }

    @Override
    public PayDto update(Long id, PayRequest payRequest) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
