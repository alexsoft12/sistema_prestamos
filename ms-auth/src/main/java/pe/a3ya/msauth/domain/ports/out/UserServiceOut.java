package pe.a3ya.msauth.domain.ports.out;

import org.springframework.security.core.userdetails.UserDetailsService;
import pe.a3ya.msauth.domain.aggregates.dto.UserDto;
import pe.a3ya.msauth.domain.aggregates.requests.UserRequest;

import java.util.List;
import java.util.Optional;

public interface UserServiceOut {
    UserDto save(UserRequest userRequest);
    Optional<UserDto> getById(Long id);
    List<UserDto> getAll();
    UserDto update(Long id, UserRequest userRequest);
    void delete(Long id);
    UserDetailsService userDetailService();
    List<UserDto> getUsers();
    Optional<UserDto> getByEmail(String email);
}
