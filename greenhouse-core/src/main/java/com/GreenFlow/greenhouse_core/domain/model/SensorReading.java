package com.GreenFlow.greenhouse_core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorReading {

    private Long id;
    private String sensorId;
    private double temperature;
    private double humidity;
    private double co2Level;
    private Instant timestamp;
}
