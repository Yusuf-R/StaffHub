package com.naviroq.staffhub.workflow.util;

import com.naviroq.staffhub.organization.domain.entity.Employee;
import org.springframework.stereotype.Component;

import java.time.Year;

@Component
public class WorkflowRequestNumberGenerator {

    public String generate(Employee employee) {

        long nextRequestNumber = employee.getWorkflowRequestCount() + 1;

        return String.format(
                "REQ-%d-%s-%06d",
                Year.now().getValue(),
                employee.getEmployeeCode(),
                nextRequestNumber
        );
    }
}