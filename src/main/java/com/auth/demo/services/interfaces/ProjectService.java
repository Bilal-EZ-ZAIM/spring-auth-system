package com.auth.demo.services.interfaces;

import java.util.List;
import java.util.UUID;

import com.auth.demo.dto.project.ProjectRequestDTO;
import com.auth.demo.dto.project.ProjectResponseDTO;
import com.auth.demo.dto.project.ProjectUpdateDTO;

public interface ProjectService {

    ProjectResponseDTO createProject(ProjectRequestDTO projectRequestDTO, Long userId);

    ProjectResponseDTO updateProject(UUID publicId, ProjectUpdateDTO projectUpdateDTO, Long userId);

    void deleteProject(UUID publicId, Long userId);

    ProjectResponseDTO getProjectById(UUID publicId);

    List<ProjectResponseDTO> getAllProjects();

    List<ProjectResponseDTO> searchByName(String name);
}
