package com.naviroq.staffhub.workflow.repository;

import com.naviroq.staffhub.organization.domain.entity.Employee;
import com.naviroq.staffhub.workflow.domain.entity.WorkflowRequest;
import com.naviroq.staffhub.workflow.domain.enums.WorkflowStatus;
import com.naviroq.staffhub.workflow.domain.enums.WorkflowType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkflowRepository extends JpaRepository<WorkflowRequest, UUID> {

    List<WorkflowRequest> findByStatus(WorkflowStatus status);

    List<WorkflowRequest> findByType(WorkflowType type);

    List<WorkflowRequest> findByRequestedBy(Employee employee);

    List<WorkflowRequest> findByAssignedTo(Employee employee);

    List<WorkflowRequest> findByReviewedBy(Employee employee);
}