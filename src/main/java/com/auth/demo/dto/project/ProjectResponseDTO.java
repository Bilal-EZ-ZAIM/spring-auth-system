package com.auth.demo.dto.project;

import java.time.Instant;
import java.util.UUID;

public record ProjectResponseDTO(
    UUID publicId,
    String name,
    String description,
    Long adminId,
    Instant createdAt,
    Instant updatedAt
) {}
