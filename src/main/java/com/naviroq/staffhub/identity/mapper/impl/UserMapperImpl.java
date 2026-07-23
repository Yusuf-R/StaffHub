package com.naviroq.staffhub.identity.mapper.impl;

import com.naviroq.staffhub.identity.domain.CreateUserCommand;
import com.naviroq.staffhub.identity.domain.UpdateUserCommand;
import com.naviroq.staffhub.identity.domain.dto.CreateUserRequestDto;
import com.naviroq.staffhub.identity.domain.dto.EmployeeRefDto;
import com.naviroq.staffhub.identity.domain.dto.UpdateUserRequestDto;
import com.naviroq.staffhub.identity.domain.dto.UserResponseDto;
import com.naviroq.staffhub.identity.domain.entity.User;
import com.naviroq.staffhub.identity.mapper.UserMapper;
import com.naviroq.staffhub.organization.domain.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public CreateUserCommand fromDto(CreateUserRequestDto dto) {
        return new CreateUserCommand(
                dto.employeeId(),
                dto.username(),
                dto.email(),
                dto.roleCode(),
                dto.password()
        );
    }

    @Override
    public UpdateUserCommand fromDto(UpdateUserRequestDto dto) {
        return new UpdateUserCommand(dto.username(), dto.roleCode(), dto.password());
    }

    @Override
    public UserResponseDto toDto(User user) {
        Employee emp = user.getEmployee();

        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getStatus(),
                new EmployeeRefDto(
                        emp.getId(),
                        emp.getEmployeeCode(),
                        emp.getFirstName() + " " + emp.getLastName()
                )
        );
    }
}
