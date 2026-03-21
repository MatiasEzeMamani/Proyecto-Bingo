package com.matias.bingo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matias.bingo.models.BingoCard;

public interface BingoCardRepository extends JpaRepository<BingoCard, Long>{

	List<BingoCard> findByGameIdAndOwnerIsNull(Long gameId);
	
	List<BingoCard> findByOwnerId(Long userId);
	
}
