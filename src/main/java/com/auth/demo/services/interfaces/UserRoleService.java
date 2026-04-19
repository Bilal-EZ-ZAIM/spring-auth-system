package com.auth.demo.services.interfaces;

import com.auth.demo.dto.UserRoleRequestDTO;
import com.auth.demo.dto.UserRoleResponseDTO;
import com.auth.demo.exception.AppException;

import java.util.List;

public interface UserRoleService {

    /**
     * Assign a role to a user
     *
     * @param userRoleRequest the user role request DTO containing userId, roleId,
     *                        and adminId
     * @return the created user role response DTO
     * @throws AppException if user or role not found, or user already has the role
     */
    void assignRoleToUser(UserRoleRequestDTO userRoleRequest);

    /**
     * Retrieve all roles for a user (active and inactive)
     *
     * @param userId the user ID
     * @return list of user role response DTOs
     * @throws AppException if user not found
     */
    List<UserRoleResponseDTO> getUserRoles(Long userId);

    /**
     * Retrieve only active roles for a user
     *
     * @param userId the user ID
     * @return list of active user role response DTOs
     * @throws AppException if user not found
     */
    List<UserRoleResponseDTO> getActiveRolesByUser(Long userId);

    /**
     * Revoke a role from a user (soft delete - mark as inactive)
     *
     * @param userRoleId the user role ID
     * @param adminId    the ID of the admin revoking the role
     * @throws AppException if user role not found
     */
    void revokeRole(Long userRoleId, Long adminId);

    /**
     * Check if a user has a specific active role
     *
     * @param userId the user ID
     * @param roleId the role ID
     * @return true if user has this active role, false otherwise
     */
    boolean userHasActiveRole(Long userId, Long roleId);

    /**
     * Get a user role by ID
     *
     * @param userRoleId the user role ID
     * @return the user role response DTO
     * @throws AppException if user role not found
     */
    UserRoleResponseDTO getUserRoleById(Long userRoleId);

    String getUserActiveRoleName(Long userId);
}
