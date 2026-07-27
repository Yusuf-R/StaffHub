package com.naviroq.staffhub.identity.repository;

import com.naviroq.staffhub.identity.domain.entity.User;
import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository <User, UUID> {

    ObservationFilter findByEmail(String email);
}