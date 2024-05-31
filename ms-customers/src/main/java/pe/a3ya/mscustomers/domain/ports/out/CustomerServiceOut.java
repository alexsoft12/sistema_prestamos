package pe.a3ya.mscustomers.domain.ports.out;

import pe.a3ya.mscustomers.domain.aggregates.dto.CustomerDto;
import pe.a3ya.mscustomers.domain.aggregates.request.CustomerRequest;

import java.util.List;
import java.util.Optional;

public interface CustomerServiceOut {
    CustomerDto save(CustomerRequest customerRequest);
    Optional<CustomerDto> getById(Long id);
    List<CustomerDto> getAll();
    CustomerDto update(Long id, CustomerRequest customerRequest);
    void delete(Long id);
}
