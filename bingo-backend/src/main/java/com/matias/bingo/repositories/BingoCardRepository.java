package com.matias.bingo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matias.bingo.models.BingoCard;

public interface BingoCardRepository extends JpaRepository<BingoCard, Long>{

	List<BingoCard> findByGame_IdAndOwnerIsNull(Long gameId);
	
	List<BingoCard> findByOwnerId(Long userId);

	Optional<BingoCard> findByGame_IdAndCardNumber(Long gameId, Integer cardNumber);	
}
