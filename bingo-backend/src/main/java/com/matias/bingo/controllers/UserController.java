package com.matias.bingo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matias.bingo.dtos.Response;
import com.matias.bingo.dtos.users.UserUpdateDTO;
import com.matias.bingo.models.User;
import com.matias.bingo.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/me")
    public ResponseEntity<Response> getMyInfo(@AuthenticationPrincipal User currentUser) {
        Response response = userService.getMyInfo(currentUser.getEmail());
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
	
	@PutMapping("/update")
    public ResponseEntity<Response> updateMyProfile(@AuthenticationPrincipal User currentUser, 
                                                   @Valid @RequestBody UserUpdateDTO dto) {
        Response response = userService.updateUser(currentUser.getId(), dto);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
	
	@GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> getAllUsers() {
        Response response = userService.getAllUsers();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response> deleteUser(@PathVariable Long id, 
	                                           @AuthenticationPrincipal User currentUser) {
	    Response response = userService.deleteUser(id, currentUser);
	    return ResponseEntity.status(response.getStatusCode()).body(response);
	}
}
