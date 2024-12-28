package com.testtask.dotcode.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {

    private Long id;

    @Size(max = 255)
    @NotBlank(message = "The first name must not be empty.")
    private String firstName;

    @Size(max = 255)
    @NotBlank(message = "The last name must not be empty.")
    private String lastName;

    @Size(max = 255)
    @Email(message = "Email should be valid")
    @NotBlank
    private String email;
}
