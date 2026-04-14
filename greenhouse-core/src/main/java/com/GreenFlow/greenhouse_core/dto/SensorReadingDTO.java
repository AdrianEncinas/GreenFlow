package com.GreenFlow.greenhouse_core.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SensorReadingDTO {

    private Long id;
    private String sensorId;
    private double temperature;
    private double humidity;
    private double co2Level;
    private Instant timestamp;
}
