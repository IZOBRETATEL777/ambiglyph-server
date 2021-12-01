package com.izobretatel777.ambiglyphserver.service;

import com.izobretatel777.ambiglyphserver.dto.TextRequestDto;
import com.izobretatel777.ambiglyphserver.dto.TextResponseDto;

public interface TextService {
    TextResponseDto recoverText(TextRequestDto textRequestDto);
}
