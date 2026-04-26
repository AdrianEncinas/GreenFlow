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
        return new SensorReading(
                entity.getId(),
                entity.getSensorId(),
                entity.getTemperature(),
                entity.getHumidity(),
                entity.getCo2Level(),
                entity.getTimestamp());
    }

    public static SensorReadingEntity toEntity(SensorReading reading) {
        if (reading == null) {
            return null;
        }
        return SensorReadingEntity.builder()
                .id(reading.id())
                .sensorId(reading.sensorId())
                .temperature(reading.temperature())
                .humidity(reading.humidity())
                .co2Level(reading.co2Level())
                .timestamp(reading.timestamp())
                .build();
    }
}
