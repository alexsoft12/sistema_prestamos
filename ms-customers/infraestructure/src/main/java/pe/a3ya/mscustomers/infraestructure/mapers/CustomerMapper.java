package pe.a3ya.mscustomers.infraestructure.mapers;

import pe.a3ya.mscustomers.domain.aggregates.dto.CustomerDto;
import pe.a3ya.mscustomers.infraestructure.entities.CustomerEntity;

import java.util.List;
import java.util.stream.Collectors;

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
                .dateBirth(customerEntity.getDateBirth())
                .build();
    }

    public static List<CustomerDto> fromEntityToDtoList(List<CustomerEntity> customerEntities) {
        return customerEntities.stream().map(CustomerMapper::fromEntityToDto).collect(Collectors.toList());
    }
}
