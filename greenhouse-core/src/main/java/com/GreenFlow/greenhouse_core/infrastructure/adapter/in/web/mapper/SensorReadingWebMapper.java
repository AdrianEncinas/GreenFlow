package com.GreenFlow.greenhouse_core.infrastructure.adapter.in.web.mapper;

import com.GreenFlow.greenhouse_core.domain.model.SensorReading;
import com.GreenFlow.greenhouse_core.infrastructure.adapter.in.web.dto.SensorReadingDTO;

public final class SensorReadingWebMapper {

    private SensorReadingWebMapper() {
    }

    public static SensorReadingDTO toDto(SensorReading reading) {
        if (reading == null) {
            return null;
        }
        return SensorReadingDTO.builder()
                .id(reading.getId())
                .sensorId(reading.getSensorId())
                .temperature(reading.getTemperature())
                .humidity(reading.getHumidity())
                .co2Level(reading.getCo2Level())
                .timestamp(reading.getTimestamp())
                .build();
    }

    public static SensorReading toDomain(SensorReadingDTO dto) {
        if (dto == null) {
            return null;
        }
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
