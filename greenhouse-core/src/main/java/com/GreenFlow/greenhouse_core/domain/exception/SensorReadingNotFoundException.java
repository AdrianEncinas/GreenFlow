package com.GreenFlow.greenhouse_core.domain.exception;

public class SensorReadingNotFoundException extends RuntimeException {

    public SensorReadingNotFoundException(Long id) {
        super("SensorReading not found with id: " + id);
    }
}
