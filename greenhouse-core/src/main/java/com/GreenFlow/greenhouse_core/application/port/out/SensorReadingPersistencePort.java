package com.GreenFlow.greenhouse_core.application.port.out;

import com.GreenFlow.greenhouse_core.domain.model.SensorReading;

import java.util.List;
import java.util.Optional;

public interface SensorReadingPersistencePort {

    List<SensorReading> findAll();

    Optional<SensorReading> findById(Long id);

    List<SensorReading> findBySensorId(String sensorId);

    SensorReading save(SensorReading reading);
}
