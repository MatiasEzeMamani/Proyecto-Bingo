package com.matias.bingo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matias.bingo.dtos.Response;
import com.matias.bingo.models.User;
import com.matias.bingo.services.DrawnBallService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/balls")
@RequiredArgsConstructor
public class DrawnBallController {

	private final DrawnBallService ballService;
	
	@PostMapping("/{gameId}/draw")
	public ResponseEntity<Response> drawBall(@PathVariable Long gameId, 
	                                        @AuthenticationPrincipal User currentUser) {
	    Response response = ballService.drawNextBall(gameId, currentUser);
	    return ResponseEntity.status(response.getStatusCode()).body(response);
	}

    @GetMapping("/{gameId}/history")
    public ResponseEntity<Response> getHistory(@PathVariable Long gameId) {
        Response response = ballService.getDrawnBallsByGame(gameId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}