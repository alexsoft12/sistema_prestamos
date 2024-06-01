package pe.a3ya.msloans.infrastructure.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pe.a3ya.msloans.domain.aggregates.dto.GuarantiesDto;
import pe.a3ya.msloans.infrastructure.entities.GuarantiesEntity;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GuarantiesMapper {
    public static GuarantiesDto fromEntityToDto(GuarantiesEntity guarantiesEntity) {
        return GuarantiesDto.builder()
                .id(guarantiesEntity.getId())
                .name(guarantiesEntity.getName())
                .description(guarantiesEntity.getDescription())
                .estimatedValue(guarantiesEntity.getEstimatedValue())
                .status(guarantiesEntity.getStatus())
                .imageUrl(guarantiesEntity.getImageUrl())
                .build();
    }
    public static List<GuarantiesDto> fromEntityToDtoList(List<GuarantiesEntity> guarantiesEntities) {
        return guarantiesEntities.stream().map(GuarantiesMapper::fromEntityToDto).toList();
    }
}
