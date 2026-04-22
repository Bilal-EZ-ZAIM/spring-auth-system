package com.auth.demo.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth.demo.apiResponces.ApiResponse;
import com.auth.demo.dto.project.ProjectRequestDTO;
import com.auth.demo.dto.project.ProjectResponseDTO;
import com.auth.demo.dto.project.ProjectUpdateDTO;
import com.auth.demo.enums.SuccessMessage;
import com.auth.demo.exception.AppException;
import com.auth.demo.services.interfaces.ProjectService;

import jakarta.validation.Valid;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Create a new project
     * POST /api/v1/projects
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ProjectResponseDTO>> createProject(
            @RequestBody @Valid ProjectRequestDTO projectRequestDTO) {

        Long userId = getCurrentUserId();
        ProjectResponseDTO createdProject = projectService.createProject(projectRequestDTO, userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(createdProject, SuccessMessage.PROJECT_CREATED.getMessage()));
    }

    /**
     * Update an existing project
     * PUT /api/v1/projects/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponseDTO>> updateProject(
            @PathVariable UUID id,
            @RequestBody @Valid ProjectUpdateDTO projectUpdateDTO) {

        Long userId = getCurrentUserId();
        ProjectResponseDTO updatedProject = projectService.updateProject(id, projectUpdateDTO, userId);

        return ResponseEntity.ok(
                new ApiResponse<>(updatedProject, SuccessMessage.PROJECT_UPDATED.getMessage()));
    }

    /**
     * Soft delete a project
     * DELETE /api/v1/projects/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable UUID id) {

        Long userId = getCurrentUserId();
        projectService.deleteProject(id, userId);

        return ResponseEntity.ok(
                new ApiResponse<>(null, SuccessMessage.PROJECT_DELETED.getMessage()));
    }

    /**
     * Get project by ID
     * GET /api/v1/projects/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponseDTO>> getProjectById(@PathVariable UUID id) {

        ProjectResponseDTO project = projectService.getProjectById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(project, SuccessMessage.PROJECT_RETRIEVED.getMessage()));
    }

    /**
     * Get all projects
     * GET /api/v1/projects
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectResponseDTO>>> getAllProjects() {

        List<ProjectResponseDTO> projects = projectService.getAllProjects();

        return ResponseEntity.ok(
                new ApiResponse<>(projects, SuccessMessage.PROJECTS_RETRIEVED.getMessage()));
    }

    /**
     * Search projects by name
     * GET /api/v1/projects/search?name=
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ProjectResponseDTO>>> searchProjects(
            @RequestParam String name) {

        List<ProjectResponseDTO> projects = projectService.searchByName(name);

        return ResponseEntity.ok(
                new ApiResponse<>(projects, SuccessMessage.SEARCH_SUCCESSFUL.getMessage()));
    }

    /**
     * Helper method to get current user ID from security context
     */
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw AppException.unauthorized("User not authenticated");
        }
        // Note: This assumes you have a custom Authentication object with userId
        // Adjust based on your actual security implementation
        try {
            return (Long) authentication.getPrincipal();
        } catch (Exception e) {
            throw AppException.unauthorized("Unable to extract user ID from token");
        }
    }
}
