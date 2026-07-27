package com.naviroq.staffhub.organization.mapper.impl;

import com.naviroq.staffhub.identity.domain.entity.User;
import com.naviroq.staffhub.organization.domain.employee.CreateEmployeeCommand;
import com.naviroq.staffhub.organization.domain.employee.UpdateEmployeeCommand;
import com.naviroq.staffhub.organization.domain.employee.dto.CreateEmployeeRequest;
import com.naviroq.staffhub.organization.domain.employee.dto.EmployeeResponseDto;
import com.naviroq.staffhub.organization.domain.employee.dto.UpdateEmployeeRequest;
import com.naviroq.staffhub.organization.domain.employee.dto.UserRefDto;
import com.naviroq.staffhub.organization.domain.entity.Employee;
import com.naviroq.staffhub.organization.mapper.EmployeeMapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapperImpl implements EmployeeMapper {
    @Override
    public CreateEmployeeCommand fromDto(CreateEmployeeRequest dto) {
        return new CreateEmployeeCommand(
                dto.firstName(),
                dto.lastName(),
                dto.gender(),
                dto.dateOfBirth(),
                dto.hireDate(),
                dto.phone(),
                dto.address(),
                dto.bio(),
                dto.profilePictureUrl(),
                dto.employmentType(),
                dto.status(),
                dto.departmentId(),
                dto.positionId(),
                dto.managerId()
        );
    }

    @Override
    public UpdateEmployeeCommand fromDto(UpdateEmployeeRequest dto) {
        return new UpdateEmployeeCommand(
                dto.firstName(),
                dto.lastName(),
                dto.gender(),
                dto.dateOfBirth(),
                dto.phone(),
                dto.address(),
                dto.bio(),
                dto.profilePictureUrl(),
                dto.status(),
                dto.employmentType(),
                dto.departmentId(),
                dto.positionId(),
                dto.managerId()
        );
    }

    @Override
    public EmployeeResponseDto toDto(Employee employee) {
        User user = employee.getUser();

        UserRefDto userRef = (user != null)
                ? new UserRefDto(user.getId(), user.getUsername(), user.getEmail(), user.getRole())
                : null;

        return new EmployeeResponseDto(
                // Basic info
                employee.getId(),
                employee.getEmployeeCode(),
                employee.getFirstName(),
                employee.getLastName(),

                // Contact & Personal
                employee.getBio(),
                employee.getDateOfBirth() != null ? employee.getDateOfBirth().toString() : null,
                employee.getPhone(),
                employee.getAddress(),
                employee.getProfilePictureUrl() != null ? employee.getProfilePictureUrl() : null,

                // Department & Position (names)
                employee.getDepartment() != null ? employee.getDepartment().getName() : null,
                employee.getPosition() != null ? employee.getPosition().getTitle() : null,

                // Employment & Status
                employee.getEmploymentType(),
                user != null ? user.getStatus() : null, employee.getStatus(),

                // User reference
                userRef,

                // IDs
                employee.getDepartment() != null ? employee.getDepartment().getId() : null,
                employee.getPosition() != null ? employee.getPosition().getId() : null
        );
    }
}
