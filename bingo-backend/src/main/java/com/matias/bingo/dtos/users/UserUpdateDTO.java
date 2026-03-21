package com.matias.bingo.dtos.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
	
	@Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    @Email(message = "Please provide a valid email address")
    private String email;
    
}
