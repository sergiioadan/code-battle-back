package com.sergioadan.game.infraestructure.rest.dto;

public class PointsRequest {
    private String player;
    private int points;

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
