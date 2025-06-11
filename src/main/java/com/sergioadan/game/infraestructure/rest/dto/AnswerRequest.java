package com.sergioadan.game.infraestructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sergioadan.game.domain.model.Difficulty;

public class AnswerRequest{
private String player;
private String nivel;

@JsonProperty("isCorrect")
private boolean isCorrect;

private Difficulty difficulty;
private long time;

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }


    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
