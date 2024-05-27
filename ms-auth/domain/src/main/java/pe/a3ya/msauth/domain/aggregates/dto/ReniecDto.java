package pe.a3ya.msauth.domain.aggregates.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ReniecDto {
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private char tipoDocumento;
    private String numeroDocumento;
}
