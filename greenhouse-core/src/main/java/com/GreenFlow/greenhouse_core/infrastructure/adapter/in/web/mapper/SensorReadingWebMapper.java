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
                .id(reading.id())
                .sensorId(reading.sensorId())
                .temperature(reading.temperature())
                .humidity(reading.humidity())
                .co2Level(reading.co2Level())
                .timestamp(reading.timestamp())
                .build();
    }

    public static SensorReading toDomain(SensorReadingDTO dto) {
        if (dto == null) {
            return null;
        }
        return new SensorReading(
                dto.getId(),
                dto.getSensorId(),
                dto.getTemperature(),
                dto.getHumidity(),
                dto.getCo2Level(),
                dto.getTimestamp());
    }
}
