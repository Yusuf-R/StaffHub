package com.naviroq.staffhub.platform.snapshot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naviroq.staffhub.workflow.domain.entity.WorkflowRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WorkflowSnapshotFactory {

    private final ObjectMapper objectMapper;

    public JsonNode create(WorkflowRequest workflow) {

        Map<String, Object> snapshot = new LinkedHashMap<>();

        snapshot.put("id", workflow.getId());
        snapshot.put("requestNumber", workflow.getRequestNumber());
        snapshot.put("type", workflow.getType());
        snapshot.put("status", workflow.getStatus());
        snapshot.put("approvalLevel", workflow.getLevel());

        if (workflow.getRequestedBy() != null) {
            snapshot.put("requestedById", workflow.getRequestedBy().getId());
            snapshot.put(
                    "requestedByName",
                    workflow.getRequestedBy().getFirstName() + " "
                            + workflow.getRequestedBy().getLastName()
            );
        }

        if (workflow.getReviewedBy() != null) {
            snapshot.put(
                    "reviewedById",
                    workflow.getReviewedBy().getId()
            );
            snapshot.put(
                    "reviewedByName",
                    workflow.getReviewedBy().getFirstName()
                            + " "
                            + workflow.getReviewedBy().getLastName()
            );
        }

        snapshot.put("reviewedAt", workflow.getReviewedAt());
        snapshot.put("createdAt", workflow.getCreatedAt());
        snapshot.put("updatedAt", workflow.getUpdatedAt());

        return objectMapper.valueToTree(snapshot);
    }
}