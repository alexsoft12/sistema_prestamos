package pe.a3ya.msauth.infrastructure.adapters;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pe.a3ya.msauth.domain.aggregates.dto.UserDto;
import pe.a3ya.msauth.domain.aggregates.requests.SignInRequest;
import pe.a3ya.msauth.domain.aggregates.requests.SignUpRequest;
import pe.a3ya.msauth.domain.aggregates.response.AuthenticationResponse;
import pe.a3ya.msauth.domain.ports.out.AuthenticationServiceOut;
import pe.a3ya.msauth.domain.ports.out.JWTServiceOut;
import pe.a3ya.msauth.infrastructure.dao.RolRepository;
import pe.a3ya.msauth.infrastructure.dao.UserRepository;
import pe.a3ya.msauth.infrastructure.entities.Rol;
import pe.a3ya.msauth.infrastructure.entities.Role;
import pe.a3ya.msauth.infrastructure.entities.UserEntity;
import pe.a3ya.msauth.infrastructure.mappers.UserMapper;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationAdapter implements AuthenticationServiceOut {

    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    private final JWTServiceOut jwtServiceOut;
    private final AuthenticationManager authenticationManager;



    @Transactional
    @Override
    public UserDto signUpUser(SignUpRequest signUpRequest) {
        /*UserEntity userEntity = new UserEntity();
        userEntity.setName(signUpRequest.getNames());
        userEntity.setLastName(signUpRequest.getLastName());
        userEntity.setMotherLastName(signUpRequest.getMotherLastName());
        userEntity.setEmail(signUpRequest.getEmail());


        Set<Rol> assignedRoles = new HashSet<>();
        Rol userRole = rolRepository.findByNameRole(Role.USER.name()).orElseThrow(() -> new RuntimeException("Error: Role not found."));

        assignedRoles.add(userRole);

        userEntity.setRoles(assignedRoles);
        userEntity.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        return UserMapper.fromEntityToDto(userRepository.save(userEntity));*/
        return null;
    }

    @Override
    public UserDto signUpAdmin(SignUpRequest signUpRequest) {
       /* UserEntity userEntity = new UserEntity();
        userEntity.setName(signUpRequest.getNames());
        userEntity.setLastName(signUpRequest.getLastName());
        userEntity.setMotherLastName(signUpRequest.getMotherLastName());
        userEntity.setEmail(signUpRequest.getEmail());

        Set<Rol> assignedRoles = new HashSet<>();

        Rol userRole = rolRepository.findByNameRole(Role.ADMIN.name()).orElseThrow(() -> new RuntimeException("Error: Role not found."));

        assignedRoles.add(userRole);

        userEntity.setRoles(assignedRoles);
        userEntity.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        return UserMapper.fromEntityToDto(userRepository.save(userEntity));*/
        return null;
    }

    @Override
    public AuthenticationResponse signin(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getEmail(),signInRequest.getPassword()));
        var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("Invalid email"));

        var jwt = jwtServiceOut.generateToken(user);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(jwt);
        return authenticationResponse;
    }

}
