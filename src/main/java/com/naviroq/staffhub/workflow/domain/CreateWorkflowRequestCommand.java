package com.naviroq.staffhub.workflow.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.naviroq.staffhub.workflow.domain.enums.ApprovalLevel;
import com.naviroq.staffhub.workflow.domain.enums.WorkflowType;

import java.util.UUID;

public record CreateWorkflowRequestCommand(
        WorkflowType workflowType,
        ApprovalLevel approvalLevel,
        UUID requestedByEmployeeId,
        UUID assignedToEmployeeId,
        JsonNode payload,
        String reason

) {}