package com.izobretatel777.ambiglyphserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TextResponseDto {
    private String text;
    List<List<String>> candidates;
}
