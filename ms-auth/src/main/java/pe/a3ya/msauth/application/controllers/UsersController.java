package pe.a3ya.msauth.application.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import pe.a3ya.msauth.domain.aggregates.response.AuthenticationResponse;
import pe.a3ya.msauth.domain.ports.in.UserServiceIn;
import pe.a3ya.msauth.infrastructure.exceptions.EmailAlreadyExistsException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Tag(
        name = "User maintenance api",
        description = "Api that contains all the end points to maintain the Users entity"
)
public class UsersController {

    private final UserServiceIn userServiceIn;


    @PostMapping("/create")
    @Operation(
            summary = "create User",
            description = "To use the endpoint, you must send a UserRequest object,  which will be saved in the database.",
            parameters = {
                    @Parameter(name = "DocumentNumber", description = "User document number"),
                    @Parameter(name = "email", description = "User email"),
                    @Parameter(name = "password", description = "User password"),
                    @Parameter(name = "phone", description = "User number of phone"),
                    @Parameter(name = "birthday", description = "User date of birthday")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "user created successfully",
                    content = @Content(mediaType = "application/json")
            ),

            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP STATUS 400 Bad Request - Email exists",
                    content = @Content(mediaType = "application/json")
            )

    })
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserRequest userRequest) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(userServiceIn.save(userRequest));
        } catch (EmailAlreadyExistsException e) {
            UserDto userDto = UserDto.builder()
                    .message("Email is already in use. Please choose another.")
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userDto);
        }

    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Search user by ID",
            description = "To use the endpoint, you must send a user ID, which will query the database after validation.",
            parameters = {
                    @Parameter(
                            name = "Authorization",
                            description = "Bearer token for authorization",
                            required = true,
                            in = ParameterIn.HEADER,
                            example = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicnlhbjc4QGdtYWlsLmNvbSIsImlhdCI6MTcxNzA1OTQxOSwiZXhwIjoxNzE3MDU5NzE5fQ.-yp7YBR86oGNgofuihxw6yjzpTZXaKzZYPyKGm9B02I"
                    ),
                    @Parameter(name = "id",description = "customer id")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP STATUS 200 User found successfully",
                    content = {@Content(mediaType = "appication/json",
                            schema = @Schema(implementation = UserDto.class))}

            )

    })
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        UserDto userDto = userServiceIn.getById(id).orElse(null);
        if (userDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok().body(userDto);
    }

    @GetMapping("/all")
    @Operation(
            summary = "List all users",
            description = "To use the endpoint, you must send a user ID, to search all users.",
            parameters = {
                    @Parameter(
                            name = "Authorization",
                            description = "Bearer token for authorization",
                            required = true,
                            in = ParameterIn.HEADER,
                            example = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicnlhbjc4QGdtYWlsLmNvbSIsImlhdCI6MTcxNzA1OTQxOSwiZXhwIjoxNzE3MDU5NzE5fQ.-yp7YBR86oGNgofuihxw6yjzpTZXaKzZYPyKGm9B02I"
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP STATUS 200 Users found successfully",
                    content = @Content(mediaType = "application/json")
            )

    })
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userServiceIn.getAll());
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update User",
            description = "To use the endpoint, you must send a UserRequest object,  which will be update in the database.",
            parameters = {
                    @Parameter(name = "DocumentNumber", description = "User document number"),
                    @Parameter(name = "email", description = "User email"),
                    @Parameter(name = "password", description = "User password"),
                    @Parameter(name = "phone", description = "User number of phone"),
                    @Parameter(name = "birthday", description = "User date of birthday"),
                    @Parameter(
                            name = "Authorization",
                            description = "Bearer token for authorization",
                            required = true,
                            in = ParameterIn.HEADER,
                            example = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicnlhbjc4QGdtYWlsLmNvbSIsImlhdCI6MTcxNzA1OTQxOSwiZXhwIjoxNzE3MDU5NzE5fQ.-yp7YBR86oGNgofuihxw6yjzpTZXaKzZYPyKGm9B02I"
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "user successfully updated",
                    content = @Content(mediaType = "application/json")
            ),

            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP STATUS 400 Bad Request - Email exists",
                    content = @Content(mediaType = "application/json")
            )

    })
    public ResponseEntity<UserDto> update(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
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
    @Operation(
            summary = "Delete user by ID",
            description = "To use the endpoint, you must send a user ID, to delete user.",
            parameters = {
                    @Parameter(
                            name = "Authorization",
                            description = "Bearer token for authorization",
                            required = true,
                            in = ParameterIn.HEADER,
                            example = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJicnlhbjc4QGdtYWlsLmNvbSIsImlhdCI6MTcxNzA1OTQxOSwiZXhwIjoxNzE3MDU5NzE5fQ.-yp7YBR86oGNgofuihxw6yjzpTZXaKzZYPyKGm9B02I"
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP STATUS 200 OK",
                    content = @Content(mediaType = "application/json")
            )

    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userServiceIn.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }


}
