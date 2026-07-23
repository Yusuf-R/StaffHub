package com.naviroq.staffhub.organization.service.impl;

import com.naviroq.staffhub.organization.domain.entity.Position;
import com.naviroq.staffhub.organization.domain.position.CreatePositionCommand;
import com.naviroq.staffhub.organization.domain.position.UpdatePositionCommand;
import com.naviroq.staffhub.organization.repository.PositionRepository;
import com.naviroq.staffhub.organization.service.PositionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PositionServiceImpl implements PositionService {
    PositionRepository positionRepository;

    public PositionServiceImpl(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public Position createPosition(CreatePositionCommand command) {

        Position position = Position.builder()
                .title(command.title())
                .description(command.description())
                .build();

        return positionRepository.save(position);

    }

    @Override
    public Position updatePosition(UUID positionId, UpdatePositionCommand command) {

        Position position = getPositionById(positionId);

        position.setTitle(command.title());
        position.setDescription(command.description());

        return positionRepository.save(position);
    }

    @Override
    public Position getPositionById(UUID positionId) {

        return positionRepository.findById(positionId)
                .orElseThrow(() -> new RuntimeException("Position not found"));

    }

    @Override
    public List<Position> listPositions() {

        return positionRepository.findAll();

    }

       @Override
    public void deletePosition(UUID positionId) {

        positionRepository.delete(getPositionById(positionId));

    }
}
