package com.GreenFlow.greenhouse_core.mapper;

import com.GreenFlow.greenhouse_core.dto.SensorReadingDTO;
import com.GreenFlow.greenhouse_core.model.SensorReading;

public class SensorReadingMapper {

    public static SensorReadingDTO toDTO(SensorReading reading) {
        if (reading == null) return null;
        return SensorReadingDTO.builder()
                .id(reading.getId())
                .sensorId(reading.getSensorId())
                .temperature(reading.getTemperature())
                .humidity(reading.getHumidity())
                .co2Level(reading.getCo2Level())
                .timestamp(reading.getTimestamp())
                .build();
    }

    public static SensorReading toEntity(SensorReadingDTO dto) {
        if (dto == null) return null;
        return SensorReading.builder()
                .id(dto.getId())
                .sensorId(dto.getSensorId())
                .temperature(dto.getTemperature())
                .humidity(dto.getHumidity())
                .co2Level(dto.getCo2Level())
                .timestamp(dto.getTimestamp())
                .build();
    }
}
