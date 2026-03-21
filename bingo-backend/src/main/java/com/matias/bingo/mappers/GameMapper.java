package com.matias.bingo.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.matias.bingo.dtos.games.GameResponseDTO;
import com.matias.bingo.models.Game;

@Mapper(componentModel = "spring", uses = {UserMapper.class, CardMapper.class})
public abstract class GameMapper {

    public abstract GameResponseDTO toResponseDTO(Game game);

    public abstract List<GameResponseDTO> toResponseDTOList(List<Game> games);
    
}