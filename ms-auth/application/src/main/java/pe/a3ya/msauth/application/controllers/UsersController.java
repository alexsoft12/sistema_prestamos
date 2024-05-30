package pe.a3ya.msauth.application.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.a3ya.msauth.domain.aggregates.dto.UserDto;
import pe.a3ya.msauth.domain.aggregates.requests.UserRequest;
import pe.a3ya.msauth.domain.ports.in.UserServiceIn;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UsersController {

    private final UserServiceIn userServiceIn;


    @PostMapping("/create")
    public ResponseEntity<UserDto> register(@RequestBody UserRequest userRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userServiceIn.save(userRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
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
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UserRequest userRequest) {
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
