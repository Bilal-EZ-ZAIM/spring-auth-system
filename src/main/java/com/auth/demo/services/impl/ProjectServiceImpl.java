package com.auth.demo.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.auth.demo.Entity.Project;
import com.auth.demo.Entity.User;
import com.auth.demo.dto.project.ProjectRequestDTO;
import com.auth.demo.dto.project.ProjectResponseDTO;
import com.auth.demo.dto.project.ProjectUpdateDTO;
import com.auth.demo.enums.ErrorMessage;
import com.auth.demo.exception.AppException;
import com.auth.demo.repository.ProjectRepository;
import com.auth.demo.repository.UserRepository;
import com.auth.demo.services.interfaces.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ProjectResponseDTO createProject(ProjectRequestDTO projectRequestDTO, Long userId) {

        // Validate admin user exists
        User admin = userRepository.findById(userId)
                .orElseThrow(() -> AppException.notFound(ErrorMessage.USER_NOT_FOUND.getMessage()));

        // Check if project name already exists
        if (projectRepository.existsByName(projectRequestDTO.name())) {
            throw AppException.conflict(
                    ErrorMessage.PROJECT_NAME_ALREADY_EXISTS.getMessage(),
                    "name");
        }

        // Create new project
        Project project = new Project();
        project.setName(projectRequestDTO.name());
        project.setDescription(projectRequestDTO.description());
        project.setCreatedBy(admin);

        Project savedProject = projectRepository.save(project);

        return mapToResponseDTO(savedProject);
    }

    @Override
    public ProjectResponseDTO updateProject(UUID publicId, ProjectUpdateDTO projectUpdateDTO, Long userId) {

        Project project = projectRepository.findByPublicId(publicId)
                .orElseThrow(() -> AppException.notFound(ErrorMessage.PROJECT_NOT_FOUND.getMessage()));

        // Verify authorization (admin only)
        if (!project.getCreatedBy().getId().equals(userId)) {
            throw AppException.unauthorized(ErrorMessage.PROJECT_UPDATE_UNAUTHORIZED.getMessage());
        }

        // Check if new name already exists (and it's different from current name)
        if (projectUpdateDTO.name() != null &&
                !projectUpdateDTO.name().equals(project.getName()) &&
                projectRepository.existsByName(projectUpdateDTO.name())) {
            throw AppException.conflict(
                    ErrorMessage.PROJECT_NAME_ALREADY_EXISTS.getMessage(),
                    "name");
        }

        // Update fields
        if (projectUpdateDTO.name() != null) {
            project.setName(projectUpdateDTO.name());
        }
        if (projectUpdateDTO.description() != null) {
            project.setDescription(projectUpdateDTO.description());
        }

        Project updatedProject = projectRepository.save(project);

        return mapToResponseDTO(updatedProject);
    }

    @Override
    public void deleteProject(UUID publicId, Long userId) {

        Project project = projectRepository.findByPublicId(publicId)
                .orElseThrow(() -> AppException.notFound(ErrorMessage.PROJECT_NOT_FOUND.getMessage()));

        // Verify authorization (admin only)
        if (!project.getCreatedBy().getId().equals(userId)) {
            throw AppException.unauthorized(ErrorMessage.PROJECT_DELETE_UNAUTHORIZED.getMessage());
        }

        // Soft delete
        project.setDeleted(true);
        projectRepository.save(project);
    }

    @Override
    public ProjectResponseDTO getProjectById(UUID publicId) {

        Project project = projectRepository.findByPublicId(publicId)
                .orElseThrow(() -> AppException.notFound(ErrorMessage.PROJECT_NOT_FOUND.getMessage()));

        if (project.isDeleted()) {
            throw AppException.notFound(ErrorMessage.PROJECT_NOT_FOUND.getMessage());
        }

        return mapToResponseDTO(project);
    }

    @Override
    public List<ProjectResponseDTO> getAllProjects() {

        List<Project> projects = projectRepository.findByIsDeletedFalse();
        return projects.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    public List<ProjectResponseDTO> searchByName(String name) {

        if (name == null || name.isBlank()) {
            throw AppException.badRequest(ErrorMessage.SEARCH_NAME_CANNOT_BE_EMPTY.getMessage());
        }

        List<Project> projects = projectRepository.findByIsDeletedFalseAndNameContainingIgnoreCase(name);
        return projects.stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    private ProjectResponseDTO mapToResponseDTO(Project project) {
        return new ProjectResponseDTO(
                project.getPublicId(),
                project.getName(),
                project.getDescription(),
                project.getCreatedBy().getId(),
                project.getCreatedAt(),
                project.getUpdatedAt());
    }
}
