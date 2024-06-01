package pe.a3ya.msloans.infrastructure.adapters;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pe.a3ya.msloans.domain.aggregates.requests.TokenRequest;
import pe.a3ya.msloans.infrastructure.clients.SecurityClient;

@Service
public class SecurityValidator {

    private final SecurityClient securityClient;

    public SecurityValidator(SecurityClient securityClient) {
        this.securityClient = securityClient;
    }

    public void validateSecurity() {
        final String authHeader = getAuthHeader();
        final String jwt;
        if (!StringUtils.hasLength(authHeader) || !StringUtils.startsWithIgnoreCase(authHeader, "Bearer ")) {
            throw new IllegalArgumentException("Token no valido");
        }
        jwt = authHeader.substring(7);

        TokenRequest bodyToken = new TokenRequest();
        bodyToken.setToken(jwt);

        boolean res = securityClient.getSecurityToken(bodyToken);
        if (!res) {
            throw new IllegalArgumentException("Token no valido");
        }
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
