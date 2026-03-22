package com.matias.bingo.services;

import com.matias.bingo.dtos.Response;
import com.matias.bingo.dtos.games.GameRequestDTO;
import com.matias.bingo.models.User;

public interface GameService {
	
	Response createGame(GameRequestDTO dto, User currentUser);
    Response getGameById(Long id);
    Response getAllGames();
    Response pickCard(Long gameId, int cardNumber, User currentUserr);
	Response getAllActiveGames();
	Response checkWinner(Long gameId, Long cardId, User currentUser);
	Response markNumber(Long gameId, Long cardId, int number, User currentUser);
	
}