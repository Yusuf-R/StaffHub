package com.naviroq.staffhub.organization.service.impl;

import com.naviroq.staffhub.common.constants.PositionStandard;
import com.naviroq.staffhub.common.exception.DuplicateResourceException;
import com.naviroq.staffhub.common.exception.ValidationException;
import com.naviroq.staffhub.organization.domain.entity.Position;
import com.naviroq.staffhub.organization.domain.position.CreatePositionCommand;
import com.naviroq.staffhub.organization.domain.position.UpdatePositionCommand;
import com.naviroq.staffhub.organization.repository.PositionRepository;
import com.naviroq.staffhub.organization.service.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;

    public PositionServiceImpl(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    @Transactional
    public Position createPosition(CreatePositionCommand command) {
        log.info("Creating position with title: {}", command.title());

        // ============================================================
        // ENFORCEMENT STEP 1: Validate against the Standard
        // ============================================================

        // 1.1 Check if the title is valid
        if (!PositionStandard.isValidTitle(command.title())) {
            throw new ValidationException(
                    String.format("Invalid position title: '%s'. Valid titles are: %s",
                            command.title(),
                            PositionStandard.getAllTitles())
            );
        }

        // 1.2 Get the standard for this title
        PositionStandard standard = PositionStandard.fromTitle(command.title());

        // 1.3 Check if the description matches the standard (case-insensitive)
        if (!standard.getDescription().equalsIgnoreCase(command.description())) {
            throw new ValidationException(
                    String.format("Position description does not match the standard. " +
                                    "For title '%s', the standard description is: '%s'. You provided: '%s'",
                            command.title(),
                            standard.getDescription(),
                            command.description())
            );
        }

        // ============================================================
        // ENFORCEMENT STEP 2: Check for duplicates
        // ============================================================

        // 2.1 Check if a position with this title already exists
        if (positionRepository.existsByTitle(command.title())) {
            throw new DuplicateResourceException(
                    String.format("Position with title '%s' already exists", command.title())
            );
        }

        // ============================================================
        // ENFORCEMENT STEP 3: Build and Save (Use Standard values for consistency)
        // ============================================================

        // Use the standard values to ensure 100% consistency
        Position position = Position.builder()
                .title(standard.getTitle())           // Use standard title
                .description(standard.getDescription()) // Use standard description
                .build();

        Position savedPosition = positionRepository.save(position);
        log.info("Successfully created position with title: {}", savedPosition.getTitle());

        return savedPosition;
    }

    @Override
    @Transactional
    public Position updatePosition(UUID positionId, UpdatePositionCommand command) {
        log.info("Updating position with ID: {}", positionId);

        // ============================================================
        // ENFORCEMENT STEP 1: Check if position exists
        // ============================================================
        Position existingPosition = getPositionById(positionId);

        // ============================================================
        // ENFORCEMENT STEP 2: Validate against the Standard
        // ============================================================

        // 2.1 Check if the new title is valid
        if (!PositionStandard.isValidTitle(command.title())) {
            throw new ValidationException(
                    String.format("Invalid position title: '%s'. Valid titles are: %s",
                            command.title(),
                            PositionStandard.getAllTitles())
            );
        }

        // 2.2 Get the standard for this title
        PositionStandard standard = PositionStandard.fromTitle(command.title());

        // 2.3 Check if the description matches the standard
        if (!standard.getDescription().equalsIgnoreCase(command.description())) {
            throw new ValidationException(
                    String.format("Position description does not match the standard. " +
                                    "For title '%s', the standard description is: '%s'. You provided: '%s'",
                            command.title(),
                            standard.getDescription(),
                            command.description())
            );
        }

        // ============================================================
        // ENFORCEMENT STEP 3: Check for duplicates (except itself)
        // ============================================================

        // 3.1 Check if another position already uses this title
        if (!existingPosition.getTitle().equalsIgnoreCase(command.title()) &&
                positionRepository.existsByTitle(command.title())) {
            throw new DuplicateResourceException(
                    String.format("Another position with title '%s' already exists", command.title())
            );
        }

        // ============================================================
        // ENFORCEMENT STEP 4: Update with Standard values
        // ============================================================
        existingPosition.setTitle(standard.getTitle());
        existingPosition.setDescription(standard.getDescription());

        Position updatedPosition = positionRepository.save(existingPosition);
        log.info("Successfully updated position with ID: {}", updatedPosition.getId());

        return updatedPosition;
    }

    @Override
    public Position getPositionById(UUID positionId) {
        return positionRepository.findById(positionId)
                .orElseThrow(() -> new RuntimeException("Position not found with ID: " + positionId));
    }

    @Override
    public List<Position> listPositions() {
        return positionRepository.findAll();
    }

    @Override
    @Transactional
    public void deletePosition(UUID positionId) {
        log.info("Deleting position with ID: {}", positionId);
        Position position = getPositionById(positionId);
        positionRepository.delete(position);
        log.info("Successfully deleted position with ID: {}", positionId);
    }
}