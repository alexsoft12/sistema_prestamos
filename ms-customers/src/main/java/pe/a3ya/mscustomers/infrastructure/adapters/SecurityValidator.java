package pe.a3ya.mscustomers.infrastructure.adapters;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pe.a3ya.mscustomers.domain.aggregates.request.TokenRequest;
import pe.a3ya.mscustomers.domain.aggregates.response.TokenResponse;
import pe.a3ya.mscustomers.infrastructure.clients.SecurityClient;

@Service
@AllArgsConstructor
public class SecurityValidator {


    private final SecurityClient securityClient;




    public Long validateSecurity() {
        final String authHeader = getAuthHeader();
        final String jwt;
        if (!StringUtils.hasLength(authHeader) || !StringUtils.startsWithIgnoreCase(authHeader, "Bearer ")) {
            throw new IllegalArgumentException("Token no valido");
        }
        jwt = authHeader.substring(7);

        TokenRequest bodyToken = new TokenRequest();
        bodyToken.setToken(jwt);

        TokenResponse res = securityClient.getSecurityToken(bodyToken);
        if (!res.isState()) {
            throw new IllegalArgumentException("Token no valido");
        }

        return res.getId();
    }

    private static String getAuthHeader() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("No request attributes found. This method must be called in the context of an HTTP request.");
        }
        HttpServletRequest request = attributes.getRequest();
        return request.getHeader("Authorization");
    }
}
