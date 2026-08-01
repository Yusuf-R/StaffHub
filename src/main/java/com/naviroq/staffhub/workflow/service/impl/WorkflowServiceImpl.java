package com.naviroq.staffhub.workflow.service.impl;

import com.naviroq.staffhub.common.enums.AuditAction;
import com.naviroq.staffhub.common.enums.AuditEntityType;
import com.naviroq.staffhub.common.enums.NotificationType;
import com.naviroq.staffhub.identity.security.AuthenticationFacade;
import com.naviroq.staffhub.organization.domain.entity.Employee;
import com.naviroq.staffhub.organization.repository.EmployeeRepository;
import com.naviroq.staffhub.platform.service.AuditLogService;
import com.naviroq.staffhub.platform.service.NotificationService;
import com.naviroq.staffhub.platform.snapshot.WorkflowSnapshotFactory;
import com.naviroq.staffhub.workflow.domain.CreateOnboardingWorkflowCommand;
import com.naviroq.staffhub.workflow.domain.dto.WorkflowRequestResponseDto;
import com.naviroq.staffhub.workflow.domain.entity.WorkflowRequest;
import com.naviroq.staffhub.workflow.domain.enums.ApprovalLevel;
import com.naviroq.staffhub.workflow.domain.enums.WorkflowStatus;
import com.naviroq.staffhub.workflow.domain.enums.WorkflowType;
import com.naviroq.staffhub.workflow.mapper.WorkflowMapper;
import com.naviroq.staffhub.workflow.repository.WorkflowRepository;
import com.naviroq.staffhub.workflow.service.WorkflowService;
import com.naviroq.staffhub.workflow.util.WorkflowRequestNumberGenerator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

@Service
@AllArgsConstructor
public class WorkflowServiceImpl implements WorkflowService {

    private final WorkflowRepository workflowRepository;
    private final EmployeeRepository employeeRepository;
    private final WorkflowRequestNumberGenerator requestNumberGenerator;
    private final AuthenticationFacade authenticationFacade;
    private final AuditLogService auditLogService;
    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;
    private final WorkflowMapper workflowMapper;
    private final WorkflowSnapshotFactory workflowSnapshotFactory;

    @Override
    @Transactional
    public WorkflowRequestResponseDto submitOnboarding(
            CreateOnboardingWorkflowCommand command
    ) {

        Employee requester = authenticationFacade.getCurrentEmployee();

        String requestNumber = requestNumberGenerator.generate(requester);

        requester.setWorkflowRequestCount(requester.getWorkflowRequestCount() + 1);

        employeeRepository.save(requester);

        WorkflowRequest workflowRequest =
                WorkflowRequest.builder()
                        .requestNumber(requestNumber)
                        .type(WorkflowType.ONBOARD_EMPLOYEE)
                        .status(WorkflowStatus.PENDING)
                        .level(ApprovalLevel.ADMIN)
                        .requestedBy(requester)
                        .payload(objectMapper.valueToTree(command))
                        .build();

        WorkflowRequest saved =
                workflowRepository.save(workflowRequest);

        /*
         * ==========================================================
         * AUDIT LOG
         * ==========================================================
         */

        auditLogService.saveAuditLog(
                AuditAction.CREATE,
                AuditEntityType.WORKFLOW,
                saved.getId(),
                authenticationFacade.getCurrentUserEmail(),
                null,
                workflowSnapshotFactory.create(saved),
                "HR submitted onboarding request.",
                null
        );

        /*
         * ==========================================================
         * NOTIFICATIONS
         * ==========================================================
         */
        notificationService.notifyWorkflowSubmission(
                requester,
                NotificationType.WORKFLOW,
                "Onboarding Request Submitted",
                "Employee onboarding request "
                        + requestNumber
                        + " has been submitted and is awaiting approval.",
                saved.getId(),
                "WORKFLOW"
        );
        return workflowMapper.toResponse(
                saved,
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