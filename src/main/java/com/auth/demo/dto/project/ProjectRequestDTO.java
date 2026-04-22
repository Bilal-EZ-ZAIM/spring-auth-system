package com.auth.demo.dto.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.auth.demo.ValidationMessages.ValidationMessages;

public record ProjectRequestDTO(
        @NotBlank(message = ValidationMessages.PROJECT_NAME_REQUIRED) @Size(min = 1, max = 100, message = "Project name must be between 1 and 100 characters") String name,

        @Size(max = 500, message = "Project description must not exceed 500 characters") String description) {
}
