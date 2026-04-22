package com.auth.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.demo.Entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByPublicId(UUID publicId);

    boolean existsByName(String name);

    List<Project> findByIsDeletedFalseAndNameContainingIgnoreCase(String name);

    List<Project> findByIsDeletedFalse();
}