package pe.a3ya.msauth.application.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import pe.a3ya.msauth.domain.aggregates.dto.UserDto;
import pe.a3ya.msauth.domain.aggregates.requests.SignInRequest;
import pe.a3ya.msauth.domain.aggregates.requests.TokenRequest;
import pe.a3ya.msauth.domain.aggregates.response.AuthenticationResponse;
import pe.a3ya.msauth.domain.aggregates.response.TokenResponse;
import pe.a3ya.msauth.domain.ports.in.AuthenticationServiceIn;
import pe.a3ya.msauth.domain.ports.in.JWTServiceIn;
import pe.a3ya.msauth.domain.ports.in.UserServiceIn;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
@Tag(
        name = "Authentication maintenance api",
        description = "Api that contains all the end points to maintain the Authentication"
)
public class AuthenticationController {

    private final AuthenticationServiceIn authenticationServiceIn;
    private final JWTServiceIn jwtServiceIn;
    private final UserServiceIn userServiceIn;

    @PostMapping("/signin")
    @Operation(
            summary = "SignIn User",
            description = "To use the endpoint, you must send a SignEd Request object, which will be validated in the database.",
            parameters = {
                    @Parameter(name = "email", description = "User email to log in"),
                    @Parameter(name = "password", description = "User password to log in")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP STATUS 200 access token created successfully",
                    content = @Content(mediaType = "application/json")
            ),

            @ApiResponse(
                    responseCode = "403",
                    description = "HTTP STATUS 403 Email or password incorrect",
                    content = @Content(mediaType = "application/json")
            )

    })

    public ResponseEntity<AuthenticationResponse> signin(@Valid @RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authenticationServiceIn.signin(signInRequest));
    }

    @PostMapping("/security")
    public ResponseEntity<TokenResponse> security(@RequestBody TokenRequest token) {
        final String userEmail;
        TokenResponse response = new TokenResponse();
        try {
            userEmail = jwtServiceIn.extractUsername(token.getToken());
            UserDetails userDetails = userServiceIn.userDetailService().loadUserByUsername(userEmail);
            Optional<UserDto> userInfo = userServiceIn.getByEmail(userEmail);

            if (userInfo.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            response.setId(userInfo.get().getId());
            response.setEmail(userInfo.get().getEmail());

            if (jwtServiceIn.validateToken(token.getToken(), userDetails)) {
                response.setState(true);
                return ResponseEntity.ok().body(response);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
