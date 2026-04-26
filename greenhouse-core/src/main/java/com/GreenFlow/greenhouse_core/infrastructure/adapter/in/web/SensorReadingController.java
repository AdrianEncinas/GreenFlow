package com.GreenFlow.greenhouse_core.infrastructure.adapter.in.web;

import com.GreenFlow.greenhouse_core.application.port.in.SensorReadingUseCase;
import com.GreenFlow.greenhouse_core.infrastructure.adapter.in.web.dto.SensorReadingDTO;
import com.GreenFlow.greenhouse_core.infrastructure.adapter.in.web.mapper.SensorReadingWebMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sensor-readings")
public class SensorReadingController {

    private final SensorReadingUseCase sensorReadingUseCase;

    public SensorReadingController(SensorReadingUseCase sensorReadingUseCase) {
        this.sensorReadingUseCase = sensorReadingUseCase;
    }

    @GetMapping("/list")
    public ResponseEntity<List<SensorReadingDTO>> getAllReadings() {
        List<SensorReadingDTO> readings = sensorReadingUseCase.getAllReadings().stream()
                .map(SensorReadingWebMapper::toDto)
                .toList();
        return ResponseEntity.ok(readings);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<SensorReadingDTO> getReadingById(@PathVariable Long id) {
        SensorReadingDTO dto = SensorReadingWebMapper.toDto(sensorReadingUseCase.getReadingById(id));
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/sensor/{sensorId}")
    public ResponseEntity<List<SensorReadingDTO>> getReadingsBySensorId(@PathVariable String sensorId) {
        List<SensorReadingDTO> readings = sensorReadingUseCase.getReadingsBySensorId(sensorId).stream()
                .map(SensorReadingWebMapper::toDto)
                .toList();
        return ResponseEntity.ok(readings);
    }
}
