package com.naviroq.staffhub.organization.mapper;

import com.naviroq.staffhub.organization.domain.employee.OnboardStaffCommand;
import com.naviroq.staffhub.organization.domain.employee.dto.OnboardStaffRequest;
import com.naviroq.staffhub.organization.domain.employee.dto.OnboardStaffResponseDto;
import com.naviroq.staffhub.organization.domain.entity.Employee;


public interface OnboardStaffMapper {
    OnboardStaffCommand createOnboardStaff(OnboardStaffRequest dto);
    OnboardStaffResponseDto toDto (Employee employee);
}
