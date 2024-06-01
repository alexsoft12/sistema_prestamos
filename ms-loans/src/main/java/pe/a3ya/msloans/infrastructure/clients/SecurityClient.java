package pe.a3ya.msloans.infrastructure.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pe.a3ya.msloans.domain.aggregates.requests.TokenRequest;

@FeignClient(name = "ms-auth", url = "http://localhost:8082/api/v1/authentication")
public interface SecurityClient {

    @PostMapping("/security")
    boolean getSecurityToken(
            @RequestBody TokenRequest token
    );
}
