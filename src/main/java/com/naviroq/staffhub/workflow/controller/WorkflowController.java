package com.naviroq.staffhub.workflow.controller;

import com.naviroq.staffhub.workflow.domain.CreateOnboardingWorkflowCommand;
import com.naviroq.staffhub.workflow.domain.dto.CreateOnboardingWorkflowRequest;
import com.naviroq.staffhub.workflow.domain.dto.WorkflowRequestResponseDto;
import com.naviroq.staffhub.workflow.mapper.WorkflowMapper;
import com.naviroq.staffhub.workflow.service.WorkflowService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/staff-hub/workflow")
public class WorkflowController {
    private final WorkflowMapper workflowMapper;
    private final WorkflowService workflowService;


    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN') or hasRole('HR')")
    @PostMapping("/onboarding")
    @ResponseStatus(HttpStatus.CREATED)
    public WorkflowRequestResponseDto submitOnboarding(
            @Valid
            @RequestBody
            CreateOnboardingWorkflowRequest request
    ) {

        CreateOnboardingWorkflowCommand command =
                workflowMapper.fromDto(request);

        return workflowService.submitOnboarding(command);
    }
//    GET    /workflow
//    GET    /workflow/pending
//    GET    /workflow/{id}
//
//    POST   /workflow/{id}/approve
//    POST   /workflow/{id}/reject
//    POST   /workflow/{id}/cancel
}
