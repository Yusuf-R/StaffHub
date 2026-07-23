package com.naviroq.staffhub.organization.mapper;

import com.naviroq.staffhub.organization.domain.entity.Position;
import com.naviroq.staffhub.organization.domain.position.CreatePositionCommand;
import com.naviroq.staffhub.organization.domain.position.UpdatePositionCommand;
import com.naviroq.staffhub.organization.domain.position.dto.CreatePositionRequest;
import com.naviroq.staffhub.organization.domain.position.dto.PositionResponseDto;
import com.naviroq.staffhub.organization.domain.position.dto.UpdatePositionRequest;

public interface PositionMapper {
    CreatePositionCommand fromDto (CreatePositionRequest dto);
    UpdatePositionCommand fromDto (UpdatePositionRequest dto);
    PositionResponseDto toDto (Position position);
}