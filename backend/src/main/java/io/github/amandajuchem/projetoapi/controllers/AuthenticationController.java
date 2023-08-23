package io.github.amandajuchem.projetoapi.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.amandajuchem.projetoapi.utils.JWTUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints for authentication management")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;

    /**
     * Login method to generate an authentication token.
     *
     * @param object the JSON object containing the username and password
     * @return The authentication token in the response body
     */
    @PostMapping("/token")
    @ResponseStatus(OK)
    @Operation(summary = "Token", description = "Generate an authentication token")
    HashMap<String, Object> token(@RequestBody ObjectNode object) {

        final var username = object.get("username").asText();
        final var password = object.get("password").asText();
        final var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList()));
        final var responseBody = new HashMap<String, Object>();
        final var token = jwtUtils.generateToken(authentication);

        responseBody.put("access_token", token);

        return responseBody;
    }
}