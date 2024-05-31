package pe.a3ya.mspayments.domain.ports.in;

import pe.a3ya.mspayments.domain.aggregates.dto.PayDto;
import pe.a3ya.mspayments.domain.aggregates.request.PayRequest;

import java.util.List;

public interface PayServiceIn{

    PayDto save(PayRequest payRequest);

    PayDto getById(Long id);

    List<PayDto> getAll();

    PayDto update(Long id,PayRequest payRequest);

    void delete(Long id);
}
