package com.GreenFlow.greenhouse_core.repository;

import com.GreenFlow.greenhouse_core.model.SensorReading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorReadingRepository extends JpaRepository<SensorReading, Long> {

    List<SensorReading> findBySensorId(String sensorId);
}
