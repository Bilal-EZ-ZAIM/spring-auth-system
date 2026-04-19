package com.auth.demo.services.impl;

import org.springframework.stereotype.Service;

import com.auth.demo.Entity.Role;
import com.auth.demo.dto.role.RoleRequestDTO;
import com.auth.demo.dto.role.RoleResponseDTO;
import com.auth.demo.enums.ErrorMessage;
import com.auth.demo.exception.AppException;
import com.auth.demo.repository.RoleRepository;
import com.auth.demo.services.interfaces.RoleService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRoleServiceImpl userRoleService;

    public RoleServiceImpl(RoleRepository roleRepository, UserRoleServiceImpl userRoleService) {
        this.roleRepository = roleRepository;
        this.userRoleService = userRoleService;
    }

    @Override
    public RoleResponseDTO createRole(RoleRequestDTO roleRequest) {
        // Validate that role name doesn't already exist
        if (roleRepository.existsByName(roleRequest.name().trim())) {
            throw AppException.conflict(
                    ErrorMessage.ROLE_ALREADY_EXISTS.getMessage(),
                    "name");
        }

        // Create new role
        Role role = new Role();
        role.setName(roleRequest.name().trim());
        role.setDescription(roleRequest.description());

        Role savedRole = roleRepository.save(role);
        return mapToResponseDTO(savedRole);
    }

    @Override
    public List<RoleResponseDTO> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoleResponseDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> AppException.notFound(
                        ErrorMessage.ROLE_NOT_FOUND.getMessage()));
        return mapToResponseDTO(role);
    }

    @Override
    public RoleResponseDTO updateRole(Long id, RoleRequestDTO roleRequest) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> AppException.notFound(
                        ErrorMessage.ROLE_NOT_FOUND.getMessage()));

        if (!role.getName().equalsIgnoreCase(roleRequest.name().trim()) &&
                roleRepository.existsByName(roleRequest.name().trim())) {
            throw AppException.conflict(
                    ErrorMessage.ROLE_ALREADY_EXISTS.getMessage(),
                    "name");
        }

        role.setName(roleRequest.name().trim());
        role.setDescription(roleRequest.description().trim());

        Role updatedRole = roleRepository.save(role);
        return mapToResponseDTO(updatedRole);
    }

    @Override
    public void deactivateRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> AppException.notFound(
                        ErrorMessage.ROLE_NOT_FOUND.getMessage()));
        role.setActive(false);
        roleRepository.save(role);
    }

    @Override
    public void activateRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> AppException.notFound(
                        ErrorMessage.ROLE_NOT_FOUND.getMessage()));
        role.setActive(true);
        roleRepository.save(role);
    }

    @Override
    public String getActiveRoleName(Long userId) {

        String roleName = userRoleService.getUserActiveRoleName(userId);

        if (!roleRepository.existsByNameAndIsActiveTrue(roleName)) {
            throw AppException.badRequest(ErrorMessage.ROLE_NOT_ACTIVE.getMessage());
        }

        return roleName;
    }

    private RoleResponseDTO mapToResponseDTO(Role role) {
        return new RoleResponseDTO(
                role.getId(),
                role.getName(),
                role.getDescription(),
                role.getCreatedAt(),
                role.getUpdatedAt());
    }
}
