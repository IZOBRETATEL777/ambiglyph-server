package com.izobretatel777.ambiglyphserver.service;

import com.izobretatel777.ambiglyphserver.dto.UserRequestDto;
import com.izobretatel777.ambiglyphserver.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    List<UserResponseDto> getUsers();
    UserResponseDto getUserById(Long id);
    Long saveUser(UserRequestDto UserRequestDto);
    void deleteUserById(Long id);
    UserResponseDto getUser();
}
