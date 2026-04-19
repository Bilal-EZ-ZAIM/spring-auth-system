package com.auth.demo.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleRequestDTO {

    @NotNull(message = "User ID cannot be null")
    private UUID userId;

    @NotNull(message = "Role ID cannot be null")
    private Long roleId;

    @NotNull(message = "Admin ID cannot be null")
    private Long adminId;
}
