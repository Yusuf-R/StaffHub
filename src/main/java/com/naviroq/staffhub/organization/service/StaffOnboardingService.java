package com.naviroq.staffhub.organization.service;

import com.naviroq.staffhub.identity.domain.entity.User;
import com.naviroq.staffhub.organization.domain.employee.OnboardStaffCommand;

public interface StaffOnboardingService {

    /**
     * Onboards a new staff member by creating both the Employee profile
     * and the User account in a single atomic transaction.
     *
     * @param command The combined Employee + User command
     * @return The created User entity (with the Employee attached)
     */
    User onboardStaff(OnboardStaffCommand command);
}