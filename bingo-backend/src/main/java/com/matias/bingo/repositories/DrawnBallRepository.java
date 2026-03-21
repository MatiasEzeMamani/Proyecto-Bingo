package com.matias.bingo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matias.bingo.models.DrawnBall;

public interface DrawnBallRepository extends JpaRepository<DrawnBall, Long>{

	List<DrawnBall> findByGameIdOrderByDrawnAtDesc(Long gameId);
	
}
