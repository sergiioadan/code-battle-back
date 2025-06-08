package com.sergioadan.game.domain.service;

import com.sergioadan.game.domain.model.Difficulty;

public class CalculatorOfPoints {
    private final long MAX_TIME = 15000;

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
