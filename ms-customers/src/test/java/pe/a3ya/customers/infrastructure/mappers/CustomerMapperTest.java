package pe.a3ya.customers.infrastructure.mappers;

import org.junit.jupiter.api.Test;
import pe.a3ya.mscustomers.domain.aggregates.dto.CustomerDto;
import pe.a3ya.mscustomers.infrastructure.entities.AddressEntity;
import pe.a3ya.mscustomers.infrastructure.entities.CustomerEntity;
import pe.a3ya.mscustomers.infrastructure.mappers.CustomerMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomerMapperTest {
    @Test
    void testFromEntityToDto() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(1L);
        customerEntity.setDocumentType('1');
        customerEntity.setDocumentNumber("75583362");
        customerEntity.setName("FRANKLIN ANTONI");
        customerEntity.setLastName("ARELLANO");
        customerEntity.setMotherLastName("VILCHEZ");
        customerEntity.setEmail("frank@gmail.com");
        customerEntity.setPhone("935426586");
        customerEntity.setDateBirth(LocalDate.parse("1990-01-01"));
        List<AddressEntity> addresses = new ArrayList<>();
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(1L);
        addressEntity.setDepartment("Lima");
        addressEntity.setProvince("Lima");
        addressEntity.setDistrict("San Borja");
        addressEntity.setAddress("Av. Los Pinos 123");
        addressEntity.setStreet("Av. Los Pinos");
        addressEntity.setNumber("123");
        addressEntity.setReference("Frente a la comisaria");
        addressEntity.setPostalCode("15037");
        addressEntity.setLatitude("-12.0853");
        addressEntity.setLongitude("-77.0343");
        addresses.add(addressEntity);
        customerEntity.setAddresses(addresses);

        CustomerDto customerDto = CustomerMapper.fromEntityToDto(customerEntity);
        assertNotNull(customerDto);
        assertEquals(customerEntity.getId(), customerDto.getId());
        assertEquals(customerEntity.getDocumentType(), customerDto.getDocumentType());
        assertEquals(customerEntity.getDocumentNumber(), customerDto.getDocumentNumber());
        assertEquals(customerEntity.getName(), customerDto.getName());
        assertEquals(customerEntity.getLastName(), customerDto.getLastName());
        assertEquals(customerEntity.getMotherLastName(), customerDto.getMotherLastName());
        assertEquals(customerEntity.getEmail(), customerDto.getEmail());
        assertEquals(customerEntity.getPhone(), customerDto.getPhone());
        assertEquals(customerEntity.getDateBirth(), customerDto.getDateBirth());
        assertEquals(customerEntity.getAddresses().size(), customerDto.getAddresses().size());

    }

    @Test
    void testFromEntityToDtoList() {

        List<AddressEntity> addresses = new ArrayList<>();
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(1L);
        addressEntity.setDepartment("Lima");
        addressEntity.setProvince("Lima");
        addressEntity.setDistrict("San Borja");
        addressEntity.setAddress("Av. Los Pinos 123");
        addressEntity.setStreet("Av. Los Pinos");
        addressEntity.setNumber("123");
        addressEntity.setReference("Frente a la comisaria");
        addressEntity.setPostalCode("15037");
        addressEntity.setLatitude("-12.0853");
        addressEntity.setLongitude("-77.0343");
        addresses.add(addressEntity);

        CustomerEntity customerEntity1 = new CustomerEntity();

        customerEntity1.setAddresses(addresses);

        CustomerEntity customerEntity2 = new CustomerEntity();
        customerEntity2.setAddresses(addresses);

        List<CustomerEntity> customerEntities = Arrays.asList(customerEntity1, customerEntity2);

        List<CustomerDto> customerDtos = CustomerMapper.fromEntityToDtoList(customerEntities);

        assertNotNull(customerDtos);
        assertEquals(customerEntities.size(), customerDtos.size());
    }
}
