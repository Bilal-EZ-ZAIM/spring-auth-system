package com.auth.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.auth.demo.apiResponces.ApiResponse;
import com.auth.demo.dto.UserRoleRequestDTO;
import com.auth.demo.dto.UserRoleResponseDTO;
import com.auth.demo.enums.SuccessMessage;
import com.auth.demo.services.interfaces.UserRoleService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/user-roles")
public class UserRoleController {

        private final UserRoleService userRoleService;

        public UserRoleController(UserRoleService userRoleService) {
                this.userRoleService = userRoleService;
        }

        /**
         * Assign a role to a user
         *
         * @param userRoleRequest the user role request DTO
         * @return ResponseEntity containing the assigned role
         */
        @PostMapping("/assign")
        public ResponseEntity<ApiResponse<Void>> assignRoleToUser(
                        @RequestBody @Valid UserRoleRequestDTO userRoleRequest) {

                userRoleService.assignRoleToUser(userRoleRequest);

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new ApiResponse<>(
                                                null,
                                                SuccessMessage.ROLE_ASSIGNED.getMessage()));
        }

        /**
         * Retrieve all roles (active and inactive) for a user
         *
         * @param userId the user ID
         * @return ResponseEntity containing list of user roles
         */
        @GetMapping("/user/{userId}")
        public ResponseEntity<ApiResponse<List<UserRoleResponseDTO>>> getUserRoles(
                        @PathVariable Long userId) {

                List<UserRoleResponseDTO> userRoles = userRoleService.getUserRoles(userId);

                return ResponseEntity.ok(new ApiResponse<>(
                                userRoles,
                                SuccessMessage.USER_ROLES_RETRIEVED.getMessage()));
        }

        /**
         * Retrieve only active roles for a user
         *
         * @param userId the user ID
         * @return ResponseEntity containing list of active user roles
         */
        @GetMapping("/user/{userId}/active")
        public ResponseEntity<ApiResponse<List<UserRoleResponseDTO>>> getActiveRolesByUser(
                        @PathVariable Long userId) {

                List<UserRoleResponseDTO> activeRoles = userRoleService.getActiveRolesByUser(userId);

                return ResponseEntity.ok(new ApiResponse<>(
                                activeRoles,
                                SuccessMessage.USER_ROLES_RETRIEVED.getMessage()));
        }

        /**
         * Revoke a role from a user
         *
         * @param userRoleId the user role ID
         * @param adminId    the ID of the admin revoking the role (query parameter)
         * @return ResponseEntity with success message
         */
        @PutMapping("/revoke/{userRoleId}")
        public ResponseEntity<ApiResponse<Void>> revokeRole(
                        @PathVariable Long userRoleId,
                        Authentication authentication) {

                Long adminId = (Long) authentication.getPrincipal();

                userRoleService.revokeRole(userRoleId, adminId);

                return ResponseEntity.ok(new ApiResponse<>(
                                null,
                                SuccessMessage.ROLE_REVOKED.getMessage()));
        }

        /**
         * Get a specific user role assignment
         *
         * @param userRoleId the user role ID
         * @return ResponseEntity containing the user role details
         */
        @GetMapping("/{userRoleId}")
        public ResponseEntity<ApiResponse<UserRoleResponseDTO>> getUserRoleById(
                        @PathVariable Long userRoleId) {

                UserRoleResponseDTO userRole = userRoleService.getUserRoleById(userRoleId);

                return ResponseEntity.ok(new ApiResponse<>(
                                userRole,
                                SuccessMessage.USER_ROLES_RETRIEVED.getMessage()));
        }
}
