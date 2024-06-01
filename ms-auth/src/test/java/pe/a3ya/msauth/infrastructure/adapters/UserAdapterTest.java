package pe.a3ya.msauth.infrastructure.adapters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import pe.a3ya.msauth.domain.aggregates.dto.ReniecDto;
import pe.a3ya.msauth.domain.aggregates.dto.UserDto;
import pe.a3ya.msauth.domain.aggregates.requests.UserRequest;
import pe.a3ya.msauth.infrastructure.clients.ApisNetReniecClient;
import pe.a3ya.msauth.infrastructure.dao.RolRepository;
import pe.a3ya.msauth.infrastructure.dao.UserRepository;
import pe.a3ya.msauth.infrastructure.entities.Rol;
import pe.a3ya.msauth.infrastructure.entities.UserEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserAdapterTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private ApisNetReniecClient reniecClient;

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private UserAdapter userAdapter;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private Rol mockRole;

    private UserRequest userRequest;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequest();
        userRequest.setDocumentNumber("12345678");
        userRequest.setEmail("test@example.com");
        userRequest.setPassword("password");
        userRequest.setPhone("123456789");
        userRequest.setBirthDate(LocalDate.now());

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setDocumentNumber("12345678");
        userEntity.setEmail("test@example.com");

        ReniecDto.ReniecDtoBuilder reniecDto = ReniecDto.builder()
                .tipoDocumento('1')
                .numeroDocumento("12345678")
                .nombres("John")
                .apellidoPaterno("Doe")
                .apellidoMaterno("Smith");

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setEmail("jhondoe@domain.com");
        userEntity.setPassword("123456");
        Rol role = new Rol();
        role.setId(1L);
        Set<Rol> roles = Set.of(role);
        userEntity.setRoles(roles);


        when(reniecClient.getReniecInfo(anyString(), anyString())).thenReturn(reniecDto.build());
        when(rolRepository.findByNameRole(anyString())).thenReturn(Optional.of(mockRole));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
    }

    @Test
    void testSaveUser() {

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDto userDto = userAdapter.save(userRequest);

        assertNotNull(userDto);
        assertEquals(userEntity.getName(), userDto.getName());
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        UserDto userDto = userAdapter.getById(1L).orElse(null);

        assertNotNull(userDto);
        assertEquals(userEntity.getId(), userDto.getId());
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(userEntity));

        assertNotNull(userAdapter.getAll());
        assertEquals(1, userAdapter.getAll().size());
    }

    @Test
    void testUpdateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDto userDto = userAdapter.update(1L, userRequest);

        assertNotNull(userDto);
        assertEquals(userEntity.getId(), userDto.getId());
    }

    @Test
    void testUpdateUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserDto userDto = userAdapter.update(1L, userRequest);

        assertNull(userDto);
    }
}
