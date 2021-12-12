package com.izobretatel777.ambiglyphserver.controller;

import com.izobretatel777.ambiglyphserver.dto.UserRequestDto;
import com.izobretatel777.ambiglyphserver.dto.UserResponseDto;
import com.izobretatel777.ambiglyphserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponseDto> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/me")
    public UserResponseDto getUser(){
        return userService.getUser();
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable long id) {
        return  userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable long id){
        userService.deleteUserById(id);
    }

    @PostMapping
    public long saveUser(@RequestBody UserRequestDto userRequestDto){
        return userService.saveUser(userRequestDto);
    }
}
