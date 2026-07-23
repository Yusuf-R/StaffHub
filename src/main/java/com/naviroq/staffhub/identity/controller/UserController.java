package com.naviroq.staffhub.identity.controller;

import com.naviroq.staffhub.identity.domain.dto.CreateUserRequestDto;
import com.naviroq.staffhub.identity.domain.dto.UpdateUserRequestDto;
import com.naviroq.staffhub.identity.domain.dto.UserResponseDto;
import com.naviroq.staffhub.identity.domain.entity.User;
import com.naviroq.staffhub.identity.mapper.UserMapper;
import com.naviroq.staffhub.identity.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/staff-hub/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(
            UserService userService,
            UserMapper userMapper
    ) {
        this.userService = userService;
        this.userMapper = userMapper;
    }



    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(
            @Valid @RequestBody CreateUserRequestDto request
    ) {

        User user = userService.createUser(
                userMapper.fromDto(request)
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.toDto(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(
            @PathVariable UUID id
    ) {

        User user = userService.getUserById(id);

        return ResponseEntity.ok(
                userMapper.toDto(user)
        );
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> listUsers() {

        List<UserResponseDto> response = userService.listUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserRequestDto request
    ) {

        User user = userService.updateUser(
                id,
                userMapper.fromDto(request)
        );

        return ResponseEntity.ok(
                userMapper.toDto(user)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable UUID id
    ) {

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

}