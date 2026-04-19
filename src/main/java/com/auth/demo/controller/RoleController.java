package com.auth.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.auth.demo.apiResponces.ApiResponse;
import com.auth.demo.dto.role.RoleRequestDTO;
import com.auth.demo.dto.role.RoleResponseDTO;
import com.auth.demo.enums.SuccessMessage;
import com.auth.demo.services.interfaces.RoleService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/roles")
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {

        private final RoleService roleService;

        public RoleController(RoleService roleService) {
                this.roleService = roleService;
        }

        /**
         * Create a new role
         *
         * @param roleRequest the role request DTO
         * @return ResponseEntity containing the created role
         */
        @PostMapping
        public ResponseEntity<ApiResponse<RoleResponseDTO>> createRole(
                        @RequestBody @Valid RoleRequestDTO roleRequest) {

                RoleResponseDTO createdRole = roleService.createRole(roleRequest);

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new ApiResponse<>(
                                                createdRole,
                                                SuccessMessage.ROLE_CREATED.getMessage()));
        }

        /**
         * Retrieve all roles
         *
         * @return ResponseEntity containing list of all roles
         */
        @GetMapping
        public ResponseEntity<ApiResponse<List<RoleResponseDTO>>> getAllRoles() {

                List<RoleResponseDTO> roles = roleService.getAllRoles();

                return ResponseEntity.ok(new ApiResponse<>(
                                roles,
                                SuccessMessage.ROLES_RETRIEVED.getMessage()));
        }

        /**
         * Retrieve a role by ID
         *
         * @param id the role ID
         * @return ResponseEntity containing the role
         */
        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<RoleResponseDTO>> getRoleById(
                        @PathVariable Long id) {

                RoleResponseDTO role = roleService.getRoleById(id);

                return ResponseEntity.ok(new ApiResponse<>(
                                role,
                                SuccessMessage.ROLES_RETRIEVED.getMessage()));
        }

        /**
         * Update an existing role
         *
         * @param id          the role ID
         * @param roleRequest the role request DTO
         * @return ResponseEntity containing the updated role
         */
        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<RoleResponseDTO>> updateRole(
                        @PathVariable Long id,
                        @RequestBody @Valid RoleRequestDTO roleRequest) {

                RoleResponseDTO updatedRole = roleService.updateRole(id, roleRequest);

                return ResponseEntity.ok(new ApiResponse<>(
                                updatedRole,
                                SuccessMessage.ROLE_UPDATED.getMessage()));
        }



        /**
         * Activate a role
         *
         * @param id the role ID
         * @return ResponseEntity with success message
         */
        @PutMapping("/{id}/activate")
        public ResponseEntity<ApiResponse<Void>> activateRole(
                        @PathVariable Long id) {

                roleService.activateRole(id);

                return ResponseEntity.ok(new ApiResponse<>(
                                null,
                                SuccessMessage.ROLE_ACTIVATED.getMessage()));
        }

        /**
         * Deactivate a role
         *
         * @param id the role ID
         * @return ResponseEntity with success message
         */

        @PutMapping("/{id}/deactivate")
        public ResponseEntity<ApiResponse<Void>> deactivateRole(@PathVariable Long id) {

                roleService.deactivateRole(id);

                return ResponseEntity.ok(new ApiResponse<>(
                                null,
                                SuccessMessage.ROLE_DEACTIVATED.getMessage()));
        }

}
