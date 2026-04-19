package com.auth.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleResponseDTO {

    private Long id;

    private Long userId;

    private String userName;

    private Long roleId;

    private String roleName;

    private boolean isActive;

    private Instant revokedAt;

    private Long adminId;

    private Instant createdAt;

    private Instant updatedAt;
}
