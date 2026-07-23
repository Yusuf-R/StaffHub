package com.naviroq.staffhub.organization.service;

import com.naviroq.staffhub.organization.domain.entity.Position;
import com.naviroq.staffhub.organization.domain.position.CreatePositionCommand;
import com.naviroq.staffhub.organization.domain.position.UpdatePositionCommand;

import java.util.List;
import java.util.UUID;

public interface PositionService {
    Position createPosition(CreatePositionCommand command);
    Position updatePosition(UUID positionId, UpdatePositionCommand command);
    Position getPositionById(UUID positionId);
    List<Position> listPositions();
    void deletePosition(UUID positionId);

}