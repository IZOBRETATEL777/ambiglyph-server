package com.izobretatel777.ambiglyphserver.mapper;

import com.izobretatel777.ambiglyphserver.dao.entity.User;
import com.izobretatel777.ambiglyphserver.dto.UserResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    List<UserResponseDto> toResponseDto(List<User> source);
    UserResponseDto toResponseDto(User source);
}
