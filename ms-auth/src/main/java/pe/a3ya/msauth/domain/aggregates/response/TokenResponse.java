package pe.a3ya.msauth.domain.aggregates.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse {
    private String email;
    private Long id;
    private boolean state;
}
