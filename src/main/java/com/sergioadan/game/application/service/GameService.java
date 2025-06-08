package com.sergioadan.game.application.service;

import com.sergioadan.game.domain.model.Points;
import com.sergioadan.game.domain.repository.PointsRepository;
import com.sergioadan.game.domain.service.CalculatorOfPoints;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final PointsRepository repository;

    public GameService(PointsRepository repository) {
        this.repository = repository;
    }


    public int result (String player, int pointsNow){
        return repository.findByPlayer(player)
                .map(actual ->{
            if (pointsNow > actual.getPoints()){
                actual.setPoints(pointsNow);
                repository.save(actual);
            }
            return actual.getPoints();
        }).orElseGet(()-> {
                    repository.save(new Points(player,pointsNow));
                    return pointsNow;

                });
    }

}
