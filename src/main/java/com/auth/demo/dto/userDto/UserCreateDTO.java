package com.auth.demo.dto.userDto;

import jakarta.validation.constraints.*;

public record UserCreateDTO(

        @NotBlank(message = "First name is required") @Size(max = 50) String firstname,

        @NotBlank(message = "Last name is required") @Size(max = 50) String lastname,

        @NotBlank(message = "Email is required") @Email(message = "Email should be valid") String email,

        @NotBlank(message = "Password is required") @Size(min = 8, message = "Password must be at least 8 characters") String password) {
}