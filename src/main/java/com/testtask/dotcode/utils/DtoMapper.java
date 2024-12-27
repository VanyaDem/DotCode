package com.testtask.dotcode.utils;

import com.testtask.dotcode.domain.entity.User;
import com.testtask.dotcode.dto.UserDto;

import java.util.List;

public class DtoMapper {

    public static User mapToUser(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        return user;
    }

    public static UserDto mapToUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public static List<UserDto> mapToUserDtoList(List<User> users) {
        return users
                .stream()
                .map(DtoMapper::mapToUserDto)
                .toList();
    }
}
