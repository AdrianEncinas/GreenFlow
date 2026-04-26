package com.GreenFlow.greenhouse_core.infrastructure.adapter.out.persistence;

import com.GreenFlow.greenhouse_core.application.port.out.SensorReadingPersistencePort;
import com.GreenFlow.greenhouse_core.domain.model.SensorReading;
import com.GreenFlow.greenhouse_core.infrastructure.adapter.out.persistence.entity.SensorReadingEntity;
import com.GreenFlow.greenhouse_core.infrastructure.adapter.out.persistence.mapper.SensorReadingPersistenceMapper;
import com.GreenFlow.greenhouse_core.infrastructure.adapter.out.persistence.repository.SensorReadingJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SensorReadingPersistenceAdapter implements SensorReadingPersistencePort {

    private final SensorReadingJpaRepository jpaRepository;

    public SensorReadingPersistenceAdapter(SensorReadingJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<SensorReading> findAll() {
        return jpaRepository.findAll().stream()
                .map(SensorReadingPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<SensorReading> findById(Long id) {
        return jpaRepository.findById(id)
                .map(SensorReadingPersistenceMapper::toDomain);
    }

    @Override
    public List<SensorReading> findBySensorId(String sensorId) {
        return jpaRepository.findBySensorId(sensorId).stream()
                .map(SensorReadingPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public SensorReading save(SensorReading reading) {
        SensorReadingEntity saved = jpaRepository.save(SensorReadingPersistenceMapper.toEntity(reading));
        return SensorReadingPersistenceMapper.toDomain(saved);
    }
}
