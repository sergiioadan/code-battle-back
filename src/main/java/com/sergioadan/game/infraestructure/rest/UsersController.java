package com.sergioadan.game.infraestructure.rest;

import com.sergioadan.game.domain.model.Points;
import com.sergioadan.game.domain.model.Users;
import com.sergioadan.game.domain.repository.PointsRepository;
import com.sergioadan.game.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class UsersController {
private final UserRepository userRepository;
private final PointsRepository pointsRepository;

    public UsersController(UserRepository userRepository, PointsRepository pointsRepository) {
        this.userRepository = userRepository;
        this.pointsRepository = pointsRepository;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Users>> searchUsers(@RequestParam("username") String username) {
        if (username.length() < 3) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        List<Users> results = userRepository.findByUsernameContainingIgnoreCase(username);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/ranking/position/{username}")
    public ResponseEntity<Integer> getUserPosition(@PathVariable("username") String username) {

        List<Users> usuarios = userRepository.findAll();
        Map<String, Integer> puntosPorUsuario = new HashMap<>();

        for (Users user : usuarios) {
            List<Points> puntos = pointsRepository.findByPlayer(user.getUsername());
            int total = puntos.stream().mapToInt(Points::getPoints).sum();
            puntosPorUsuario.put(user.getUsername(), total);
        }

        List<String> ranking = puntosPorUsuario.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .toList();

        int posicion = ranking.indexOf(username);
        return posicion >= 0 ? ResponseEntity.ok(posicion + 1) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(-1);
    }

}
