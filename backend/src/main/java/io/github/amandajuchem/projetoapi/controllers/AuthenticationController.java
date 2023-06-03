package io.github.amandajuchem.projetoapi.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.amandajuchem.projetoapi.utils.JWTTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JWTTokenUtils jwtTokenUtils;

    @PostMapping("/token")
    ResponseEntity<?> login(@RequestBody ObjectNode object) {

        final var username = object.get("username").asText();
        final var password = object.get("password").asText();
        final var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList()));
        final var responseBody = new HashMap<String, Object>();
        final var token = jwtTokenUtils.generateToken(authentication);

        responseBody.put("access_token", token);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
