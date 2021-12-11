package com.izobretatel777.ambiglyphserver.service.impl;

import com.izobretatel777.ambiglyphserver.dao.entity.User;
import com.izobretatel777.ambiglyphserver.dao.repo.UserRepo;
import com.izobretatel777.ambiglyphserver.dto.UserRequestDto;
import com.izobretatel777.ambiglyphserver.dto.UserResponseDto;
import com.izobretatel777.ambiglyphserver.mapper.UserMapper;
import com.izobretatel777.ambiglyphserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userEntityRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponseDto> getUsers() {
        List<User> entities = userEntityRepository.findAll().stream().filter(User::isActive).collect(Collectors.toList());
        return userMapper.toResponseDto(entities);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        Optional<User> entity = userEntityRepository.findById(id);
        if (entity.isEmpty()) {
            return null;
        }
        User user = entity.get();
        UserResponseDto response = null;
        if (user.isActive()) {
            response = userMapper.toResponseDto(user);
        }
        return response;
    }

    @Override
    public Long saveUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setLogin(userRequestDto.getLogin());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setActive(true);
        return userEntityRepository.save(user).getId();
    }

    @Override
    public void deleteUserById(Long id) {
        Optional<User> entity = userEntityRepository.findById(id);
        if (entity.isEmpty()) {
            return;
        }
        User user = entity.get();
        user.setActive(false);
        userEntityRepository.save(user);
    }
}