package pe.a3ya.customers.infrastructure.mappers;

import org.junit.jupiter.api.Test;
import pe.a3ya.mscustomers.domain.aggregates.dto.AddressDto;
import pe.a3ya.mscustomers.infrastructure.entities.AddressEntity;
import pe.a3ya.mscustomers.infrastructure.mappers.AddressMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AddressMapperTest {

    @Test
    void testFromEntityToDto() {
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

        AddressDto addressDto = AddressMapper.fromEntityToDto(addressEntity);

        assertNotNull(addressDto);
        assertEquals(addressEntity.getId(), addressDto.getId());
        assertEquals(addressEntity.getDepartment(), addressDto.getDepartment());
        assertEquals(addressEntity.getProvince(), addressDto.getProvince());
        assertEquals(addressEntity.getDistrict(), addressDto.getDistrict());
        assertEquals(addressEntity.getAddress(), addressDto.getAddress());
        assertEquals(addressEntity.getStreet(), addressDto.getStreet());
        assertEquals(addressEntity.getNumber(), addressDto.getNumber());
        assertEquals(addressEntity.getReference(), addressDto.getReference());
        assertEquals(addressEntity.getPostalCode(), addressDto.getPostalCode());
        assertEquals(addressEntity.getLatitude(), addressDto.getLatitude());
        assertEquals(addressEntity.getLongitude(), addressDto.getLongitude());
    }

    @Test
    void testFromEntityToDtoList() {
        AddressEntity addressEntity1 = new AddressEntity();
        // Define addressEntity1 properties

        AddressEntity addressEntity2 = new AddressEntity();
        // Define addressEntity2 properties

        List<AddressEntity> addressEntities = Arrays.asList(addressEntity1, addressEntity2);

        List<AddressDto> addressDtos = AddressMapper.fromEntityToDtoList(addressEntities);

        assertNotNull(addressDtos);
        assertEquals(addressEntities.size(), addressDtos.size());

        // Optional: Add more assertions to verify individual elements in the list if needed
    }
}