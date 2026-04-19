package com.auth.demo.Entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends AuditEntity {

    @Column(nullable = false, unique = true, length = 20)
    private String name;

    private boolean isActive = true;

    private String description;
}