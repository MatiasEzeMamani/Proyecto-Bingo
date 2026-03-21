package com.matias.bingo.dtos.users;

import com.matias.bingo.models.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
	
	private Long id;
    private String name;
    private String email;
    private Role role;
    private String status;
    
}
