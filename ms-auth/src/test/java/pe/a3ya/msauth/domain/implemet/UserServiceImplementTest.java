package pe.a3ya.msauth.domain.implemet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.a3ya.msauth.domain.aggregates.dto.UserDto;
import pe.a3ya.msauth.domain.aggregates.requests.UserRequest;
import pe.a3ya.msauth.domain.implement.UserServiceImplement;
import pe.a3ya.msauth.domain.ports.out.UserServiceOut;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplementTest {
    @Mock
    private UserServiceOut userServiceOut;

    @InjectMocks
    private UserServiceImplement userServiceImplement;
    private UserRequest userRequest;
    private UserDto userDto;
    private List<UserDto> userDtoList;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequest();
        userRequest.setDocumentNumber("12345678");
        userRequest.setEmail("jhondoe@domain.com");
        userRequest.setPassword("password");
        userRequest.setPhone("987654321");
        userRequest.setBirthDate(LocalDate.parse("1990-01-01"));

        userDto = UserDto.builder()
                .id(1L)
                .documentNumber("12345678")
                .email("jhondoe@domain.com")
                .phone("987654321")
                .build();

        userDtoList = List.of(userDto);
    }

    @Test
    void testCreateUser() {
        when(userServiceOut.save(any(UserRequest.class))).thenReturn(userDto);
        UserDto result = userServiceImplement.save(userRequest);
        verify(userServiceOut, times(1)).save(any(UserRequest.class));
        assertNotNull(result);
        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getDocumentNumber(), result.getDocumentNumber());
    }

    @Test
    void testGetUser() {
        when(userServiceOut.getById(any(Long.class))).thenReturn(Optional.ofNullable(userDto));
        Optional<UserDto> result = userServiceImplement.getById(1L);
        verify(userServiceOut, times(1)).getById(any(Long.class));
        assertNotNull(result);
        assertEquals(userDto.getId(), result.get().getId());
        assertEquals(userDto.getDocumentNumber(), result.get().getDocumentNumber());
    }
    @Test
    void testGetAllUsers() {
        when(userServiceOut.getAll()).thenReturn(userDtoList);
        List<UserDto> result = userServiceImplement.getAll();
        verify(userServiceOut, times(1)).getAll();
        assertNotNull(result);
        assertEquals(userDtoList.size(), result.size());
    }

    @Test
    void testUpdateUser() {
        when(userServiceOut.update(anyLong(), any(UserRequest.class))).thenReturn(userDto);
        UserDto result = userServiceImplement.update(1L, userRequest);
        verify(userServiceOut, times(1)).update(anyLong(), any(UserRequest.class));
        assertNotNull(result);
        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getDocumentNumber(), result.getDocumentNumber());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userServiceOut).delete(anyLong());
        userServiceImplement.delete(1L);
        verify(userServiceOut, times(1)).delete(anyLong());
    }

}
