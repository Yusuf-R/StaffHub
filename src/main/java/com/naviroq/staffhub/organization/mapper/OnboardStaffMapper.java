package com.naviroq.staffhub.organization.mapper;

import com.naviroq.staffhub.organization.domain.employee.OnboardStaffCommand;
import com.naviroq.staffhub.organization.domain.employee.dto.OnboardStaffRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OnboardStaffMapper {

    @Mappings({
            // ---- Employee Fields ----
            @Mapping(target = "employeeCommand.employeeCode", source = "employeeCode"),
            @Mapping(target = "employeeCommand.firstName", source = "firstName"),
            @Mapping(target = "employeeCommand.lastName", source = "lastName"),
            @Mapping(target = "employeeCommand.phone", source = "phone"),
            @Mapping(target = "employeeCommand.address", source = "address"),
            @Mapping(target = "employeeCommand.gender", source = "gender"),
            @Mapping(target = "employeeCommand.employmentType", source = "employmentType"),
            @Mapping(target = "employeeCommand.dateOfBirth", source = "dateOfBirth"),
            @Mapping(target = "employeeCommand.hireDate", source = "hireDate"),
            @Mapping(target = "employeeCommand.bio", source = "bio"),
            @Mapping(target = "employeeCommand.profilePictureUrl", source = "profilePictureUrl"),
            @Mapping(target = "employeeCommand.status", source = "status"),
            @Mapping(target = "employeeCommand.departmentId", source = "departmentId"),
            @Mapping(target = "employeeCommand.positionId", source = "positionId"),
            @Mapping(target = "employeeCommand.managerId", source = "managerId"),

            // ---- User Fields ----
            @Mapping(target = "userCommand.username", source = "username"),
            @Mapping(target = "userCommand.email", source = "email"),
            @Mapping(target = "userCommand.password", source = "password"),
            @Mapping(target = "userCommand.roleCode", source = "roleCode"),

            // CRITICAL: We ignore employeeId here because we don't have it yet.
            // The service will set this after creating the Employee.
            @Mapping(target = "userCommand.employeeId", ignore = true)
    })
    OnboardStaffCommand toCommand(OnboardStaffRequest request);
}