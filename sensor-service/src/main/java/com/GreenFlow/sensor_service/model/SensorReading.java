package com.GreenFlow.sensor_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorReading {

    private String sensorId;
    private double temperature;
    private double humidity;
    private double co2Level;
    private Instant timestamp;
}
