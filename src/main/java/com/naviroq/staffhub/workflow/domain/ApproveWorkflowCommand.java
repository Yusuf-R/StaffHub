package com.naviroq.staffhub.workflow.domain;

import java.util.UUID;

public record ApproveWorkflowCommand(

        UUID workflowId,

        UUID reviewedByEmployeeId,

        String reviewComment

) {}