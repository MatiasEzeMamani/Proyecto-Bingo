package com.matias.bingo.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.matias.bingo.dtos.Response;
import com.matias.bingo.exceptions.OurException;
import com.matias.bingo.models.DrawnBall;
import com.matias.bingo.models.Game;
import com.matias.bingo.models.Status;
import com.matias.bingo.models.User;
import com.matias.bingo.repositories.DrawnBallRepository;
import com.matias.bingo.repositories.GameRepository;
import com.matias.bingo.services.DrawnBallService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DrawnBallServiceImpl implements DrawnBallService {
	
	private final GameRepository gameRepository;
    private final DrawnBallRepository drawnBallRepository;

    @Override
    @Transactional
    public Response drawNextBall(Long gameId, User currentUser) { 
        Response response = new Response();
        try {
            Game game = gameRepository.findById(gameId)
                    .orElseThrow(() -> new OurException("Game not found"));
            
            if (game.getStatus() == Status.WAITING) {
                game.setStatus(Status.IN_PROGRESS);
            }
            
            if (!game.getCreator().getId().equals(currentUser.getId())) {
                throw new OurException("Only the game host can draw a ball.");
            }

            List<Integer> usedNumbers = game.getDrawnBalls().stream()
                    .map(DrawnBall::getNumber)
                    .collect(Collectors.toList());

            if (usedNumbers.size() >= 90) {
                throw new OurException("All 90 balls have already been drawn");
            }

            Integer nextNumber = generateNextAvailableBall(usedNumbers);

            DrawnBall drawnBall = new DrawnBall();
            drawnBall.setNumber(nextNumber);
            drawnBall.setGame(game);
            drawnBallRepository.save(drawnBall);

            game.getCards().forEach(card -> {
                if (card.getPenaltyTurns() > 0) {
                    card.setPenaltyTurns(card.getPenaltyTurns() - 1);
                }
            });
            
            gameRepository.save(game); 

            response.setStatusCode(200);
            response.setMessage("¡Ball " + nextNumber + " drawn!");
            response.setDrawnBall(nextNumber);
            
        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getDrawnBallsByGame(Long gameId) {
        Response response = new Response();
        try {
            Game game = gameRepository.findById(gameId)
                    .orElseThrow(() -> new OurException("Game not found"));
            
            List<Integer> numbers = game.getDrawnBalls().stream()
                    .map(DrawnBall::getNumber)
                    .collect(Collectors.toList());

            response.setStatusCode(200);
            response.setMessage("Drawn balls fetched successfully");
            response.setDrawnBalls(numbers);
            
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error: " + e.getMessage());
        }
        return response;
    }

    private Integer generateNextAvailableBall(List<Integer> usedNumbers) {
        List<Integer> available = new ArrayList<>();
        for (int i = 1; i <= 90; i++) {
            if (!usedNumbers.contains(i)) {
                available.add(i);
            }
        }
        Collections.shuffle(available);
        return available.get(0);
    }
}