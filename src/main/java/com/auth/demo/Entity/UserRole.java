package com.auth.demo.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_roles", indexes = {
        @Index(name = "idx_user_role_user_role_active", columnList = "user_id, role_id, is_active"),

        @Index(name = "idx_user_role_user_active", columnList = "user_id, is_active")
})
public class UserRole extends AuditEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(nullable = false)
    private boolean isActive = true;

    private Instant revokedAt;

    @Column(name = "assigned_by", nullable = false)
    private Long adminId;
}