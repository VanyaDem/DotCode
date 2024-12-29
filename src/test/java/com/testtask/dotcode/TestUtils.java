package com.testtask.dotcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testtask.dotcode.domain.entity.User;
import com.testtask.dotcode.dto.UserDto;

import java.util.List;

public class TestUtils {

    public static UserDto getUser(){
        UserDto dto = new UserDto();
        dto.setFirstName("Ivan");
        dto.setLastName("Demydenko");
        dto.setEmail("foo@email.com");
        return dto;
    }

    public static List<User> getUserList() {
        User ivan = new User();
        ivan.setFirstName("Ivan");
        ivan.setLastName("Demydenko");
        ivan.setEmail("foo@email.com");

        User nic = new User();
        nic.setFirstName("Nick");
        nic.setLastName("Yefimov");
        nic.setEmail("yefimov@email.com");

        User ann = new User();
        ann.setFirstName("Anna");
        ann.setLastName("Demydenko");
        ann.setEmail("demydenkoann@email.com");

        return List.of(ivan, nic, ann);
    }

    public static String objectToJson(ObjectMapper objectMapper, Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
