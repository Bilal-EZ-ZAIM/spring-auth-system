package com.auth.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auth.demo.Entity.UserRole;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    /**
     * Find all active roles for a user
     *
     * @param userId the ID of the user
     * @return List of active UserRole records
     */
    List<UserRole> findByUserIdAndIsActiveTrue(Long userId);

    /**
     * Find all roles (active and inactive) for a user
     *
     * @param userId the ID of the user
     * @return List of all UserRole records for the user
     */
    List<UserRole> findByUserId(Long userId);

    /**
     * Check if a user already has a specific role (active or inactive)
     *
     * @param userId the ID of the user
     * @param roleId the ID of the role
     * @return true if assignment exists, false otherwise
     */
    boolean existsByUserIdAndRoleId(Long userId, Long roleId);

    /**
     * Find an active role assignment between a user and role
     *
     * @param userId the ID of the user
     * @param roleId the ID of the role
     * @return Optional containing the active UserRole if found
     */
    Optional<UserRole> findByUserIdAndRoleIdAndIsActiveTrue(Long userId, Long roleId);

    /**
     * Check if a user has a specific active role
     *
     * @param userId the ID of the user
     * @param roleId the ID of the role
     * @return true if user has this active role, false otherwise
     */
    boolean existsByUserIdAndRoleIdAndIsActiveTrue(Long userId, Long roleId);

    /**
     * Find a UserRole record by ID
     *
     * @param id the ID of the UserRole record
     * @return Optional containing the UserRole if found
     */
    Optional<UserRole> findById(Long id);

    /**
     * Count active roles for a user
     *
     * @param userId the ID of the user
     * @return count of active roles
     */
    int countByUserIdAndIsActiveTrue(Long userId);

    /**
     * Find the most recent active role for a user
     *
     * @param userId the ID of the user
     * @return Optional containing the most recent active role
     */
    Optional<UserRole> findTopByUser_IdAndIsActiveTrueOrderByCreatedAtDesc(Long userId);
}
