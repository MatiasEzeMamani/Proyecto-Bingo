package com.matias.bingo.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.matias.bingo.dtos.Response;
import com.matias.bingo.exceptions.OurException;
import com.matias.bingo.mappers.CardMapper;
import com.matias.bingo.models.BingoCard;
import com.matias.bingo.repositories.BingoCardRepository;
import com.matias.bingo.services.BingoCardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BingoCardServiceImpl implements BingoCardService {

    private final BingoCardRepository bingoCardRepository;
    private final CardMapper bingoCardMapper;

    @Override
    public Response getCardById(Long id) {
        Response response = new Response();
        try {
            BingoCard card = bingoCardRepository.findById(id)
                    .orElseThrow(() -> new OurException("Bingo Card not found"));

            response.setStatusCode(200);
            response.setMessage("Card fetched successfully");
            response.setCard(bingoCardMapper.toResponseDTO(card));

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error fetching card: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getCardsByUserId(Long userId) {
        Response response = new Response();
        try {
            List<BingoCard> userCards = bingoCardRepository.findByOwnerId(userId);

            response.setStatusCode(200);
            response.setMessage("User cards fetched successfully");
            response.setCards(bingoCardMapper.toResponseDTOList(userCards));

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error fetching user cards: " + e.getMessage());
        }
        return response;
    }

    @Override
    public boolean checkBingo(Long cardId, List<Integer> drawnNumbers) {
        BingoCard card = bingoCardRepository.findById(cardId)
                .orElseThrow(() -> new OurException("Card not found"));

        return drawnNumbers.containsAll(card.getNumbers());
    }
}
