package com.matias.bingo.services;

import com.matias.bingo.dtos.Response;
import com.matias.bingo.models.User;

public interface DrawnBallService {
	
	Response drawNextBall(Long gameId, User currentUser);
    Response getDrawnBallsByGame(Long gameId);
	
}
