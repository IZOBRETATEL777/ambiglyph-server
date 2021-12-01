package com.izobretatel777.ambiglyphserver.service;


import com.izobretatel777.ambiglyphserver.dto.WordRequestDto;
import com.izobretatel777.ambiglyphserver.dto.WordResponseDto;

import java.util.List;

public interface WordService {
    List<WordResponseDto> getWords();
    WordResponseDto getWordById(Long id);
    Long saveWord(WordRequestDto wordRequestDto);
    void deleteWordById(Long id);
}
