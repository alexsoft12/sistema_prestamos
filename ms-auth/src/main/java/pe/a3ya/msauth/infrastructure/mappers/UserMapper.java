package pe.a3ya.msauth.infrastructure.mappers;

import pe.a3ya.msauth.domain.aggregates.dto.UserDto;
import pe.a3ya.msauth.infrastructure.entities.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserDto fromEntityToDto(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .documentType(userEntity.getDocumentType())
                .documentNumber(userEntity.getDocumentNumber())
                .name(userEntity.getName())
                .lastName(userEntity.getLastName())
                .motherLastName(userEntity.getMotherLastName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .phone(userEntity.getPhone())
                .dateBirth(userEntity.getDateBirth())
                .createdBy(userEntity.getCreatedBy())
                .createdAt(userEntity.getCreatedAt())
                .updatedBy(userEntity.getUpdatedBy())
                .updatedAt(userEntity.getUpdatedAt())
                .build();
    }

    public static List<UserDto> fromEntityToDtoList(List<UserEntity> usersEntities) {
        return usersEntities.stream().map(UserMapper::fromEntityToDto).collect(Collectors.toList());
    }
}
