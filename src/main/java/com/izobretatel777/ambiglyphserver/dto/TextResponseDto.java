package com.izobretatel777.ambiglyphserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TextResponseDto {
    private String text;
    private boolean modified;
}
