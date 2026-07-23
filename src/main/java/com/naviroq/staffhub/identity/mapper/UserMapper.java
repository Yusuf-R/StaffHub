package com.naviroq.staffhub.identity.mapper;

import com.naviroq.staffhub.identity.domain.CreateUserCommand;
import com.naviroq.staffhub.identity.domain.UpdateUserCommand;
import com.naviroq.staffhub.identity.domain.dto.CreateUserRequestDto;
import com.naviroq.staffhub.identity.domain.dto.UpdateUserRequestDto;
import com.naviroq.staffhub.identity.domain.dto.UserResponseDto;
import com.naviroq.staffhub.identity.domain.entity.User;

public interface UserMapper {
    CreateUserCommand fromDto (CreateUserRequestDto dto);
    UpdateUserCommand fromDto (UpdateUserRequestDto dto);
    UserResponseDto dto (User user);

}
