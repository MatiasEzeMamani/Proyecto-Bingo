package com.matias.bingo.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.matias.bingo.dtos.cards.BingoCardResponseDTO;
import com.matias.bingo.dtos.games.GameResponseDTO;
import com.matias.bingo.dtos.users.UserResponseDTO;
import com.matias.bingo.models.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
	
	private int statusCode;
    private String message;
    
    @JsonProperty("accessToken")
    private String token;
    private String refreshToken;
    private String expirationTime;
    private Role role;
    
    private UserResponseDTO user;
    private List<UserResponseDTO> users;

    private GameResponseDTO game;
    private List<GameResponseDTO> games;
    private BingoCardResponseDTO card;
    private List<BingoCardResponseDTO> cards;
    
    private Integer drawnBall;
    
    public Response(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
