package pe.a3ya.mspayments.infrastructure.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pe.a3ya.mspayments.domain.aggregates.request.TokenRequest;

@FeignClient(name = "ms-auth", url = "http://localhost:8082/api/v1/authentication")
public interface SecurityClient {

    @PostMapping("/security")
    boolean getSecurityToken(
            @RequestBody TokenRequest token
    );
}
