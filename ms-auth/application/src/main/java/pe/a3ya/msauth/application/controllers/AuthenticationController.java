package pe.a3ya.msauth.application.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.a3ya.msauth.domain.aggregates.dto.UserDto;
import pe.a3ya.msauth.domain.aggregates.requests.SignInRequest;
import pe.a3ya.msauth.domain.aggregates.requests.SignUpRequest;
import pe.a3ya.msauth.domain.aggregates.response.AuthenticationResponse;
import pe.a3ya.msauth.domain.ports.in.AuthenticationServiceIn;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceIn authenticationServiceIn;

    @PostMapping("/signupuser")
    public ResponseEntity<UserDto> signUpUser(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationServiceIn.signUpUser(signUpRequest));
    }
    @PostMapping("/signupadmin")
    public ResponseEntity<UserDto> signUpAdmin(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationServiceIn.signUpAdmin(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signin(@RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(authenticationServiceIn.signin(signInRequest));
    }
}
