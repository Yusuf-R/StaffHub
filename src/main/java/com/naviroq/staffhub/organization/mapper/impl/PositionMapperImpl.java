package com.naviroq.staffhub.organization.mapper.impl;


import com.naviroq.staffhub.organization.domain.entity.Position;
import com.naviroq.staffhub.organization.domain.position.CreatePositionCommand;
import com.naviroq.staffhub.organization.domain.position.UpdatePositionCommand;
import com.naviroq.staffhub.organization.domain.position.dto.CreatePositionRequest;
import com.naviroq.staffhub.organization.domain.position.dto.PositionResponseDto;
import com.naviroq.staffhub.organization.domain.position.dto.UpdatePositionRequest;
import com.naviroq.staffhub.organization.mapper.PositionMapper;
import org.springframework.stereotype.Component;

@Component
public class PositionMapperImpl implements PositionMapper {

    @Override
    public CreatePositionCommand fromDto(CreatePositionRequest dto) {
        return new CreatePositionCommand(dto.description(),dto.title());
    }

    @Override
    public UpdatePositionCommand fromDto(UpdatePositionRequest dto) {
        return new UpdatePositionCommand(dto.title(), dto.description());
    }

    @Override
    public PositionResponseDto toDto(Position position) {
        return new PositionResponseDto(position.getTitle(), position.getDescription());
    }
}