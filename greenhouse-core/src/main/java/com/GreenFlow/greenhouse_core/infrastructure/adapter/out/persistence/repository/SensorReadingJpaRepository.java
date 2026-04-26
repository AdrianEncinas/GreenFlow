package com.GreenFlow.greenhouse_core.infrastructure.adapter.out.persistence.repository;

import com.GreenFlow.greenhouse_core.infrastructure.adapter.out.persistence.entity.SensorReadingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorReadingJpaRepository extends JpaRepository<SensorReadingEntity, Long> {

    List<SensorReadingEntity> findBySensorId(String sensorId);
}
