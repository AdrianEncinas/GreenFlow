package com.GreenFlow.greenhouse_core.application.service;

import com.GreenFlow.greenhouse_core.application.port.in.SensorReadingUseCase;
import com.GreenFlow.greenhouse_core.application.port.out.SensorReadingPersistencePort;
import com.GreenFlow.greenhouse_core.domain.exception.SensorReadingNotFoundException;
import com.GreenFlow.greenhouse_core.domain.model.SensorReading;
import com.GreenFlow.greenhouse_core.infrastructure.adapter.in.web.dto.SensorReadingsSummaryDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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
        public SensorReadingsSummaryDTO getSummary() {
        List<SensorReading> readings = persistencePort.findAll();
        if (readings.isEmpty()) {
            return SensorReadingsSummaryDTO.builder()
                .totalReadings(0)
                .totalSensors(0)
                .averageTemperature(0)
                .averageHumidity(0)
                .averageCo2Level(0)
                .criticalAlerts(0)
                .latestTimestamp(Instant.now())
                .latestPerSensor(Map.of())
                .build();
        }

        double averageTemperature = readings.stream().mapToDouble(SensorReading::temperature).average().orElse(0);
        double averageHumidity = readings.stream().mapToDouble(SensorReading::humidity).average().orElse(0);
        double averageCo2Level = readings.stream().mapToDouble(SensorReading::co2Level).average().orElse(0);

        Map<String, SensorReadingsSummaryDTO.LatestReadingDTO> latestPerSensor = readings.stream()
            .collect(java.util.stream.Collectors.groupingBy(SensorReading::sensorId,
                java.util.stream.Collectors.collectingAndThen(
                    java.util.stream.Collectors.maxBy(Comparator.comparing(SensorReading::timestamp)),
                    optionalReading -> optionalReading
                        .map(reading -> SensorReadingsSummaryDTO.LatestReadingDTO.builder()
                            .temperature(reading.temperature())
                            .humidity(reading.humidity())
                            .co2Level(reading.co2Level())
                            .timestamp(reading.timestamp())
                            .build())
                        .orElse(null))));

        long criticalAlerts = readings.stream()
            .filter(reading -> reading.temperature() > 35 || reading.humidity() < 35 || reading.co2Level() > 1200)
            .count();

        Instant latestTimestamp = readings.stream()
            .map(SensorReading::timestamp)
            .max(Instant::compareTo)
            .orElse(Instant.now());

        return SensorReadingsSummaryDTO.builder()
            .totalReadings(readings.size())
            .totalSensors((int) readings.stream().map(SensorReading::sensorId).distinct().count())
            .averageTemperature(averageTemperature)
            .averageHumidity(averageHumidity)
            .averageCo2Level(averageCo2Level)
            .criticalAlerts((int) criticalAlerts)
            .latestTimestamp(latestTimestamp)
            .latestPerSensor(latestPerSensor)
            .build();
        }

    @Override
    @Transactional
    public SensorReading saveReading(SensorReading reading) {
        return persistencePort.save(reading);
    }
}
