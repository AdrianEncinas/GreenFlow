package com.GreenFlow.sensor_service.producer;

import com.GreenFlow.sensor_service.model.SensorReading;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SensorProducer {

    private final KafkaTemplate<String, SensorReading> kafkaTemplate;

    @Value("${sensor.kafka.topic}")
    private String topic;

    public void send(SensorReading reading) {
        log.info("Kafka OUT | topic={} | key={} | payload={}",
                topic,
                reading.getSensorId(),
                reading);

        kafkaTemplate.send(topic, reading.getSensorId(), reading)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send reading for sensor {}: {}", reading.getSensorId(), ex.getMessage());
                    } else {
                        log.info("Sent reading for sensor {} to partition {} offset {}",
                                reading.getSensorId(),
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }
}
