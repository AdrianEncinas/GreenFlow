package com.GreenFlow.greenhouse_core.infrastructure.adapter.in.kafka;

import com.GreenFlow.greenhouse_core.application.port.in.SensorReadingUseCase;
import com.GreenFlow.greenhouse_core.domain.model.SensorReading;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@RequiredArgsConstructor
public class SensorReadingConsumer {

    private final SensorReadingUseCase sensorReadingUseCase;

    @KafkaListener(
            topics = "${sensor.kafka.topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(SensorReading reading) {
        log.info("Received sensor reading from [{}]: temp={}, humidity={}, co2={}",
                reading.getSensorId(),
                reading.getTemperature(),
                reading.getHumidity(),
                reading.getCo2Level());

        String consoleMessage = String.format(
                "[KAFKA] sensorId=%s temp=%.2f humidity=%.2f co2=%.2f timestamp=%s",
                reading.getSensorId(),
                reading.getTemperature(),
                reading.getHumidity(),
                reading.getCo2Level(),
                reading.getTimestamp() == null
                        ? "null"
                        : DateTimeFormatter.ISO_INSTANT.format(reading.getTimestamp())
        );
        System.out.println(consoleMessage);

        sensorReadingUseCase.saveReading(reading);
    }
}
