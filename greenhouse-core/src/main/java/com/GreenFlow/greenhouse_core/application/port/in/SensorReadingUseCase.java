package com.GreenFlow.greenhouse_core.application.port.in;

import com.GreenFlow.greenhouse_core.domain.model.SensorReading;
import com.GreenFlow.greenhouse_core.infrastructure.adapter.in.web.dto.SensorReadingsSummaryDTO;

import java.util.List;

public interface SensorReadingUseCase {

    List<SensorReading> getAllReadings();

    SensorReading getReadingById(Long id);

    List<SensorReading> getReadingsBySensorId(String sensorId);

    SensorReadingsSummaryDTO getSummary();

    SensorReading saveReading(SensorReading reading);
}
