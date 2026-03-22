package com.matias.bingo.dtos.auth;

import lombok.Data;

@Data
public class RefreshTokenRequestDTO {
	
	private String refreshToken;
}
