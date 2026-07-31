package com.naviroq.staffhub.workflow.domain;

import java.util.UUID;

public record CancelWorkflowCommand(

        UUID workflowId,

        UUID requestedByEmployeeId,

        String reason

) {}
