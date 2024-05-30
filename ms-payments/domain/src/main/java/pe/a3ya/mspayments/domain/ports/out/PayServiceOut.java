package pe.a3ya.mspayments.domain.ports.out;

import pe.a3ya.mspayments.domain.aggregates.dto.PayDto;
import pe.a3ya.mspayments.domain.aggregates.request.PayRequest;

import java.util.List;

public interface PayServiceOut {
    PayDto save(PayRequest payRequest);

    PayDto findById(Long id);

    List<PayDto> getAll();

    PayDto update(Long id,PayRequest payRequest);

    void delete(Long id);
}
