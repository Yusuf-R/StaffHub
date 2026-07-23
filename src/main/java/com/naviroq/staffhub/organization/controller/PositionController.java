package com.naviroq.staffhub.organization.controller;

import com.naviroq.staffhub.organization.domain.entity.Position;
import com.naviroq.staffhub.organization.domain.position.dto.CreatePositionRequest;
import com.naviroq.staffhub.organization.domain.position.dto.PositionResponseDto;
import com.naviroq.staffhub.organization.domain.position.dto.UpdatePositionRequest;
import com.naviroq.staffhub.organization.mapper.PositionMapper;
import com.naviroq.staffhub.organization.service.PositionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/staff-hub/positions")
public class PositionController {

    private final PositionService positionService;
    private final PositionMapper positionMapper;

    public PositionController(
            PositionService positionService,
            PositionMapper positionMapper
    ) {
        this.positionService = positionService;
        this.positionMapper = positionMapper;
    }

    @PostMapping
    public ResponseEntity<PositionResponseDto> createPosition(
            @Valid @RequestBody CreatePositionRequest request
    ) {

        Position position = positionService.createPosition(
                positionMapper.fromDto(request)
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(positionMapper.toDto(position));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PositionResponseDto> getPosition(
            @PathVariable UUID id
    ) {

        Position position = positionService.getPositionById(id);

        return ResponseEntity.ok(
                positionMapper.toDto(position)
        );
    }

    @GetMapping
    public ResponseEntity<List<PositionResponseDto>> listPositions() {

        List<PositionResponseDto> response = positionService.listPositions()
                .stream()
                .map(positionMapper::toDto)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PositionResponseDto> updatePosition(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePositionRequest request
    ) {

        Position position = positionService.updatePosition(
                id,
                positionMapper.fromDto(request)
        );

        return ResponseEntity.ok(
                positionMapper.toDto(position)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosition(
            @PathVariable UUID id
    ) {

        positionService.deletePosition(id);

        return ResponseEntity.noContent().build();
    }

}