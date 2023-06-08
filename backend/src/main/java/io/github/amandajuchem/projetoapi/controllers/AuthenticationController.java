package io.github.amandajuchem.projetoapi.controllers;

import io.github.amandajuchem.projetoapi.security.Authentication;
import io.github.amandajuchem.projetoapi.security.User;
import io.github.amandajuchem.projetoapi.utils.JWTTokenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoint para autenticação")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JWTTokenUtils jwtTokenUtils;

    @Operation(summary = "Authenticate", description = "Authenticate an user")
    @ResponseStatus(OK)
    @PostMapping("/token")
    ResponseEntity<Authentication> login(@RequestBody User user) {

        final var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.username(), user.password(), Collections.emptyList()));
        final var token = jwtTokenUtils.generateToken(authentication);
        final var response = new Authentication(token);

        return ResponseEntity.status(OK).body(response);
    }
}
