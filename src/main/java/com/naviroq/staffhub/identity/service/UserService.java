package com.naviroq.staffhub.identity.service;

import com.naviroq.staffhub.identity.domain.CreateUserCommand;
import com.naviroq.staffhub.identity.domain.UpdateUserCommand;
import com.naviroq.staffhub.identity.domain.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User createUser(CreateUserCommand command);

    User getUserById(UUID userId);

    List<User> listUsers();

    User updateUser(UUID userId, UpdateUserCommand command);

    void deleteUser(UUID userId);

}