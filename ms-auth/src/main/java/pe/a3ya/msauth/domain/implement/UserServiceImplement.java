package pe.a3ya.msauth.domain.implement;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import pe.a3ya.msauth.domain.aggregates.dto.UserDto;
import pe.a3ya.msauth.domain.aggregates.requests.UserRequest;
import pe.a3ya.msauth.domain.ports.in.UserServiceIn;
import pe.a3ya.msauth.domain.ports.out.UserServiceOut;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImplement implements UserServiceIn {

    private final UserServiceOut userServiceOut;

    @Override
    public UserDto save(UserRequest userRequest) {
        return userServiceOut.save(userRequest);
    }

    @Override
    public Optional<UserDto> getById(Long id) {
        return userServiceOut.getById(id);
    }

    @Override
    public List<UserDto> getAll() {
        return userServiceOut.getAll();
    }

    @Override
    public UserDto update(Long id, UserRequest userRequest) {
        return userServiceOut.update(id, userRequest);
    }

    @Override
    public void delete(Long id) {
        userServiceOut.delete(id);
    }

    @Override
    public UserDetailsService userDetailService() {
        return userServiceOut.userDetailService();
    }

    @Override
    public List<UserDto> getUsers() {
        return userServiceOut.getUsers();
    }
}
