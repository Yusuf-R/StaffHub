package com.naviroq.staffhub.organization.domain.entity;

import com.naviroq.staffhub.common.entity.BaseEntity;
import com.naviroq.staffhub.common.enums.EmploymentStatus;
import com.naviroq.staffhub.common.enums.EmploymentType;
import com.naviroq.staffhub.common.enums.Gender;
import com.naviroq.staffhub.identity.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees", schema = "staff_hub")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee extends BaseEntity {

    @Column(name = "employee_code", nullable = false, unique = true, length = 64)
    private String employeeCode;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(length = 20)
    private String phone;

    @Column(length = 255)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmploymentType employmentType;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmploymentStatus status;

    // ---------- SOFT DELETE FIELDS ----------
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;       // null = active, not-null = deleted

    @Column(name = "deletion_reason", length = 500)
    private String deletionReason;         // Why they were deleted

    // ---------- RELATIONSHIPS ----------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @OneToOne(mappedBy = "employee")
    private User user;

    // ---------- HELPER METHODS ----------
    public void softDelete(String reason) {
        this.deletedAt = LocalDateTime.now();
        this.deletionReason = reason;
        this.status = EmploymentStatus.TERMINATED;  // Also update the status
    }

    public void restore() {
        this.deletedAt = null;
        this.deletionReason = null;
        this.status = EmploymentStatus.ACTIVE;  // Restore to active status
    }

    public boolean isDeleted() {
        return deletedAt != null;
    }
}