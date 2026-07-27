package com.naviroq.staffhub.identity.mapper;

import com.naviroq.staffhub.identity.domain.LoginCommand;
import com.naviroq.staffhub.identity.domain.dto.LoginRequestDto;

public interface LoginMapper {

    LoginCommand toCommand(LoginRequestDto dto);
}