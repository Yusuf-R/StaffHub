package com.naviroq.staffhub.identity.service.impl;

import com.naviroq.staffhub.common.enums.UserStatus;
import com.naviroq.staffhub.identity.domain.CreateUserCommand;
import com.naviroq.staffhub.identity.domain.UpdateUserCommand;
import com.naviroq.staffhub.identity.domain.entity.User;
import com.naviroq.staffhub.identity.repository.UserRepository;
import com.naviroq.staffhub.identity.service.UserService;
import com.naviroq.staffhub.organization.domain.entity.Employee;
import com.naviroq.staffhub.organization.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    public UserServiceImpl(
            UserRepository userRepository,
            EmployeeRepository employeeRepository
    ) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public User createUser(CreateUserCommand command) {

        Employee employee = employeeRepository.findById(command.employeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        User user = User.builder()
                .username(command.username())
                .email(command.email())
                .password(command.password())
                .role(command.roleCode())
                .status(UserStatus.ACTIVE)
                .employee(employee)
                .build();

        return userRepository.save(user);
    }

    @Override
    public User getUserById(UUID userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

    }

    @Override
    public List<User> listUsers() {

        return userRepository.findAll();

    }

    @Override
    public User updateUser(UUID userId, UpdateUserCommand command) {

        User user = getUserById(userId);

        user.setUsername(command.username());
        user.setRole(command.roleCode());
        user.setPassword(command.password());

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID userId) {

        userRepository.delete(getUserById(userId));

    }
}