package com.matias.bingo.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.matias.bingo.dtos.Response;
import com.matias.bingo.dtos.games.GameRequestDTO;
import com.matias.bingo.dtos.games.GameResponseDTO;
import com.matias.bingo.exceptions.OurException;
import com.matias.bingo.mappers.GameMapper;
import com.matias.bingo.models.BingoCard;
import com.matias.bingo.models.DrawnBall;
import com.matias.bingo.models.Game;
import com.matias.bingo.models.Status;
import com.matias.bingo.models.User;
import com.matias.bingo.repositories.BingoCardRepository;
import com.matias.bingo.repositories.GameRepository;
import com.matias.bingo.services.GameService;
import com.matias.bingo.utils.BingoCardGenerator;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

	private final BingoCardRepository bingoCardRepository;
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;
    

    @Override
    public Response createGame(GameRequestDTO dto, User currentUser) {
        Response response = new Response();
        try {
            Game game = new Game();
            game.setName(dto.getName());
            game.setCreator(currentUser);
            game.setStatus(Status.WAITING);

            List<BingoCard> initialCards = new ArrayList<>();
            for (int i = 1; i <= 100; i++) {
                BingoCard card = new BingoCard();
                card.setNumbers(BingoCardGenerator.generate()); 
                card.setCardNumber(i); 
                card.setGame(game);
                card.setOwner(null); 
                initialCards.add(card);
            }
            game.setCards(initialCards);

            Game savedGame = gameRepository.save(game);
            
            response.setStatusCode(200);
            response.setMessage("Game created with 100 cards available.");
            response.setGame(gameMapper.toResponseDTO(savedGame));

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response pickCard(Long gameId, int cardNumber, User currentUser) {
        Response response = new Response();
        try {
            BingoCard card = bingoCardRepository.findByGame_IdAndCardNumber(gameId, cardNumber)
                    .orElseThrow(() -> new OurException("Card not found in this game."));

            if (card.getOwner() != null) {
                throw new OurException("Card #" + cardNumber + " is already taken.");
            }

            card.setOwner(currentUser);
            bingoCardRepository.save(card);

            response.setStatusCode(200);
            response.setMessage("Card #" + cardNumber + " successfully selected.");
            
        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public Response getGameById(Long id) {
        Response response = new Response();
        try {
            Game game = gameRepository.findById(id)
                    .orElseThrow(() -> new OurException("Game not found"));
            response.setStatusCode(200);
            response.setGame(gameMapper.toResponseDTO(game));
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error fetching game: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllGames() {
        Response response = new Response();
        try {
            List<Game> games = gameRepository.findAll();
            response.setStatusCode(200);
            response.setGames(gameMapper.toResponseDTOList(games));
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error fetching games: " + e.getMessage());
        }
        return response;
    }
    
    @Override
    public Response getAllActiveGames() {
        Response response = new Response();
        try {
            List<Game> activeGames = gameRepository.findByStatus(Status.WAITING);

            List<GameResponseDTO> gameDTOList = gameMapper.toResponseDTOList(activeGames);
            
            response.setStatusCode(200);
            response.setGames(gameDTOList); 

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error retrieving games: " + e.getMessage());
        }
        return response;
    }
    
    @Override
    @Transactional
    public Response markNumber(Long gameId, Long cardId, int number, User currentUser) {
        Response response = new Response();
        try {
            BingoCard card = bingoCardRepository.findById(cardId)
                    .orElseThrow(() -> new OurException("Card not found"));

            if (card.getMarkedNumbers().contains(number)) {
                throw new OurException("You already marked number " + number + ". No cheating!");
            }

            if (card.getPenaltyTurns() > 0) {
                throw new OurException("You are penalized for " + card.getPenaltyTurns() + " turns.");
            }

            if (!card.getNumbers().contains(number)) {
                throw new OurException("That number is not on your card.");
            }

            Game game = gameRepository.findById(gameId).orElseThrow();
            
            boolean ballExists = game.getDrawnBalls().stream()
                    .anyMatch(ball -> ball.getNumber() == number);

            if (ballExists) {
                card.getMarkedNumbers().add(number);
                bingoCardRepository.save(card);
                
                response.setStatusCode(200);
                response.setMessage("Number " + number + " successfully marked.");
            } else {
                card.setPenaltyTurns(2);
                bingoCardRepository.save(card);
                response.setStatusCode(400);
                response.setMessage("THAT NUMBER HAS NOT BEEN DRAWN! Penalized for 2 turns.");
            }

        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    @Transactional
    public Response checkWinner(Long gameId, Long cardId, User currentUser) {
        Response response = new Response();
        try {
            Game game = gameRepository.findById(gameId).orElseThrow();

            if (game.getStatus() != Status.IN_PROGRESS) {
                throw new OurException("The game is not currently in progress.");
            }

            BingoCard card = bingoCardRepository.findById(cardId).orElseThrow();

            List<Integer> cleanCardNumbers = card.getNumbers().stream()
                    .filter(n -> n != 0)
                    .toList();

            List<Integer> drawnNumbers = game.getDrawnBalls().stream()
                    .map(DrawnBall::getNumber)
                    .toList();

            if (drawnNumbers.containsAll(cleanCardNumbers)) {
                game.setStatus(Status.FINISHED);
                game.setWinner(currentUser);
                gameRepository.save(game);
                
                response.setStatusCode(200);
                response.setMessage("BINGO! Congratulations " + currentUser.getName() + "!");
                response.setGame(gameMapper.toResponseDTO(game));
            } else {
                response.setStatusCode(400);
                response.setMessage("Card not yet complete.");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }
}
