package com.naviroq.staffhub.identity.mapper.impl;

import com.naviroq.staffhub.identity.domain.LoginCommand;
import com.naviroq.staffhub.identity.domain.dto.LoginRequestDto;
import com.naviroq.staffhub.identity.mapper.LoginMapper;
import org.springframework.stereotype.Component;

@Component
public class LoginMapperImpl implements LoginMapper {


    @Override
    public LoginCommand toCommand(LoginRequestDto dto) {
        if (dto == null) {
            return null;
        }
        return new LoginCommand(
                dto.email(),
                dto.password(),
                dto.deviceName()
        );
    }
}
