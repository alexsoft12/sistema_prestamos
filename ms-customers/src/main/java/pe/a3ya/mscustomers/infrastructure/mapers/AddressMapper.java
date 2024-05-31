package pe.a3ya.mscustomers.infrastructure.mapers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pe.a3ya.mscustomers.domain.aggregates.dto.AddressDto;
import pe.a3ya.mscustomers.infrastructure.entities.AddressEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressMapper {
    public static AddressDto fromEntityToDto(AddressEntity addressEntity) {
        return AddressDto.builder()
                .id(addressEntity.getId())
                .department(addressEntity.getDepartment())
                .province(addressEntity.getProvince())
                .district(addressEntity.getDistrict())
                .address(addressEntity.getAddress())
                .street(addressEntity.getStreet())
                .number(addressEntity.getNumber())
                .reference(addressEntity.getReference())
                .postalCode(addressEntity.getPostalCode())
                .latitude(addressEntity.getLatitude())
                .longitude(addressEntity.getLongitude())
                .build();
    }

    public static List<AddressDto> fromEntityToDtoList(List<AddressEntity> addressEntities) {
        return addressEntities.stream().map(AddressMapper::fromEntityToDto).toList();
    }
}
