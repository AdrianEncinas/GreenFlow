package com.GreenFlow.greenhouse_core.domain.model;

import java.time.Instant;

public class SensorReading {

    private Long id;
    private String sensorId;
    private double temperature;
    private double humidity;
    private double co2Level;
    private Instant timestamp;

    public SensorReading() {
    }

    public SensorReading(Long id, String sensorId, double temperature, double humidity, double co2Level, Instant timestamp) {
        this.id = id;
        this.sensorId = sensorId;
        this.temperature = temperature;
        this.humidity = humidity;
        this.co2Level = co2Level;
        this.timestamp = timestamp;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSensorId() { return sensorId; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public double getHumidity() { return humidity; }
    public void setHumidity(double humidity) { this.humidity = humidity; }

    public double getCo2Level() { return co2Level; }
    public void setCo2Level(double co2Level) { this.co2Level = co2Level; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private Long id;
        private String sensorId;
        private double temperature;
        private double humidity;
        private double co2Level;
        private Instant timestamp;

        private Builder() {
        }

        public Builder id(Long id) { this.id = id; return this; }
        public Builder sensorId(String sensorId) { this.sensorId = sensorId; return this; }
        public Builder temperature(double temperature) { this.temperature = temperature; return this; }
        public Builder humidity(double humidity) { this.humidity = humidity; return this; }
        public Builder co2Level(double co2Level) { this.co2Level = co2Level; return this; }
        public Builder timestamp(Instant timestamp) { this.timestamp = timestamp; return this; }

        public SensorReading build() {
            return new SensorReading(id, sensorId, temperature, humidity, co2Level, timestamp);
        }
    }
}
