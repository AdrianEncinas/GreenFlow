package com.GreenFlow.greenhouse_core.service;

import com.GreenFlow.greenhouse_core.dto.SensorReadingDTO;
import com.GreenFlow.greenhouse_core.exception.NotFoundException;
import com.GreenFlow.greenhouse_core.mapper.SensorReadingMapper;
import com.GreenFlow.greenhouse_core.model.SensorReading;
import com.GreenFlow.greenhouse_core.repository.SensorReadingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorReadingService implements ISensorReadingService {

    private final SensorReadingRepository sensorReadingRepository;

    public SensorReadingService(SensorReadingRepository sensorReadingRepository) {
        this.sensorReadingRepository = sensorReadingRepository;
    }

    @Override
    public List<SensorReadingDTO> getAllReadings() {
        return sensorReadingRepository.findAll()
                .stream()
                .map(SensorReadingMapper::toDTO)
                .toList();
    }

    @Override
    public SensorReadingDTO getReadingById(Long id) {
        return SensorReadingMapper.toDTO(
                sensorReadingRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("SensorReading not found with id: " + id))
        );
    }

    @Override
    public List<SensorReadingDTO> getReadingsBySensorId(String sensorId) {
        return sensorReadingRepository.findBySensorId(sensorId)
                .stream()
                .map(SensorReadingMapper::toDTO)
                .toList();
    }

    @Override
    public SensorReadingDTO saveReading(SensorReading reading) {
        SensorReading saved = sensorReadingRepository.save(reading);
        return SensorReadingMapper.toDTO(saved);
    }
}
