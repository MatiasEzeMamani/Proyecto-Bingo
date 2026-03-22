package com.matias.bingo.services;

import java.util.List;

import com.matias.bingo.dtos.Response;

public interface BingoCardService {
	
	Response getCardById(Long id);
	Response getCardsByUserId(Long userId);
	boolean checkBingo(Long cardId, List<Integer> drawnNumbers);
	
}
