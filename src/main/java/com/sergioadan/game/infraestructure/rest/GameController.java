package com.sergioadan.game.infraestructure.rest;

import com.sergioadan.game.application.service.GameService;
import com.sergioadan.game.infraestructure.helper.AuthUtils;
import com.sergioadan.game.infraestructure.rest.dto.AnswerRequest;
import com.sergioadan.game.infraestructure.rest.dto.PointsRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class GameController {
    private static final Logger log = LoggerFactory.getLogger(GameController.class);
    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @PostMapping("/points")
    public ResponseEntity<Integer> savePoints(@RequestBody PointsRequest request){

        String username = AuthUtils.getCurrentUsername();
        var pointsSaved = service.result(username,request.getPoints(),request.getLevel());
        log.info("Se ha guardado" + pointsSaved);
        return ResponseEntity.ok(pointsSaved);
    }

    @PostMapping("/calculate")
    public ResponseEntity<Integer> calculatePoints(@RequestBody AnswerRequest request) {
        int points =service.calculator(request.isCorrect(), request.getDifficulty(), request.getTime());
        log.info("Se ha calculado "+points);
        return ResponseEntity.ok(points);
    }
}
