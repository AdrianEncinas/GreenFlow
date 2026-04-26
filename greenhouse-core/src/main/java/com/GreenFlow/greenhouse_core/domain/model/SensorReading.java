package com.GreenFlow.greenhouse_core.domain.model;

import java.time.Instant;

public record SensorReading(
        Long id,
        String sensorId,
        double temperature,
        double humidity,
        double co2Level,
        Instant timestamp) {
}
