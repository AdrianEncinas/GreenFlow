package com.GreenFlow.greenhouse_core.application.port.in;

import com.GreenFlow.greenhouse_core.domain.model.SensorReading;

import java.util.List;

public interface SensorReadingUseCase {

    List<SensorReading> getAllReadings();

    SensorReading getReadingById(Long id);

    List<SensorReading> getReadingsBySensorId(String sensorId);

    SensorReading saveReading(SensorReading reading);
}
