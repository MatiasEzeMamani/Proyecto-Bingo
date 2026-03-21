package com.matias.bingo.dtos.games;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class GameRequestDTO {

	@NotEmpty(message = "Game name is required")
    private String name;
    
}
