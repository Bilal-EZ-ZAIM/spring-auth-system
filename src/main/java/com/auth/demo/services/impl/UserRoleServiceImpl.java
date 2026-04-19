package com.auth.demo.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth.demo.Entity.Role;
import com.auth.demo.Entity.User;
import com.auth.demo.Entity.UserRole;
import com.auth.demo.dto.UserRoleRequestDTO;
import com.auth.demo.dto.UserRoleResponseDTO;
import com.auth.demo.enums.ErrorMessage;
import com.auth.demo.exception.AppException;
import com.auth.demo.repository.RoleRepository;
import com.auth.demo.repository.UserRepository;
import com.auth.demo.repository.UserRoleRepository;
import com.auth.demo.services.interfaces.UserRoleService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository,
            UserRepository userRepository,
            RoleRepository roleRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void assignRoleToUser(UserRoleRequestDTO userRoleRequest) {

        // Validate user exists
        User user = userRepository.findByPublicId(userRoleRequest.getUserId())
                .orElseThrow(() -> AppException.notFound(
                        ErrorMessage.USER_NOT_FOUND.getMessage()));

        // Validate role exists
        Role role = roleRepository.findById(userRoleRequest.getRoleId())
                .orElseThrow(() -> AppException.notFound(
                        ErrorMessage.ROLE_NOT_FOUND.getMessage()));

        // Check if user already has this role (active)
        boolean alreadyActive = userRoleRepository
                .existsByUserIdAndRoleIdAndIsActiveTrue(user.getId(), role.getId());

        if (alreadyActive) {
            throw AppException.conflict(
                    ErrorMessage.USER_ALREADY_HAS_ROLE.getMessage(),
                    "roleId");
        }


        // Create new role assignment
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        userRole.setAdminId(userRoleRequest.getAdminId());
        userRole.setActive(true);

        userRoleRepository.save(userRole);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserRoleResponseDTO> getUserRoles(Long userId) {
        // Validate user exists
        if (!userRepository.existsById(userId)) {
            throw AppException.notFound(
                    ErrorMessage.USER_NOT_FOUND.getMessage());
        }

        return userRoleRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserRoleResponseDTO> getActiveRolesByUser(Long userId) {
        // Validate user exists
        if (!userRepository.existsById(userId)) {
            throw AppException.notFound(
                    ErrorMessage.USER_NOT_FOUND.getMessage());
        }

        return userRoleRepository.findByUserIdAndIsActiveTrue(userId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void revokeRole(Long userRoleId, Long adminId) {
        UserRole userRole = userRoleRepository.findById(userRoleId)
                .orElseThrow(() -> AppException.notFound(
                        ErrorMessage.ROLE_NOT_FOUND.getMessage()));

        // Mark as inactive and record revocation
        userRole.setActive(false);
        userRole.setRevokedAt(Instant.now());
        userRole.setAdminId(adminId);

        userRoleRepository.save(userRole);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean userHasActiveRole(Long userId, Long roleId) {
        return userRoleRepository.existsByUserIdAndRoleIdAndIsActiveTrue(userId, roleId);
    }

    @Override
    @Transactional(readOnly = true)
    public UserRoleResponseDTO getUserRoleById(Long userRoleId) {
        UserRole userRole = userRoleRepository.findById(userRoleId)
                .orElseThrow(() -> AppException.notFound(
                        ErrorMessage.ROLE_NOT_FOUND.getMessage()));
        return mapToResponseDTO(userRole);
    }

    @Override
    public String getUserActiveRoleName(Long userId) {
        UserRole userRole = userRoleRepository.findTopByUser_IdAndIsActiveTrueOrderByCreatedAtDesc(userId)
                .orElseThrow(() -> AppException.badRequest(ErrorMessage.NO_ACTIVE_ROLE_FOUND.getMessage()));
        return userRole.getRole().getName();
    }

    /**
     * Map UserRole entity to UserRoleResponseDTO
     */
    private UserRoleResponseDTO mapToResponseDTO(UserRole userRole) {
        UserRoleResponseDTO dto = new UserRoleResponseDTO();
        dto.setId(userRole.getId());
        dto.setUserId(userRole.getUser().getId());
        dto.setUserName(userRole.getUser().getFirstname() + " " + userRole.getUser().getLastname());
        dto.setRoleId(userRole.getRole().getId());
        dto.setRoleName(userRole.getRole().getName());
        dto.setActive(userRole.isActive());
        dto.setRevokedAt(userRole.getRevokedAt());
        dto.setAdminId(userRole.getAdminId());
        dto.setCreatedAt(userRole.getCreatedAt());
        dto.setUpdatedAt(userRole.getUpdatedAt());
        return dto;
    }
}
