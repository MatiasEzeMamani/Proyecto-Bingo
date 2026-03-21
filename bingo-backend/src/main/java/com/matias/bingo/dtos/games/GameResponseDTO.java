package com.matias.bingo.dtos.games;

import java.time.LocalDateTime;
import java.util.List;

import com.matias.bingo.dtos.cards.BingoCardResponseDTO;
import com.matias.bingo.dtos.users.UserResponseDTO;

import lombok.Data;

@Data
public class GameResponseDTO {
	
	private Long id;
    private String name;           
    private String status;         
    private UserResponseDTO creator; 
    private UserResponseDTO winner;  
    private List<BingoCardResponseDTO> cards; 
    private LocalDateTime createdAt;
    
}
