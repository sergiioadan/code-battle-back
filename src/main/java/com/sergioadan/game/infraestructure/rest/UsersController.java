package com.sergioadan.game.infraestructure.rest;

import com.sergioadan.game.domain.model.Users;
import com.sergioadan.game.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
public class UsersController {
private final UserRepository userRepository;

    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Users>> searchUsers(@RequestParam("username") String username) {
        if (username.length() < 3) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        List<Users> results = userRepository.findByUsernameContainingIgnoreCase(username);
        return ResponseEntity.ok(results);
    }

}
