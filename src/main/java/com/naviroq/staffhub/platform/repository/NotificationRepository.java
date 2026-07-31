package com.naviroq.staffhub.platform.repository;

import com.naviroq.staffhub.organization.domain.entity.Employee;
import com.naviroq.staffhub.platform.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    List<Notification> findByRecipientOrderByCreatedAtDesc(Employee recipient);

}