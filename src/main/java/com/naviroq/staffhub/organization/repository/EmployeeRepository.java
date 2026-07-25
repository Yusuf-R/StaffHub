package com.naviroq.staffhub.organization.repository;

import com.naviroq.staffhub.common.enums.EmploymentStatus;
import com.naviroq.staffhub.organization.domain.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    // ---------- DERIVED QUERIES (Spring Data JPA auto-implements) ----------
    List<Employee> findByDepartment_Name(String departmentName);
    List<Employee> findByStatus(EmploymentStatus status);
    List<Employee> findByDepartment_NameAndStatus(String departmentName, EmploymentStatus status);
    boolean existsByEmployeeCode(String employeeCode);
    Optional<Employee> findByEmployeeCode(String employeeCode);

    // ---------- NEW: JOIN FETCH METHODS (Pre-load department, position, user) ----------

    @Query("SELECT e FROM Employee e " +
            "LEFT JOIN FETCH e.department " +
            "LEFT JOIN FETCH e.position " +
            "LEFT JOIN FETCH e.user " +
            "WHERE e.id = :id")
    Optional<Employee> findByIdWithDetails(UUID id);

    @Query("SELECT e FROM Employee e " +
            "LEFT JOIN FETCH e.department " +
            "LEFT JOIN FETCH e.position " +
            "LEFT JOIN FETCH e.user")
    List<Employee> findAllWithDetails();

    @Query("SELECT e FROM Employee e " +
            "LEFT JOIN FETCH e.department " +
            "LEFT JOIN FETCH e.position " +
            "LEFT JOIN FETCH e.user " +
            "WHERE LOWER(e.department.name) = LOWER(:departmentName)")
    List<Employee> findByDepartmentWithDetails(String departmentName);

    @Query("SELECT e FROM Employee e " +
            "LEFT JOIN FETCH e.department " +
            "LEFT JOIN FETCH e.position " +
            "LEFT JOIN FETCH e.user " +
            "WHERE e.status = :status")
    List<Employee> findByStatusWithDetails(EmploymentStatus status);

    @Query("SELECT e FROM Employee e " +
            "LEFT JOIN FETCH e.department " +
            "LEFT JOIN FETCH e.position " +
            "LEFT JOIN FETCH e.user " +
            "WHERE LOWER(e.department.name) = LOWER(:departmentName) AND e.status = :status")
    List<Employee> findByDepartmentAndStatusWithDetails(String departmentName, EmploymentStatus status);

    // ---------- PAGINATION (For later) ----------
    @Query("SELECT e FROM Employee e " +
            "LEFT JOIN FETCH e.department " +
            "LEFT JOIN FETCH e.position " +
            "LEFT JOIN FETCH e.user")
    Page<Employee> findAllWithDetails(Pageable pageable);
}