package pe.a3ya.msauth.domain.aggregates.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Long id;
    private char documentType;
    private String documentNumber;
    private String name;
    private String lastName;
    private String motherLastName;
    private String email;
    private String password;
    private String phone;
    private LocalDate dateBirth;
    private Long createdBy;
    private Timestamp createdAt;
    private Long updatedBy;
    private Timestamp updatedAt;
    private List<RolDto> roles;

    private String message;

}
