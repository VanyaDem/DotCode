package com.testtask.dotcode.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {

    private Long id;

    @NotBlank(message = "The first name must not be empty.")
    private String firstName;

    @NotBlank(message = "The last name must not be empty.")
    private String lastName;

    @Email(message = "Email should be valid")
    @NotBlank
    private String email;
}
