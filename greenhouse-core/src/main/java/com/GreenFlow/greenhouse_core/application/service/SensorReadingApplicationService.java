package com.GreenFlow.greenhouse_core.application.service;

import com.GreenFlow.greenhouse_core.application.port.in.SensorReadingUseCase;
import com.GreenFlow.greenhouse_core.application.port.out.SensorReadingPersistencePort;
import com.GreenFlow.greenhouse_core.domain.exception.SensorReadingNotFoundException;
import com.GreenFlow.greenhouse_core.domain.model.SensorReading;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SensorReadingApplicationService implements SensorReadingUseCase {

    private final SensorReadingPersistencePort persistencePort;

    public SensorReadingApplicationService(SensorReadingPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public List<SensorReading> getAllReadings() {
        return persistencePort.findAll();
    }

    @Override
    public SensorReading getReadingById(Long id) {
        return persistencePort.findById(id)
                .orElseThrow(() -> new SensorReadingNotFoundException(id));
    }

    @Override
    public List<SensorReading> getReadingsBySensorId(String sensorId) {
        return persistencePort.findBySensorId(sensorId);
    }

    @Override
    @Transactional
    public SensorReading saveReading(SensorReading reading) {
        return persistencePort.save(reading);
    }
}
