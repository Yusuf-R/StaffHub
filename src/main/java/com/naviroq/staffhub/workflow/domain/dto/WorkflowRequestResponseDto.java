package com.naviroq.staffhub.workflow.domain.dto;

import com.naviroq.staffhub.workflow.domain.enums.WorkflowStatus;
import com.naviroq.staffhub.workflow.domain.enums.WorkflowType;

import java.util.UUID;

public record WorkflowRequestResponseDto(
        UUID id,
        String requestNumber,
        WorkflowType workflowType,
        WorkflowStatus status,
        String message
) {
}

/**
 * Example response
 * {
 *     "id":"...",
 *     "requestNumber":"REQ-2026-SE-260724-66B96D-000001",
 *     "workflowType":"EMPLOYEE_ONBOARDING",
 *     "status":"PENDING",
 *     "message":"Request submitted successfully. Awaiting Administrator approval."
 * }
 */
