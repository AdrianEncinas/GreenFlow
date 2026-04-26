package com.GreenFlow.greenhouse_core.infrastructure.adapter.out.persistence.mapper;

import com.GreenFlow.greenhouse_core.domain.model.SensorReading;
import com.GreenFlow.greenhouse_core.infrastructure.adapter.out.persistence.entity.SensorReadingEntity;

public final class SensorReadingPersistenceMapper {

    private SensorReadingPersistenceMapper() {
    }

    public static SensorReading toDomain(SensorReadingEntity entity) {
        if (entity == null) {
            return null;
        }
        return SensorReading.builder()
                .id(entity.getId())
                .sensorId(entity.getSensorId())
                .temperature(entity.getTemperature())
                .humidity(entity.getHumidity())
                .co2Level(entity.getCo2Level())
                .timestamp(entity.getTimestamp())
                .build();
    }

    public static SensorReadingEntity toEntity(SensorReading reading) {
        if (reading == null) {
            return null;
        }
        return SensorReadingEntity.builder()
                .id(reading.getId())
                .sensorId(reading.getSensorId())
                .temperature(reading.getTemperature())
                .humidity(reading.getHumidity())
                .co2Level(reading.getCo2Level())
                .timestamp(reading.getTimestamp())
                .build();
    }
}
