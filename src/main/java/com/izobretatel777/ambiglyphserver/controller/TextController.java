package com.izobretatel777.ambiglyphserver.controller;

import com.izobretatel777.ambiglyphserver.dto.TextRequestDto;
import com.izobretatel777.ambiglyphserver.dto.TextResponseDto;
import com.izobretatel777.ambiglyphserver.service.TextService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Text scanning", description = "Scanning text for homoglyphs")
@RequestMapping("check")
public class TextController {
    private final TextService textService;
    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'APP')")
    @Operation(
            summary = "Send text for checking",
            description = """
                    All words that are detected to be spoofed will be replaced with
                    <%%ambiglyph-detected>num<ambiglyph-detected%%>, where num - the number of a detection.
                    Only for authorized users/clients.
                    """,
            security = @SecurityRequirement(name = "bearerAuth")
    )
    TextResponseDto checkText(@RequestBody TextRequestDto textRequestDto) {
        return textService.recoverText(textRequestDto);
    }

}
