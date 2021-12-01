package com.izobretatel777.ambiglyphserver.service.impl;

import com.izobretatel777.ambiglyphserver.dao.entity.User;
import com.izobretatel777.ambiglyphserver.dao.repo.UserRepo;
import com.izobretatel777.ambiglyphserver.dao.repo.WordRepo;
import com.izobretatel777.ambiglyphserver.dto.UserRequestDto;
import com.izobretatel777.ambiglyphserver.dto.UserResponseDto;
import com.izobretatel777.ambiglyphserver.mapper.UserMapper;
import com.izobretatel777.ambiglyphserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userEntityRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponseDto> getUsers() {
        List<User> entities = userEntityRepository.findAll().stream().filter(u -> u.isActive()).collect(Collectors.toList());
        List<UserResponseDto> listUser = userMapper.toResponseDto(entities);
        return listUser;
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        User entity = userEntityRepository.findById(id).get();
        UserResponseDto response = null;
        if (entity.isActive()) {
            response = userMapper.toResponseDto(entity);
        }
        return  response;
    }

    @Override
    public Long saveUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setLogin(userRequestDto.getLogin());
        user.setPassword(userRequestDto.getPassword());
        user.setActive(true);
        return userEntityRepository.save(user).getId();
    }

    @Override
    public void deleteUserById(Long id) {
        User entity = userEntityRepository.findById(id).get();
        entity.setActive(false);
        userEntityRepository.save(entity);
    }
}