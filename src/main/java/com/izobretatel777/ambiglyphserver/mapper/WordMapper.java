package com.izobretatel777.ambiglyphserver.mapper;

import com.izobretatel777.ambiglyphserver.dao.entity.Word;
import com.izobretatel777.ambiglyphserver.dto.WordResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WordMapper {
    List<WordResponseDto> toResponseDto(List<Word> source);
    WordResponseDto toResponseDto(Word source);
}
