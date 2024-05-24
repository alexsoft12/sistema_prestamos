package pe.a3ya.mscustomers.domain.iplements;

import pe.a3ya.mscustomers.domain.aggregates.dto.CustomerDto;
import pe.a3ya.mscustomers.domain.aggregates.request.CustomerRequest;
import pe.a3ya.mscustomers.domain.ports.in.CustomerServiceIn;
import pe.a3ya.mscustomers.domain.ports.out.CustomerServiceOut;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImplement implements CustomerServiceIn {

    private final CustomerServiceOut customerServiceOut;


    @Override
    public CustomerDto save(CustomerRequest customerRequest) {
        return customerServiceOut.save(customerRequest);
    }

    @Override
    public Optional<CustomerDto> getById(Long id) {
        return customerServiceOut.getById(id);
    }

    @Override
    public List<CustomerDto> getAll() {
        return customerServiceOut.getAll();
    }

    @Override
    public CustomerDto update(Long id, CustomerRequest customerRequest) {
        return customerServiceOut.update(id, customerRequest);
    }

    @Override
    public void delete(Long id) {
        customerServiceOut.delete(id);
    }
}
