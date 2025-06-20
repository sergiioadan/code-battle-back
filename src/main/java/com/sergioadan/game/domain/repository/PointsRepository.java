package com.sergioadan.game.domain.repository;

import com.sergioadan.game.domain.model.Points;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PointsRepository extends JpaRepository<Points,String> {


    List<Points> findByPlayer (String player);

    Optional<Points> findByPlayerAndLevel(String player,String level);

        @Query("SELECT p.player, SUM(p.points) FROM Points p GROUP BY p.player ORDER BY SUM(p.points) DESC")
    List<Object[]> findTop10PlayersByTotalPoints();


}
