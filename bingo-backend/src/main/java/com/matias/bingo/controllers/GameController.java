package com.matias.bingo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.matias.bingo.dtos.Response;
import com.matias.bingo.dtos.games.GameRequestDTO;
import com.matias.bingo.models.User;
import com.matias.bingo.services.GameService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/games")
@RequiredArgsConstructor
public class GameController {
	
	private final GameService gameService;
	
	@PostMapping("/create")
    public ResponseEntity<Response> createGame(@RequestBody GameRequestDTO dto, 
                                             @AuthenticationPrincipal User currentUser) {
        Response response = gameService.createGame(dto, currentUser);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
	
	@PostMapping("/{gameId}/pick-card")
    public ResponseEntity<Response> pickCard(
            @PathVariable Long gameId, 
            @RequestParam int cardNumber, 
            @AuthenticationPrincipal User currentUser) {
        Response response = gameService.pickCard(gameId, cardNumber, currentUser);
        
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
	
	@GetMapping("/active")
    public ResponseEntity<Response> getActiveGames() {
        Response response = gameService.getAllActiveGames();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
	
	@GetMapping("/{gameId}")
    public ResponseEntity<Response> getGameDetails(@PathVariable Long gameId) {
        Response response = gameService.getGameById(gameId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
	
	@PostMapping("/{gameId}/claim-bingo/{cardId}")
	public ResponseEntity<Response> claimBingo(
	        @PathVariable Long gameId, 
	        @PathVariable Long cardId, 
	        @AuthenticationPrincipal User currentUser) {
	    
	    Response response = gameService.checkWinner(gameId, cardId, currentUser);
	    return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@PostMapping("/{gameId}/cards/{cardId}/mark")
	public ResponseEntity<Response> markNumber(
	        @PathVariable Long gameId, 
	        @PathVariable Long cardId, 
	        @RequestParam int number, 
	        @AuthenticationPrincipal User currentUser) {
	    
	    Response response = gameService.markNumber(gameId, cardId, number, currentUser);
	    
	    return ResponseEntity.status(response.getStatusCode()).body(response);
	}
}
