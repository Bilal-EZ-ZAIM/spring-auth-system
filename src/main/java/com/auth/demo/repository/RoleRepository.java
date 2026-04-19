package com.auth.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.demo.Entity.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Find a role by its name
     *
     * @param name the name of the role
     * @return Optional containing the role if found
     */
    Optional<Role> findByName(String name);

    /**
     * Check if a role with the given name exists
     *
     * @param name the name of the role
     * @return true if role exists, false otherwise
     */
    boolean existsByName(String name);

    /**
     * check if a role is active
     */
    boolean existsByNameAndIsActiveTrue(String name);

}
