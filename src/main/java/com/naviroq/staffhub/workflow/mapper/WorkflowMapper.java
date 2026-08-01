package com.naviroq.staffhub.workflow.mapper;

import com.naviroq.staffhub.workflow.domain.CreateOnboardingWorkflowCommand;
import com.naviroq.staffhub.workflow.domain.dto.CreateOnboardingWorkflowRequest;
import com.naviroq.staffhub.workflow.domain.dto.WorkflowRequestResponseDto;
import com.naviroq.staffhub.workflow.domain.entity.WorkflowRequest;

public interface WorkflowMapper {

    CreateOnboardingWorkflowCommand fromDto(CreateOnboardingWorkflowRequest dto);

    WorkflowRequestResponseDto toResponse(WorkflowRequest workflow, String message);
}