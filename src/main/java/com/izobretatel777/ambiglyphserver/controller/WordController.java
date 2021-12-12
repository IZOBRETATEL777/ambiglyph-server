package com.izobretatel777.ambiglyphserver.controller;

import com.izobretatel777.ambiglyphserver.dto.WordRequestDto;
import com.izobretatel777.ambiglyphserver.dto.WordResponseDto;
import com.izobretatel777.ambiglyphserver.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("words")
@RequiredArgsConstructor
public class WordController {

    private final WordService wordService;
    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<WordResponseDto> getWords(){
        return wordService.getWords();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public WordResponseDto getWordById(@PathVariable long id) {
        return  wordService.getWordById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public void deleteWordById(@PathVariable long id){
        wordService.deleteWordById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public long saveWord(@RequestBody WordRequestDto WordRequestDto){
        return wordService.saveWord(WordRequestDto);
    }
}