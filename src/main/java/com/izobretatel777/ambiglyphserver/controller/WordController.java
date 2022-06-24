package com.izobretatel777.ambiglyphserver.controller;

import com.izobretatel777.ambiglyphserver.dto.WordRequestDto;
import com.izobretatel777.ambiglyphserver.dto.WordResponseDto;
import com.izobretatel777.ambiglyphserver.service.WordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("words")
@Tag(name = "Words (Dictionary)", description = "Controller for CRUD manipulations on dataset of words.")
@RequiredArgsConstructor
public class WordController {

    private final WordService wordService;
    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @Operation(
            summary = "Delete a User",
            description = "Delete a User by ID. Only for ADMINs",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public List<WordResponseDto> getWords(){
        return wordService.getWords();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(
            summary = "Get word by ID",
            description = "Get word by its ID. Only for ADMINs",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public WordResponseDto getWordById(@PathVariable long id) {
        return  wordService.getWordById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @Operation(
            summary = "Delete a word by ID",
            description = """
                    Delete a word by ID. Only for ADMINs and USERs.
                    USER can delete a word only if this word was added by him/her.
            """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public void deleteWordById(@PathVariable long id){
        wordService.deleteWordById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @Operation(
            summary = "Save a new word",
            description = "Save new word. For ADMINs and USERs",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Word was added successfully"),
            @ApiResponse(responseCode = "401", description = "Not a USER or ADMIN"),
            @ApiResponse(responseCode = "409", description = "Word is already used")
    })
    public long saveWord(@RequestBody WordRequestDto WordRequestDto){
        return wordService.saveWord(WordRequestDto);
    }
}