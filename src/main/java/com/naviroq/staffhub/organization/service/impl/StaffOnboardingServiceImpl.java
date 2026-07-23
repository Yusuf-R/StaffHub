package com.naviroq.staffhub.organization.service.impl;

import com.naviroq.staffhub.identity.domain.CreateUserCommand;
import com.naviroq.staffhub.identity.domain.entity.User;
import com.naviroq.staffhub.identity.service.UserService;
import com.naviroq.staffhub.organization.domain.employee.OnboardStaffCommand;
import com.naviroq.staffhub.organization.domain.entity.Employee;
import com.naviroq.staffhub.organization.service.EmployeeService;
import com.naviroq.staffhub.organization.service.StaffOnboardingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StaffOnboardingServiceImpl implements StaffOnboardingService {

    private final EmployeeService employeeService;
    private final UserService userService;

    @Override
    @Transactional // it either 100 % success or roll back completely during failure
    public User onboardStaff(OnboardStaffCommand command) {
        log.info("🚀 Onboarding staff with employee code: {}",
                command.employeeCommand().employeeCode());

        // 1. Create the Employee profile
        Employee employee = employeeService.createEmployee(command.employeeCommand());
        log.info("✅ Employee created with ID: {}", employee.getId());

        // 2. Create the User. We override the employeeId with the newly created Employee's ID.
        CreateUserCommand userCommand = command.userCommand();
        CreateUserCommand userWithEmployeeId = new CreateUserCommand(
                employee.getId(),
                userCommand.email(),
                userCommand.password(),
                userCommand.roleCode(),
                userCommand.username()  // 👈 Here is where we inject the new Employee ID
        );

        User user = userService.createUser(userWithEmployeeId);
        log.info("✅ User created for employee: {}", employee.getEmployeeCode());

        return user;
    }
}