
package com.naviroq.staffhub.organization.domain.employee;

import com.naviroq.staffhub.identity.domain.CreateUserCommand;

public record OnboardStaffCommand(
        CreateEmployeeCommand employeeCommand,
        CreateUserCommand userCommand
) {}