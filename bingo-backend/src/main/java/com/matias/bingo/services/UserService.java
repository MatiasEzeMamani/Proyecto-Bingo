package com.matias.bingo.services;

import com.matias.bingo.dtos.Response;
import com.matias.bingo.dtos.auth.LoginRequestDTO;
import com.matias.bingo.dtos.auth.RefreshTokenRequestDTO;
import com.matias.bingo.dtos.users.UserRegistrationDTO;
import com.matias.bingo.dtos.users.UserUpdateDTO;
import com.matias.bingo.models.User;

public interface UserService {
	
	Response register(UserRegistrationDTO dto);
    Response login(LoginRequestDTO loginRequest);
    Response refreshToken(RefreshTokenRequestDTO request);
    Response getAllUsers();
    Response getUserById(Long id);
    Response getMyInfo(String email);
    Response updateUser(Long id, UserUpdateDTO dto);
    Response deleteUser(Long id, User currentUser);
    
}
