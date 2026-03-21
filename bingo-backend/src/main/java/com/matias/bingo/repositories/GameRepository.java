package com.matias.bingo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matias.bingo.models.Game;
import com.matias.bingo.models.Status;

public interface GameRepository extends JpaRepository<Game, Long>{
	
	List<Game> findByStatus(Status status);

}
