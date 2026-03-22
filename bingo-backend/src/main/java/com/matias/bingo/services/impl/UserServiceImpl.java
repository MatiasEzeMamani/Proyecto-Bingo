package com.matias.bingo.services.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.matias.bingo.dtos.Response;
import com.matias.bingo.dtos.auth.LoginRequestDTO;
import com.matias.bingo.dtos.auth.RefreshTokenRequestDTO;
import com.matias.bingo.dtos.users.UserRegistrationDTO;
import com.matias.bingo.dtos.users.UserResponseDTO;
import com.matias.bingo.dtos.users.UserUpdateDTO;
import com.matias.bingo.exceptions.OurException;
import com.matias.bingo.mappers.UserMapper;
import com.matias.bingo.models.Role;
import com.matias.bingo.models.User;
import com.matias.bingo.repositories.UserRepository;
import com.matias.bingo.services.UserService;
import com.matias.bingo.utils.JWTUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    
    @Override
    public Response register(UserRegistrationDTO dto) {
        Response response = new Response();
        try {
            if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
                throw new OurException("Email already exists");
            }

            if (!dto.getPassword().equals(dto.getConfirmPassword())) {
                throw new OurException("Passwords do not match");
            }

            User user = userMapper.toEntity(dto);
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            if (user.getRole() == null) user.setRole(Role.ROLE_USER);

            User savedUser = userRepository.save(user);
            UserResponseDTO resDTO = userMapper.toResponseDTO(savedUser);

            response.setStatusCode(200);
            response.setMessage("User registered successfully");
            response.setUser(resDTO);

        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("An error occurred during registration: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response login(LoginRequestDTO loginRequest) {
        Response response = new Response();
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new OurException("User not found"));

            String accessToken = jwtUtils.generateAccessToken(user.getEmail(), user.getRole().name());
            String refreshToken = jwtUtils.generateRefreshToken(user);

            response.setStatusCode(200);
            response.setMessage("Login successful");
            response.setToken(accessToken);
            response.setRefreshToken(refreshToken);
            response.setRole(user.getRole());
            response.setExpirationTime("7 Days");

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Login error: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response refreshToken(RefreshTokenRequestDTO request) {
        Response response = new Response();
        try {
            String refreshToken = request.getRefreshToken();
            if (refreshToken == null || !jwtUtils.isValidRefreshToken(refreshToken)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired refresh token");
            }

            String email = jwtUtils.extractUserName(refreshToken);
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new OurException("User not found"));

            String newAccessToken = jwtUtils.generateAccessToken(user.getEmail(), user.getRole().name());

            response.setStatusCode(200);
            response.setMessage("Access token renewed successfully");
            response.setToken(newAccessToken);
            response.setExpirationTime("1 hour");

        } catch (OurException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error renewing token: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllUsers() {
        Response response = new Response();
        try {
            List<User> userList = userRepository.findAll();
            response.setStatusCode(200);
            response.setUsers(userMapper.toResponseDTOList(userList));
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error fetching all users: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserById(Long id) {
        Response response = new Response();
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new OurException("User not found"));
            response.setStatusCode(200);
            response.setUser(userMapper.toResponseDTO(user));
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error finding user: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getMyInfo(String email) {
        Response response = new Response();
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new OurException("User not found"));
            response.setStatusCode(200);
            response.setUser(userMapper.toResponseDTO(user));
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error fetching user info: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateUser(Long id, UserUpdateDTO dto) {
        Response response = new Response();
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new OurException("User not found"));

            userMapper.updateEntityFromDTO(dto, user);

            User updatedUser = userRepository.save(user);
            UserResponseDTO resDTO = userMapper.toResponseDTO(updatedUser);

            response.setStatusCode(200);
            response.setUser(resDTO);
            response.setMessage("User updated successfully");
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating user: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteUser(Long id, User currentUser) {
        Response response = new Response();
        try {
            if (!currentUser.getRole().equals(Role.ROLE_ADMIN)) {
                throw new OurException("You do not have permission to delete users");
            }
            userRepository.findById(id)
                    .orElseThrow(() -> new OurException("User not found"));
            userRepository.deleteById(id);
            response.setStatusCode(200);
            response.setMessage("Success");
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting user: " + e.getMessage());
        }
        return response;
    }
}
