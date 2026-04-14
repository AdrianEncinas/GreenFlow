package com.GreenFlow.greenhouse_core.service;

import com.GreenFlow.greenhouse_core.dto.SensorReadingDTO;
import com.GreenFlow.greenhouse_core.model.SensorReading;

import java.util.List;

public interface ISensorReadingService {

    List<SensorReadingDTO> getAllReadings();

    SensorReadingDTO getReadingById(Long id);

    List<SensorReadingDTO> getReadingsBySensorId(String sensorId);

    SensorReadingDTO saveReading(SensorReading reading);
}
