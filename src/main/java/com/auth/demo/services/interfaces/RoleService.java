package com.auth.demo.services.interfaces;

import java.util.List;

import com.auth.demo.dto.role.RoleRequestDTO;
import com.auth.demo.dto.role.RoleResponseDTO;
import com.auth.demo.exception.AppException;

public interface RoleService {

    /**
     * Create a new role
     *
     * @param roleRequest the role request DTO
     * @return the created role response DTO
     * @throws AppException if role name already exists
     */
    RoleResponseDTO createRole(RoleRequestDTO roleRequest);

    /**
     * Retrieve all roles
     *
     * @return list of all roles
     */
    List<RoleResponseDTO> getAllRoles();

    /**
     * Retrieve a role by ID
     *
     * @param id the role ID
     * @return the role response DTO
     * @throws AppException if role not found
     */
    RoleResponseDTO getRoleById(Long id);

    /**
     * Update an existing role
     *
     * @param id          the role ID
     * @param roleRequest the role request DTO
     * @return the updated role response DTO
     * @throws AppException if role not found or name already exists
     */
    RoleResponseDTO updateRole(Long id, RoleRequestDTO roleRequest);

    /**
     * Deactivate a role
     *
     * @param id the role ID
     * @return ResponseEntity with success message
     */
    void deactivateRole(Long id);

    /**
     * Activate a role
     *
     * @param id the role ID
     * @return ResponseEntity with success message
     */
    void activateRole(Long id);

    String getActiveRoleName(Long userId);

}
