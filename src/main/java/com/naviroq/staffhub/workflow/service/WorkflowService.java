package com.naviroq.staffhub.workflow.service;

import com.naviroq.staffhub.workflow.domain.CreateOnboardingWorkflowCommand;
import com.naviroq.staffhub.workflow.domain.dto.WorkflowRequestResponseDto;

import java.util.UUID;

public interface WorkflowService {

    WorkflowRequestResponseDto submitOnboarding(CreateOnboardingWorkflowCommand command);

//     WorkflowRequestResponseDto submitLeave(CreateLeaveWorkflowCommand command);

//     WorkflowRequestResponseDto submitProfileUpdate(CreateProfileUpdateWorkflowCommand command);

    WorkflowRequestResponseDto approve(UUID workflowId);

    WorkflowRequestResponseDto reject(UUID workflowId, String reason);

    WorkflowRequestResponseDto cancel(UUID workflowId);

}