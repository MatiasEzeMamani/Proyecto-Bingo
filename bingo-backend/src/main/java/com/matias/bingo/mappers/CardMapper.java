package com.matias.bingo.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.matias.bingo.dtos.cards.BingoCardResponseDTO;
import com.matias.bingo.models.BingoCard;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public abstract class CardMapper {

    public abstract BingoCardResponseDTO toResponseDTO(BingoCard card);

    public abstract List<BingoCardResponseDTO> toResponseDTOList(List<BingoCard> cards);
    
}