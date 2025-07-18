package com.sergioadan.game.application.service;

import com.sergioadan.game.domain.model.Difficulty;
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
    private final long MAX_TIME = 15000;

    public GameService(PointsRepository repository) {
        this.repository = repository;
    }


    public int result(String player, int pointsNow, String level,int hits) {
        return repository.findByPlayerAndLevel(player, level)
                .map(actual -> {
                    if (pointsNow > actual.getPoints() || (pointsNow == actual.getPoints() && hits > actual.getHits())) {
                        actual.setPoints(pointsNow);
                        actual.setHits(hits);
                        repository.save(actual);
                    }
                    return actual.getPoints();
                })
                .orElseGet(() -> {
                    repository.save(new Points(player, pointsNow, level,hits));
                    return pointsNow;
                });
    }


    public int calculator(Boolean isCorrect, Difficulty difficulty, Long time){
        if(!isCorrect) return 0;

        int base = switch (difficulty){
            case Hard -> 30;
            case Basic -> 20;
            case Medium -> 10;
        };
        double timeFactor = Math.max(0.5, (double) (MAX_TIME - time) / MAX_TIME);
        double points = base * (1 + timeFactor);

        int totalValue = (int) points;

        return totalValue;

    }

}
