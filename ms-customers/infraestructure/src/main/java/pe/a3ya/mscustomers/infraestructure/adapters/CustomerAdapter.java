package pe.a3ya.mscustomers.infraestructure.adapters;

import pe.a3ya.mscustomers.domain.aggregates.dto.CustomerDto;
import pe.a3ya.mscustomers.domain.aggregates.request.CustomerRequest;
import pe.a3ya.mscustomers.domain.ports.out.CustomerServiceOut;
import pe.a3ya.mscustomers.infraestructure.dao.CustomerRepository;
import pe.a3ya.mscustomers.infraestructure.entities.CustomerEntity;
import pe.a3ya.mscustomers.infraestructure.mapers.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerAdapter implements CustomerServiceOut {

    private final CustomerRepository customerRepository;
    @Override
    public CustomerDto save(CustomerRequest customerRequest) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(customerRequest.getNumeroDocumento());
        return CustomerMapper.formEntityToDto(customerRepository.save(customerEntity));
    }

    @Override
    public Optional<CustomerDto> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<CustomerDto> getAll() {
        return List.of();
    }

    @Override
    public CustomerDto update(Long id, CustomerRequest customerRequest) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
