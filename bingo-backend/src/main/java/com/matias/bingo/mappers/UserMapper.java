package com.matias.bingo.mappers;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.matias.bingo.dtos.users.UserRegistrationDTO;
import com.matias.bingo.dtos.users.UserResponseDTO;
import com.matias.bingo.dtos.users.UserUpdateDTO;
import com.matias.bingo.models.User;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
	
	public User toEntity(UserRegistrationDTO dto) {
        if (dto == null) return null;
        
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setConfirmPassword(dto.getConfirmPassword());
        
        user.setGamesCreated(null);
        user.setGamesWon(null);
        user.setProfilePictures(null);
        
        return user;
    }
	
	public abstract UserResponseDTO toResponseDTO(User user);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateEntityFromDTO(UserUpdateDTO dto, @MappingTarget User user);
	
	public abstract List<UserResponseDTO> toResponseDTOList(List<User> users);
	
}
