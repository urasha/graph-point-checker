package ru.urasha.webspring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.urasha.webspring.models.UserAccount;
import ru.urasha.webspring.services.AuthenticationService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final Logger logger = Logger.getLogger(AuthenticationController.class.getName());

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserAccount user) {
        logger.info(String.format("Get user for log in: %s", user));

        Optional<String> jwt = authenticationService.login(user);
        Map<String, String> response = new HashMap<>(Map.of("jwt", ""));

        if (jwt.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        response.put("jwt", jwt.get());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
