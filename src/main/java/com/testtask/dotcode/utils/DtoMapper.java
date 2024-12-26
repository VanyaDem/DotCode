package com.testtask.dotcode.utils;

import com.testtask.dotcode.dto.UserDto;
import com.testtask.dotcode.domain.entity.User;

public class DtoMapper {

    public static User mapToUser(UserDto dto){
        User user = new User();

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        return user;
    }

    public static UserDto mapToUserDto(User user){
        UserDto dto = new UserDto();

        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
