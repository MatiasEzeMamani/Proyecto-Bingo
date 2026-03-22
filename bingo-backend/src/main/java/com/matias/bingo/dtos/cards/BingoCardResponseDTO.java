package com.matias.bingo.dtos.cards;

import java.util.List;

import com.matias.bingo.dtos.users.UserResponseDTO;

import lombok.Data;

@Data
public class BingoCardResponseDTO {
    
	private Long id;
	private List<Integer> numbers;
    private List<Integer> markedNumbers;
    private UserResponseDTO owner; 
    private Boolean isWinner;
    private int penaltyTurns;
}
