package com.naviroq.staffhub.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneOffset; // Optional: for UTC consistency
import java.util.UUID;

/**
 * Base entity class for all database tables in StaffHub.
 *
 * <p><b>Purpose:</b> Centralizes common fields (ID, created/updated timestamps)
 * so that every entity (Employee, Department, LeaveRequest, etc.) inherits them.
 * This enforces consistency and reduces code duplication across the entire project.</p>
 *
 * <p><b>Why MappedSuperclass?</b> This class is NOT a database table itself.
 * Instead, its fields are copied into the child entity tables (e.g., employees,
 * departments). Each child table will have its own 'id', 'created_at', and 'updated_at' columns.</p>
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    /**
     * Primary key for the entity.
     *
     * <p><b>Why UUID?</b> Unlike auto-incrementing Longs, UUIDs are globally unique
     * and harder to guess. This is great for security (prevents IDOR attacks where
     * someone guesses /employees/1, /employees/2, etc.) and works well in
     * distributed systems.</p>
     *
     * <p><b>GenerationType.UUID:</b> Hibernate 6+ will automatically generate a
     * random UUID using RFC 4122 standard (version 4).</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    /**
     * Timestamp when this record was first created.
     *
     * <p><b>Key constraints:</b></p>
     * <ul>
     *   <li><b>nullable = false</b> → Database enforces that this must always have a value.</li>
     *   <li><b>updatable = false</b> → Hibernate will NEVER include this column in UPDATE SQL statements.
     *       This ensures the creation date cannot be accidentally overwritten.</li>
     * </ul>
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp when this record was last updated.
     *
     * <p><b>Note:</b> This field IS updatable. It gets automatically refreshed
     * every time the entity is saved with changes.</p>
     *
     * <p><b>Database best practice:</b> This is invaluable for debugging,
     * auditing, and showing users when something was last modified (e.g.,
     * "Sarah updated her profile 2 hours ago").</p>
     */
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * JPA lifecycle callback that runs RIGHT BEFORE the entity is inserted
     * into the database for the very first time.
     *
     * <p><b>When does this execute?</b> When you call {@code repository.save()}
     * on a NEW entity that doesn't yet exist in the database (i.e., the ID is null).</p>
     *
     * <p><b>What does it do?</b></p>
     * <ul>
     *   <li>Sets {@code createdAt} to the current server time.</li>
     *   <li>Sets {@code updatedAt} to the same current time (because at the moment
     *       of creation, the record is considered "up-to-date").</li>
     * </ul>
     *
     * <p><b>Benefit:</b> You NEVER have to manually write {@code employee.setCreatedAt(LocalDateTime.now())}
     * in your Service layer. Hibernate handles it automatically!</p>
     *
     */
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now(); // Or use: LocalDateTime.now(ZoneOffset.UTC)
        createdAt = now;
        updatedAt = now;
    }

    /**
     * JPA lifecycle callback that runs RIGHT BEFORE the entity is updated
     * in the database.
     *
     * <p><b>When does this execute?</b> When you call {@code repository.save()}
     * on an EXISTING entity (i.e., the ID is NOT null) AND Hibernate detects
     * that at least one field has actually changed.</p>
     *
     * <p><b>⚠️ IMPORTANT GOTCHA:</b> This method will NOT fire if you call
     * {@code save()} but didn't change any data. Hibernate is smart enough
     * to skip the UPDATE query entirely, which means this callback won't run.
     * This is actually a GOOD thing – it prevents unnecessary database writes
     * and keeps your logs clean.</p>
     *
     * <p><b>What does it do?</b></p>
     * <ul>
     *   <li>Updates {@code updatedAt} to the current time.</li>
     *   <li><b>Does NOT touch {@code createdAt}</b> – this ensures the creation
     *       timestamp remains immutable, preserving the record's history.</li>
     * </ul>
     *
     * <p><b>Example in StaffHub:</b> When Sarah updates her phone number via
     * {@code PUT /api/employees/{id}}, this method automatically fires and
     * updates the 'updated_at' column. When you fetch her profile later, the
     * API can show "Last modified: 2 minutes ago".</p>
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now(); // Or use: LocalDateTime.now(ZoneOffset.UTC)
    }
}