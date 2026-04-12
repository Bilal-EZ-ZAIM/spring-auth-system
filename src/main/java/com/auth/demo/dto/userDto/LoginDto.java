package com.auth.demo.dto.userDto;

import jakarta.validation.constraints.*;
// import lombok.*;

// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
// public class LoginDto {

//     @NotBlank(message = "Email is required")
//     @Email(message = "Email should be valid")
//     private String email;

//     @NotBlank(message = "Password is required")
//     @Size(min = 8, message = "Password must be at least 8 characters")
//     private String password;
// }

public record LoginDto(

        @NotBlank(message = "Email is required") @Email(message = "Email should be valid") String email,

        @NotBlank(message = "Password is required") @Size(min = 8, message = "Password must be at least 8 characters") String password) {
}