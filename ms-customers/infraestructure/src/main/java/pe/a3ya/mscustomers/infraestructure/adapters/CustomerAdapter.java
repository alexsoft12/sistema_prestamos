package pe.a3ya.mscustomers.infraestructure.adapters;

import org.springframework.beans.factory.annotation.Value;
import pe.a3ya.mscustomers.domain.aggregates.dto.CustomerDto;
import pe.a3ya.mscustomers.domain.aggregates.dto.ReniecDto;
import pe.a3ya.mscustomers.domain.aggregates.request.CustomerRequest;
import pe.a3ya.mscustomers.domain.ports.out.CustomerServiceOut;
import pe.a3ya.mscustomers.infraestructure.clients.ApisNetReniecClient;
import pe.a3ya.mscustomers.infraestructure.dao.CustomerRepository;
import pe.a3ya.mscustomers.infraestructure.entities.CustomerEntity;
import pe.a3ya.mscustomers.infraestructure.mapers.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerAdapter implements CustomerServiceOut {

    private final CustomerRepository customerRepository;
    private final ApisNetReniecClient reniecClient;

    @Value("${token.apis_net}")
    private String token;

    private CustomerEntity getCustomerEntity(CustomerRequest customerRequest, CustomerEntity customer) {
        ReniecDto reniecDto = getExecReniec(customerRequest.getDocumentNumber());
        CustomerEntity customerEntity;
        customerEntity = customer != null ? customer : new CustomerEntity();
        customerEntity.setDocumentType(reniecDto.getTipoDocumento());
        customerEntity.setDocumentNumber(reniecDto.getNumeroDocumento());
        customerEntity.setName(reniecDto.getNombres());
        customerEntity.setLastName(reniecDto.getApellidoPaterno());
        customerEntity.setMotherLastName(reniecDto.getApellidoMaterno());
        customerEntity.setEmail(customerRequest.getEmail());
        customerEntity.setPhone(customerRequest.getPhone());
        customerEntity.setDateBirth(customerRequest.getBirthDate());
        return customerEntity;
    }

    private ReniecDto getExecReniec(String numeroDocumento) {
        String authorization = "Bearer " + token;
        return reniecClient.getReniecInfo(numeroDocumento, authorization);
    }

    @Override
    public CustomerDto save(CustomerRequest customerRequest) {
        CustomerEntity customerEntity = getCustomerEntity(customerRequest, null);
        customerEntity.setCreatedBy(1L);
        customerEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return CustomerMapper.fromEntityToDto(customerRepository.save(customerEntity));
    }

    @Override
    public Optional<CustomerDto> getById(Long id) {
        CustomerEntity customerEntity = customerRepository.findById(id).orElse(null);
        if (customerEntity != null) {
            return Optional.ofNullable(CustomerMapper.fromEntityToDto(customerEntity));
        }
        return Optional.empty();
    }

    @Override
    public List<CustomerDto> getAll() {
       List<CustomerEntity> customerEntities = customerRepository.findAll();
         return CustomerMapper.fromEntityToDtoList(customerEntities);
    }

    @Override
    public CustomerDto update(Long id, CustomerRequest customerRequest) {
        CustomerEntity customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            CustomerEntity customerEntity = getCustomerEntity(customerRequest, customer);
            customerEntity.setUpdatedBy(1L);
            customerEntity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            return CustomerMapper.fromEntityToDto(customerRepository.save(customerEntity));
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        CustomerEntity customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            customer.setDeletedBy(1L);
            customer.setDeletedAt(new Timestamp(System.currentTimeMillis()));
            customerRepository.save(customer);
        }
    }
}
