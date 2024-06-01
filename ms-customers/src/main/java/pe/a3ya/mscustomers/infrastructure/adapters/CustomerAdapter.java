package pe.a3ya.mscustomers.infrastructure.adapters;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import pe.a3ya.mscustomers.domain.aggregates.constants.Constant;
import pe.a3ya.mscustomers.domain.aggregates.dto.CustomerDto;
import pe.a3ya.mscustomers.domain.aggregates.dto.ReniecDto;
import pe.a3ya.mscustomers.domain.aggregates.request.AddressRequest;
import pe.a3ya.mscustomers.domain.aggregates.request.CustomerRequest;
import pe.a3ya.mscustomers.domain.ports.out.CustomerServiceOut;
import pe.a3ya.mscustomers.infrastructure.Redis.RedisService;
import pe.a3ya.mscustomers.infrastructure.clients.ApisNetReniecClient;
import pe.a3ya.mscustomers.infrastructure.dao.AddressRepository;
import pe.a3ya.mscustomers.infrastructure.dao.CustomerRepository;
import pe.a3ya.mscustomers.infrastructure.entities.AddressEntity;
import pe.a3ya.mscustomers.infrastructure.entities.CustomerEntity;
import pe.a3ya.mscustomers.infrastructure.mappers.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.a3ya.mscustomers.infrastructure.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerAdapter implements CustomerServiceOut {

    private final CustomerRepository customerRepository;
    private final ApisNetReniecClient reniecClient;
    private final AddressRepository addressRepository;
    private final RedisService redisService;

    @Value("${token.apis_net}")
    private String token;

    private CustomerEntity getCustomerEntity(CustomerRequest customerRequest) {
        ReniecDto reniecDto = getExecReniec(customerRequest.getDocumentNumber());
        CustomerEntity customerEntity;
        customerEntity = new CustomerEntity();
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
    @Transactional
    public CustomerDto save(CustomerRequest customerRequest) {
        CustomerEntity customerEntity = getCustomerEntity(customerRequest);
        CustomerEntity savedCustomer = customerRepository.save(customerEntity);

        List<AddressEntity> addressEntities = customerRequest.getAddresses().stream().map(addressRequest -> {
            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setDepartment(addressRequest.getDepartment());
            addressEntity.setProvince(addressRequest.getProvince());
            addressEntity.setDistrict(addressRequest.getDistrict());
            addressEntity.setAddress(addressRequest.getAddress());
            addressEntity.setStreet(addressRequest.getStreet());
            addressEntity.setNumber(addressRequest.getNumber());
            addressEntity.setReference(addressRequest.getReference());
            addressEntity.setPostalCode(addressRequest.getPostalCode());
            addressEntity.setLatitude(addressRequest.getLatitude());
            addressEntity.setLongitude(addressRequest.getLongitude());
            addressEntity.setCustomer(savedCustomer);
            return addressEntity;
        }).collect(Collectors.toList());

        addressRepository.saveAll(addressEntities);
        savedCustomer.setAddresses(addressEntities);
        customerRepository.save(savedCustomer);
        return CustomerMapper.fromEntityToDto(savedCustomer);
    }

    @Override
    public Optional<CustomerDto> getById(Long id) {
        // Buscar la entidad en el repositorio
        CustomerEntity customerEntity = customerRepository.findById(id).orElse(null);

        // Intentar obtener la información desde Redis
        String redisInfo = redisService.getFromRedis(Constant.REDIS_KEY_GETCUSTOMER + id);

        if (redisInfo != null) {
            CustomerDto customerDto = Util.convertirDesdeString(redisInfo, CustomerDto.class);
            return Optional.ofNullable(customerDto);
        } else {
            if (customerEntity != null) {
                CustomerDto customerDto = CustomerMapper.fromEntityToDto(customerEntity);
                String dataForRedis = Util.convertirAString(customerDto);
                if (dataForRedis != null) {
                    redisService.saveInRedis(Constant.REDIS_KEY_GETCUSTOMER + id, dataForRedis, 1000);
                }
                return Optional.ofNullable(customerDto);
            }
        }

        return Optional.empty();
    }

    @Override
    public List<CustomerDto> getAll() {
        List<CustomerEntity> customerEntities = customerRepository.findAll();
        return CustomerMapper.fromEntityToDtoList(customerEntities);
    }

    @Override
    @Transactional
    public CustomerDto update(Long id, CustomerRequest customerRequest) {
        CustomerEntity customerEntity = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        updateCustomerEntity(customerEntity, customerRequest);

        CustomerEntity updatedCustomer = customerRepository.save(customerEntity);

        return CustomerMapper.fromEntityToDto(updatedCustomer);
    }

    private void updateCustomerEntity(CustomerEntity customerEntity, CustomerRequest customerRequest) {
        ReniecDto reniecDto = getExecReniec(customerRequest.getDocumentNumber());
        customerEntity.setDocumentType(reniecDto.getTipoDocumento());
        customerEntity.setDocumentNumber(reniecDto.getNumeroDocumento());
        customerEntity.setName(reniecDto.getNombres());
        customerEntity.setLastName(reniecDto.getApellidoPaterno());
        customerEntity.setMotherLastName(reniecDto.getApellidoMaterno());
        customerEntity.setEmail(customerRequest.getEmail());
        customerEntity.setPhone(customerRequest.getPhone());
        customerEntity.setDateBirth(customerRequest.getBirthDate());

        // Para las direcciones, puedes comparar y actualizar o agregar nuevas
        List<AddressEntity> updatedAddresses = new ArrayList<>();
        for (AddressRequest addressRequest : customerRequest.getAddresses()) {
            AddressEntity addressEntity = addressRequest.getId() != null
                    ? addressRepository.findById(addressRequest.getId()).orElseGet(AddressEntity::new)
                    : new AddressEntity();
            addressEntity.setCustomer(customerEntity);
            addressEntity.setDepartment(addressRequest.getDepartment());
            addressEntity.setProvince(addressRequest.getProvince());
            addressEntity.setDistrict(addressRequest.getDistrict());
            addressEntity.setAddress(addressRequest.getAddress());
            addressEntity.setStreet(addressRequest.getStreet());
            addressEntity.setNumber(addressRequest.getNumber());
            addressEntity.setReference(addressRequest.getReference());
            addressEntity.setPostalCode(addressRequest.getPostalCode());
            addressEntity.setLatitude(addressRequest.getLatitude());
            addressEntity.setLongitude(addressRequest.getLongitude());
            updatedAddresses.add(addressEntity);
        }
        customerEntity.setAddresses(updatedAddresses);
    }

    @Override
    public void delete(Long id) {
        CustomerEntity customer = customerRepository.findById(id).orElse(null);
        if (customer != null) {
            customer.onDeleted();
            for (AddressEntity address : customer.getAddresses()) {
                address.onDeleted();
            }
            customerRepository.delete(customer);
        }
    }
}
