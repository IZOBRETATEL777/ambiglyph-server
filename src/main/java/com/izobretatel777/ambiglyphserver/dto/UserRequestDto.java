package com.izobretatel777.ambiglyphserver.dto;

import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private Long id;
    private String login;
    private String password;
}
