package pe.a3ya.mscustomers.infrastructure.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pe.a3ya.mscustomers.domain.aggregates.dto.CustomerDto;
import pe.a3ya.mscustomers.infrastructure.entities.CustomerEntity;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerMapper {
    public static CustomerDto fromEntityToDto(CustomerEntity customerEntity) {
        return CustomerDto.builder()
                .id(customerEntity.getId())
                .documentType(customerEntity.getDocumentType())
                .documentNumber(customerEntity.getDocumentNumber())
                .name(customerEntity.getName())
                .lastName(customerEntity.getLastName())
                .motherLastName(customerEntity.getMotherLastName())
                .email(customerEntity.getEmail())
                .phone(customerEntity.getPhone())
                .dateBirth(customerEntity.getDateBirth().toString())
                .addresses(AddressMapper.fromEntityToDtoList(customerEntity.getAddresses()))
                .createdBy(customerEntity.getCreatedBy())
                .createdAt(customerEntity.getCreatedAt())
                .updatedBy(customerEntity.getUpdatedBy())
                .updatedAt(customerEntity.getUpdatedAt())
                .build();
    }

    public static List<CustomerDto> fromEntityToDtoList(List<CustomerEntity> customerEntities) {
        return customerEntities.stream().map(CustomerMapper::fromEntityToDto).toList();
    }
}
