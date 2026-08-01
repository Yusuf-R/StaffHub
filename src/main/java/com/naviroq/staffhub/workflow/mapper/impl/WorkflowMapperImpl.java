package com.naviroq.staffhub.workflow.mapper.impl;

import com.naviroq.staffhub.workflow.domain.CreateOnboardingWorkflowCommand;
import com.naviroq.staffhub.workflow.domain.dto.CreateOnboardingWorkflowRequest;
import com.naviroq.staffhub.workflow.domain.dto.WorkflowRequestResponseDto;
import com.naviroq.staffhub.workflow.domain.entity.WorkflowRequest;
import com.naviroq.staffhub.workflow.mapper.WorkflowMapper;
import org.springframework.stereotype.Component;

@Component
public class WorkflowMapperImpl implements WorkflowMapper {

    @Override
    public CreateOnboardingWorkflowCommand fromDto(CreateOnboardingWorkflowRequest dto) {

        return new CreateOnboardingWorkflowCommand(

                dto.firstName(),
                dto.lastName(),
                dto.gender(),
                dto.dateOfBirth(),
                dto.hireDate(),
                dto.phone(),
                dto.address(),
                dto.bio(),
                dto.profilePictureUrl(),
                dto.employmentType(),
                dto.departmentId(),
                dto.positionId(),
                dto.managerId(),
                dto.username(),
                dto.workEmail(),
                dto.roleCode()
        );
    }

    @Override
    public WorkflowRequestResponseDto toResponse(WorkflowRequest workflow, String message) {
        return new WorkflowRequestResponseDto(
                workflow.getId(),
                workflow.getRequestNumber(),
                workflow.getType(),
                workflow.getStatus(),
                message
        );
    }
}