package pe.a3ya.mscustomers.domain.aggregates.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse {
    private String email;
    private Long id;
    private boolean state;
}
