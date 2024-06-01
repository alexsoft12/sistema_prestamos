package pe.a3ya.mscustomers.infrastructure.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pe.a3ya.mscustomers.domain.aggregates.request.TokenRequest;
import pe.a3ya.mscustomers.domain.aggregates.response.TokenResponse;

@FeignClient(name = "ms-auth", url = "http://localhost:8082/api/v1/authentication")
public interface SecurityClient {


    @PostMapping("/security")
    TokenResponse getSecurityToken(
            @RequestBody TokenRequest token
    );
}
