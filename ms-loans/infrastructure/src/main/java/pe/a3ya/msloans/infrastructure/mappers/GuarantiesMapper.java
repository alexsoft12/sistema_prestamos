package pe.a3ya.msloans.infrastructure.mappers;

import pe.a3ya.msloans.domain.aggregates.dto.GuarantiesDto;
import pe.a3ya.msloans.infrastructure.entities.GuarantiesEntity;

import java.util.List;
import java.util.stream.Collectors;

public class GuarantiesMapper {
    public static GuarantiesDto fromEntityToDto(GuarantiesEntity guarantiesEntity) {
        return GuarantiesDto.builder()
                .id(guarantiesEntity.getId())
                .name(guarantiesEntity.getName())
                .description(guarantiesEntity.getDescription())
                .estimated_value(guarantiesEntity.getEstimated_value())
                .status(guarantiesEntity.getStatus())
                .image_url(guarantiesEntity.getImage_url())
                .build();
    }
    public static List<GuarantiesDto> fromEntityToDtoList(List<GuarantiesEntity> guarantiesEntities) {
        return guarantiesEntities.stream().map(GuarantiesMapper::fromEntityToDto).collect(Collectors.toList());
    }
}
