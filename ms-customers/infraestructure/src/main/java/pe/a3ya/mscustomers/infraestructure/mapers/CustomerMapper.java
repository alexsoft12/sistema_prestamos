package pe.a3ya.mscustomers.infraestructure.mapers;

import pe.a3ya.mscustomers.domain.aggregates.dto.CustomerDto;
import pe.a3ya.mscustomers.infraestructure.entities.CustomerEntity;

public class CustomerMapper {
    public static CustomerDto formEntityToDto(CustomerEntity customerEntity) {
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
}
