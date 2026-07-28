package com.naviroq.staffhub.identity.service.impl;

import com.naviroq.staffhub.common.enums.UserStatus;
import com.naviroq.staffhub.common.exception.ResourceNotFoundException;
import com.naviroq.staffhub.identity.domain.CreateUserCommand;
import com.naviroq.staffhub.identity.domain.UpdateUserCommand;
import com.naviroq.staffhub.identity.domain.entity.User;
import com.naviroq.staffhub.identity.repository.UserRepository;
import com.naviroq.staffhub.identity.service.UserService;
import com.naviroq.staffhub.organization.domain.entity.Employee;
import com.naviroq.staffhub.organization.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User createUser(CreateUserCommand command) {
        Employee employee = employeeRepository.findById(command.employeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + command.employeeId()));

        String encodedPassword = passwordEncoder.encode(command.password());

        User user = User.builder()
                .username(command.username())
                .email(command.email())
                .password(encodedPassword)
                .role(command.roleCode())
                .status(UserStatus.ACTIVE)  // or PENDING if email verification is needed
                .employee(employee)
                .build();

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(UUID userId) {
        return userRepository.findByIdWithEmployee(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listUsers() {
        return userRepository.findAllWithEmployee();
    }

    @Override
    @Transactional
    public User updateUser(UUID userId, UpdateUserCommand command) {
        User user = getUserById(userId);

        user.setUsername(command.username());
        user.setRole(command.roleCode());

        // Only update password if a new one is provided (optional improvement)
        if (command.password() != null && !command.password().isBlank()) {
            String encodedPassword = passwordEncoder.encode(command.password());
            user.setPassword(encodedPassword);
        }

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(UUID userId) {
        User user = getUserById(userId);
        userRepository.delete(user);
    }
}