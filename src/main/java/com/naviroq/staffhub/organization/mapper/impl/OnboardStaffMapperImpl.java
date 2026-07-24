package com.naviroq.staffhub.organization.mapper.impl;

import com.naviroq.staffhub.organization.domain.employee.OnboardStaffCommand;
import com.naviroq.staffhub.organization.domain.employee.dto.OnboardStaffRequest;
import com.naviroq.staffhub.organization.domain.employee.dto.OnboardStaffResponseDto;
import com.naviroq.staffhub.organization.domain.entity.Employee;
import com.naviroq.staffhub.organization.mapper.OnboardStaffMapper;
import org.springframework.stereotype.Component;

@Component
public class OnboardStaffMapperImpl implements OnboardStaffMapper {

    @Override
    public OnboardStaffCommand createOnboardStaff(OnboardStaffRequest dto) {
        return new OnboardStaffCommand(
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
                dto.managerId(),
                // User fields
                dto.username(),
                dto.email(),
                dto.roleCode(),
                dto.password()
        );
    }

    @Override
    public OnboardStaffResponseDto toDto(Employee employee) {
        if (employee == null) {
            return null;
        }

        // You need to decide what goes into OnboardStaffResponseDto
        return new OnboardStaffResponseDto(
                employee.getId(),
                employee.getEmployeeCode(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getDepartment() != null ? employee.getDepartment().getName() : null,
                employee.getPosition() != null ? employee.getPosition().getTitle() : null,
                employee.getStatus(),
                null  // UserRefDto - you'll need to get this from employee.getUser()
        );
    }
}