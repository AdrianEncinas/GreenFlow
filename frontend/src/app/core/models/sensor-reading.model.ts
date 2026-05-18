export interface SensorReading {
  id: number;
  sensorId: string;
  temperature: number;
  humidity: number;
  co2Level: number;
  timestamp: string;
}

export interface LatestReading {
  temperature: number;
  humidity: number;
  co2Level: number;
  timestamp: string;
}

export interface SensorReadingsSummary {
  totalReadings: number;
  totalSensors: number;
  averageTemperature: number;
  averageHumidity: number;
  averageCo2Level: number;
  criticalAlerts: number;
  latestTimestamp: string;
  latestPerSensor: Record<string, LatestReading>;
}

export interface SensorAlert {
  sensorId: string;
  type: 'temperature' | 'humidity' | 'co2';
  message: string;
  value: number;
  timestamp: string;
}
