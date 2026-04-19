package com.auth.demo.dto.role;

import java.time.Instant;

public record RoleResponseDTO(
                Long id,

                String name,

                String description,

                Instant createdAt,

                Instant updatedAt) {
}
