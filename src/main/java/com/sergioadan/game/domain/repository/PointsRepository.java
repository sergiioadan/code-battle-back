package com.sergioadan.game.domain.repository;

import com.sergioadan.game.domain.model.Points;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointsRepository extends JpaRepository<Points,String> {

    Optional<Points> findByPlayer (String player);

    Optional<Points> findByPlayerAndLevel (String player,String level);

}
