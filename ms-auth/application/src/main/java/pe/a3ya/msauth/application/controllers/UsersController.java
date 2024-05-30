package pe.a3ya.msauth.application.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pe.a3ya.msauth.domain.aggregates.dto.UserDto;
import pe.a3ya.msauth.domain.aggregates.requests.UserRequest;
import pe.a3ya.msauth.domain.ports.in.UserServiceIn;
import pe.a3ya.msauth.infrastructure.exceptions.EmailAlreadyExistsException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UsersController {

    private final UserServiceIn userServiceIn;


    @PostMapping("/create")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserRequest userRequest) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(userServiceIn.save(userRequest));
        }catch (EmailAlreadyExistsException e){
            UserDto userDto =  UserDto.builder()
                    .message("Email is already in use. Please choose another.")
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userDto);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id ) {
        UserDto userDto = userServiceIn.getById(id).orElse(null);
        if (userDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok().body(userDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userServiceIn.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id,@Valid @RequestBody UserRequest userRequest) {
        UserDto userDto = userServiceIn.update(id, userRequest);
        if (userDto != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userDto);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userServiceIn.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }


}
