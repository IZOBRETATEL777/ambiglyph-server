package com.izobretatel777.ambiglyphserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WordRequestDto {
    private Long id;
    private String text;
    private Long userId;
}
