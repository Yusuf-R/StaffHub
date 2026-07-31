package com.naviroq.staffhub.workflow.domain.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.naviroq.staffhub.common.entity.BaseEntity;
import com.naviroq.staffhub.organization.domain.entity.Employee;
import com.naviroq.staffhub.workflow.domain.enums.ApprovalLevel;
import com.naviroq.staffhub.workflow.domain.enums.WorkflowStatus;
import com.naviroq.staffhub.workflow.domain.enums.WorkflowType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "workflow_requests",  schema = "staff_hub")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowRequest extends BaseEntity {

    @Column(nullable = false, unique = true, length = 64)
    private String requestNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private WorkflowType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private WorkflowStatus status = WorkflowStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ApprovalLevel level;

    /**
     * The employee who submitted the request.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_by_id", nullable = false)
    private Employee requestedBy;

    /**
     * The employee currently assigned to review this request.
     * Can be null until assignment.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to_id")
    private Employee assignedTo;

    /**
     * The employee who approved or rejected the request.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by_id")
    private Employee reviewedBy;

    /**
     * JSON payload containing the original request.
     */
    //    @Column(columnDefinition = "jsonb", nullable = false)
    //    private JsonNode payload;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload", columnDefinition = "jsonb")
    private JsonNode payload;

    /**
     * Reason supplied by the requester.
     */
    @Column(columnDefinition = "TEXT")
    private String reason;

    /**
     * Reviewer's comment.
     */
    @Column(columnDefinition = "TEXT")
    private String reviewComment;

    /**
     * Time the request was reviewed.
     */
    private LocalDateTime reviewedAt;
}