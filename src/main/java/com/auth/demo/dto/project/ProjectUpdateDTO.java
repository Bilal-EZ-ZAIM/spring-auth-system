package com.auth.demo.dto.project;

import jakarta.validation.constraints.Size;

public record ProjectUpdateDTO(
        @Size(min = 1, max = 100, message = "Project name must be between 1 and 100 characters") String name,

        @Size(max = 500, message = "Project description must not exceed 500 characters") String description) {
}
