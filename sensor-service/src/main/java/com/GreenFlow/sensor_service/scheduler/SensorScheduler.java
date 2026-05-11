package com.GreenFlow.sensor_service.scheduler;

import com.GreenFlow.sensor_service.model.SensorReading;
import com.GreenFlow.sensor_service.producer.SensorProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class SensorScheduler {

    private final SensorProducer sensorProducer;
    private final Random random = new Random();

    private static final List<String> SENSOR_IDS = List.of(
            "sensor-001", "sensor-002", "sensor-003"
    );

    @Scheduled(fixedRateString = "${sensor.scheduler.fixed-rate-ms:300000}")
    public void publishSensorReadings() {
        SENSOR_IDS.forEach(sensorId -> {
            SensorReading reading = SensorReading.builder()
                    .sensorId(sensorId)
                    .temperature(15.0 + random.nextDouble() * 2)
                    .humidity(40.0 + random.nextDouble() * 2)
                    .co2Level(300.0 + random.nextDouble() * 2)
                    .timestamp(Instant.now())
                    .build();

            log.debug("Publishing reading: {}", reading);
            sensorProducer.send(reading);
        });
    }
}
