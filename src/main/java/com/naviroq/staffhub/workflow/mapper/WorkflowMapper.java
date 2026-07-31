package com.naviroq.staffhub.workflow.mapper;

import com.naviroq.staffhub.workflow.domain.CreateOnboardingWorkflowCommand;
import com.naviroq.staffhub.workflow.domain.dto.CreateOnboardingWorkflowRequest;

public interface WorkflowMapper {

    CreateOnboardingWorkflowCommand fromDto(CreateOnboardingWorkflowRequest dto);
}