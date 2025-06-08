package com.sergioadan.game.infraestructure.rest;

import com.sergioadan.game.application.service.GameService;
import com.sergioadan.game.infraestructure.rest.dto.PointsRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class GameController {
    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @PostMapping("/points")
    public ResponseEntity<Integer> savePoints(@RequestBody PointsRequest request){
        var pointsSaved = service.result(request.getPlayer(),request.getPoints());
        return ResponseEntity.ok(pointsSaved);
    }
}
