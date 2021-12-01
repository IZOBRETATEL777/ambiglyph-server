package com.izobretatel777.ambiglyphserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TextRequestDto {
    private Long userId;
    private String text;
}
