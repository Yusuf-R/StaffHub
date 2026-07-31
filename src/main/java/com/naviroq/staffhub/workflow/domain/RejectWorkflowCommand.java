package com.naviroq.staffhub.workflow.domain;

import java.util.UUID;

public record RejectWorkflowCommand(
        UUID workflowId,
        UUID reviewedByEmployeeId,
        String reviewComment
) {}