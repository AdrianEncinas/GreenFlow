package com.GreenFlow.greenhouse_core.controller;

import com.GreenFlow.greenhouse_core.dto.SensorReadingDTO;
import com.GreenFlow.greenhouse_core.service.ISensorReadingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sensor-readings")
public class SensorReadingController {

    private final ISensorReadingService sensorReadingService;

    public SensorReadingController(ISensorReadingService sensorReadingService) {
        this.sensorReadingService = sensorReadingService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<SensorReadingDTO>> getAllReadings() {
        return ResponseEntity.ok(sensorReadingService.getAllReadings());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<SensorReadingDTO> getReadingById(@PathVariable Long id) {
        return ResponseEntity.ok(sensorReadingService.getReadingById(id));
    }

    @GetMapping("/sensor/{sensorId}")
    public ResponseEntity<List<SensorReadingDTO>> getReadingsBySensorId(@PathVariable String sensorId) {
        return ResponseEntity.ok(sensorReadingService.getReadingsBySensorId(sensorId));
    }
}
