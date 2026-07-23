package com.naviroq.staffhub.identity.domain.entity;

import com.naviroq.staffhub.common.entity.BaseEntity;
import com.naviroq.staffhub.common.enums.RoleCode;
import com.naviroq.staffhub.common.enums.UserStatus;
import com.naviroq.staffhub.organization.domain.entity.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", schema = "staff_hub")
public class User extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleCode role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status = UserStatus.PENDING;

    private LocalDateTime lastLogin;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false, unique = true)
    private Employee employee;

}