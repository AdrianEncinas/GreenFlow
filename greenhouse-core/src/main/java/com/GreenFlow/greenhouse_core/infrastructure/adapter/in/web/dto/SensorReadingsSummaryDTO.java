package com.GreenFlow.greenhouse_core.infrastructure.adapter.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SensorReadingsSummaryDTO {

    private int totalReadings;
    private int totalSensors;
    private double averageTemperature;
    private double averageHumidity;
    private double averageCo2Level;
    private int criticalAlerts;
    private Instant latestTimestamp;
    private Map<String, LatestReadingDTO> latestPerSensor;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LatestReadingDTO {
        private double temperature;
        private double humidity;
        private double co2Level;
        private Instant timestamp;
    }
}
