package com.naviroq.staffhub.workflow.service.impl;

import com.naviroq.staffhub.common.exception.ResourceNotFoundException;
import com.naviroq.staffhub.identity.domain.entity.User;
import com.naviroq.staffhub.identity.security.AuthenticationFacade;
import com.naviroq.staffhub.organization.domain.entity.Employee;
import com.naviroq.staffhub.organization.repository.EmployeeRepository;
import com.naviroq.staffhub.workflow.domain.CreateOnboardingWorkflowCommand;
import com.naviroq.staffhub.workflow.domain.CreateWorkflowRequestCommand;
import com.naviroq.staffhub.workflow.domain.dto.WorkflowRequestResponseDto;
import com.naviroq.staffhub.workflow.domain.entity.WorkflowRequest;
import com.naviroq.staffhub.workflow.domain.enums.ApprovalLevel;
import com.naviroq.staffhub.workflow.domain.enums.WorkflowStatus;
import com.naviroq.staffhub.workflow.domain.enums.WorkflowType;
import com.naviroq.staffhub.workflow.repository.WorkflowRepository;
import com.naviroq.staffhub.workflow.service.WorkflowService;
import com.naviroq.staffhub.workflow.util.WorkflowRequestNumberGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WorkflowServiceImpl implements WorkflowService {

    private final WorkflowRepository workflowRepository;
    private final EmployeeRepository employeeRepository;
    private final WorkflowRequestNumberGenerator requestNumberGenerator;
    private final AuthenticationFacade authenticationFacade;
    private final ObjectMapper objectMapper;


    @Override
    public WorkflowRequestResponseDto submitOnboarding(
            CreateOnboardingWorkflowCommand command
    ) {

        User requester = authenticationFacade.getCurrentUser();

        Employee employee = requester.getEmployee();

        String requestNumber =
                requestNumberGenerator.generate(employee);

        employee.setWorkflowRequestCount(
                employee.getWorkflowRequestCount() + 1
        );

        employeeRepository.save(employee);

        WorkflowRequest workflowRequest =
                WorkflowRequest.builder()
                        .requestNumber(requestNumber)
                        .type(WorkflowType.ONBOARD_EMPLOYEE)
                        .status(WorkflowStatus.PENDING)
                        .level(ApprovalLevel.ADMIN)
                        .requestedBy(employee)
                        .payload(objectMapper.valueToTree(command))
                        .build();

        WorkflowRequest saved =
                workflowRepository.save(workflowRequest);

        return new WorkflowRequestResponseDto(
                saved.getId(),
                saved.getRequestNumber(),
                saved.getType(),
                saved.getStatus(),
                "Onboarding request submitted successfully. Awaiting administrator approval."
        );

    }

    @Override
    public WorkflowRequestResponseDto approve(UUID workflowId) {
        return null;
    }

    @Override
    public WorkflowRequestResponseDto reject(UUID workflowId, String reason) {
        return null;
    }

    @Override
    public WorkflowRequestResponseDto cancel(UUID workflowId) {
        return null;
    }
}