package com.sergioadan.game.infraestructure.rest;

import com.sergioadan.game.application.service.GameService;
import com.sergioadan.game.domain.model.Points;
import com.sergioadan.game.domain.repository.PointsRepository;
import com.sergioadan.game.infraestructure.helper.AuthUtils;
import com.sergioadan.game.infraestructure.rest.dto.AnswerRequest;
import com.sergioadan.game.infraestructure.rest.dto.PointsRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class GameController {
    private static final Logger log = LoggerFactory.getLogger(GameController.class);
    private final GameService service;
    private final PointsRepository pointsRepository;

    public GameController(GameService service, PointsRepository pointsRepository) {
        this.service = service;
        this.pointsRepository = pointsRepository;
    }

    @PostMapping("/points")
    public ResponseEntity<Integer> savePoints(@RequestBody PointsRequest request){

        String username = AuthUtils.getCurrentUsername();
        var pointsSaved = service.result(username,request.getPoints(),request.getLevel(), request.getHits());
        System.out.println("Nivel recibido: " + request.getLevel());
        log.info("Se ha guardado" + pointsSaved);
        return ResponseEntity.ok(pointsSaved);
    }

    @PostMapping("/calculate")
    public ResponseEntity<Integer> calculatePoints(@RequestBody AnswerRequest request) {
        int points =service.calculator(request.isCorrect(), request.getDifficulty(), request.getTime());
        log.info("Se ha calculado "+points);
        return ResponseEntity.ok(points);
    }
    @GetMapping("/progress")
    public ResponseEntity<?> getProgress(@RequestParam String username) {
        List<Points> points = pointsRepository.findByPlayer(username);
        Map<String, Boolean> progreso = new HashMap<>();

        for (Points p : points) {
            if (p.getHits() >= 7) {
                progreso.put(p.getLevel(), true);
            } else {
                progreso.put(p.getLevel(), false);
            }
        }

        return ResponseEntity.ok(progreso);
    }
    
}
