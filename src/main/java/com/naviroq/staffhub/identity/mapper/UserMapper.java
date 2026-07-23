package com.naviroq.staffhub.identity.mapper;

import com.naviroq.staffhub.identity.domain.dto.CreateUserRequestDto;
import com.naviroq.staffhub.identity.domain.dto.UpdateUserRequestDto;
import com.naviroq.staffhub.identity.domain.entity.User;

public interface UserMapper {
    CreateUserRequest fromDto (CreateUserRequestDto dto);
    UpdateUserRequest fromDto (UpdateUserRequestDto dto);
    UserRequestDto dto (User user);

}
