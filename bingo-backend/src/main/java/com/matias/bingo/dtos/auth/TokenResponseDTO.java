package com.matias.bingo.dtos.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenResponseDTO {

	private String accessToken;

    public TokenResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
