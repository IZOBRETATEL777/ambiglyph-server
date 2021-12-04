package com.izobretatel777.ambiglyphserver.controller;

import com.izobretatel777.ambiglyphserver.dto.TextRequestDto;
import com.izobretatel777.ambiglyphserver.dto.TextResponseDto;
import com.izobretatel777.ambiglyphserver.service.TextService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("check")
public class TextController {
    private final TextService textService;
    @PostMapping
    TextResponseDto checkText(@RequestBody TextRequestDto textRequestDto) {
        return textService.recoverText(textRequestDto);
    }

}
