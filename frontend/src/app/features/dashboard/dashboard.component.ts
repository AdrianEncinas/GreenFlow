import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { DatePipe, DecimalPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BaseChartDirective } from 'ng2-charts';
import { ChartConfiguration } from 'chart.js';
import { forkJoin } from 'rxjs';
import { AuthService } from '../../core/services/auth.service';
import { SensorReadingsService } from '../../core/services/sensor-readings.service';
import {
  SensorAlert,
  SensorReading,
  SensorReadingsSummary,
} from '../../core/models/sensor-reading.model';

@Component({
  selector: 'app-dashboard',
  imports: [FormsModule, DecimalPipe, DatePipe, BaseChartDirective],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DashboardComponent implements OnInit {
  readings: SensorReading[] = [];
  displayedReadings: SensorReading[] = [];
  summary: SensorReadingsSummary | null = null;
  alerts: SensorAlert[] = [];

  selectedSensor = 'all';
  lastRecords = 24;
  loading = true;
  error = '';

  temperatureChartData: ChartConfiguration<'line'>['data'] = {
    labels: [],
    datasets: [],
  };

  humidityChartData: ChartConfiguration<'line'>['data'] = {
    labels: [],
    datasets: [],
  };

  co2ChartData: ChartConfiguration<'line'>['data'] = {
    labels: [],
    datasets: [],
  };

  temperatureChartOptions = this.createMetricChartOptions(0, 10);
  humidityChartOptions = this.createMetricChartOptions(0, 10);
  co2ChartOptions = this.createMetricChartOptions(0, 10);

  constructor(
    private readonly sensorReadingsService: SensorReadingsService,
    private readonly authService: AuthService,
  ) {}

  ngOnInit(): void {
    this.refresh();
  }

  get sensors(): string[] {
    return Array.from(new Set(this.readings.map((item) => item.sensorId))).sort((a, b) =>
      a.localeCompare(b),
    );
  }

  logout(): void {
    this.authService.logout();
  }

  refresh(): void {
    this.loading = true;
    this.error = '';

    forkJoin({
      readings: this.sensorReadingsService.getAllReadings(),
      summary: this.sensorReadingsService.getSummary(),
    }).subscribe({
      next: ({ readings, summary }) => {
        this.readings = readings;
        this.summary = summary;
        this.applyFilters();
        this.loading = false;
      },
      error: () => {
        this.loading = false;
        this.error = 'No fue posible cargar las lecturas. Verifica token, CORS y servicios activos.';
      },
    });
  }

  applyFilters(): void {
    const source =
      this.selectedSensor === 'all'
        ? this.readings
        : this.readings.filter((item) => item.sensorId === this.selectedSensor);

    this.displayedReadings = source.slice(0, this.lastRecords);
    this.alerts = this.buildAlerts(this.displayedReadings);
    this.buildCharts(this.displayedReadings);
  }

  private buildCharts(source: SensorReading[]): void {
    const ordered = [...source].sort(
      (a, b) => new Date(a.timestamp).getTime() - new Date(b.timestamp).getTime(),
    );

    const labels = ordered.map((item) =>
      new Date(item.timestamp).toLocaleTimeString('es-ES', { hour: '2-digit', minute: '2-digit' }),
    );

    const temperatures = ordered.map((item) => item.temperature);
    const humidities = ordered.map((item) => item.humidity);
    const co2Levels = ordered.map((item) => item.co2Level);

    this.temperatureChartData = this.createMetricChartData(
      labels,
      temperatures,
      'Temperatura (C)',
      '#df7e39',
      'rgba(223, 126, 57, 0.18)',
    );
    this.humidityChartData = this.createMetricChartData(
      labels,
      humidities,
      'Humedad (%)',
      '#2a8d7b',
      'rgba(42, 141, 123, 0.18)',
    );
    this.co2ChartData = this.createMetricChartData(
      labels,
      co2Levels,
      'CO2 (ppm)',
      '#4a69c2',
      'rgba(74, 105, 194, 0.18)',
    );

    const temperatureRange = this.calculateYAxisRange(temperatures);
    const humidityRange = this.calculateYAxisRange(humidities);
    const co2Range = this.calculateYAxisRange(co2Levels);

    this.temperatureChartOptions = this.createMetricChartOptions(
      temperatureRange.min,
      temperatureRange.max,
    );
    this.humidityChartOptions = this.createMetricChartOptions(
      humidityRange.min,
      humidityRange.max,
    );
    this.co2ChartOptions = this.createMetricChartOptions(co2Range.min, co2Range.max);
  }

  private createMetricChartData(
    labels: string[],
    values: number[],
    label: string,
    borderColor: string,
    backgroundColor: string,
  ): ChartConfiguration<'line'>['data'] {
    return {
      labels,
      datasets: [
        {
          label,
          data: values,
          borderColor,
          backgroundColor,
          tension: 0.25,
          borderWidth: 2,
          fill: true,
          pointRadius: 2,
          pointHoverRadius: 4,
        },
      ],
    };
  }

  private createMetricChartOptions(
    min: number,
    max: number,
  ): ChartConfiguration<'line'>['options'] {
    return {
      responsive: true,
      maintainAspectRatio: false,
      animation: false,
      plugins: {
        legend: {
          labels: {
            color: '#29503d',
          },
        },
      },
      scales: {
        x: {
          ticks: { color: '#315644', maxRotation: 0, autoSkip: true, maxTicksLimit: 8 },
          grid: { color: 'rgba(36, 79, 57, 0.09)' },
        },
        y: {
          min,
          max,
          ticks: { color: '#315644' },
          grid: { color: 'rgba(36, 79, 57, 0.14)' },
        },
      },
    };
  }

  private calculateYAxisRange(values: number[]): { min: number; max: number } {
    if (values.length === 0) {
      return { min: 0, max: 10 };
    }

    const minValue = Math.min(...values);
    const maxValue = Math.max(...values);
    const spread = maxValue - minValue;

    if (spread === 0) {
      const padding = Math.max(Math.abs(minValue) * 0.05, 1);
      return {
        min: minValue - padding,
        max: maxValue + padding,
      };
    }

    const padding = Math.max(spread * 0.18, spread < 5 ? 0.4 : 0);
    return {
      min: minValue - padding,
      max: maxValue + padding,
    };
  }

  private buildAlerts(source: SensorReading[]): SensorAlert[] {
    const alerts: SensorAlert[] = [];

    for (const reading of source) {
      if (reading.temperature > 35) {
        alerts.push({
          sensorId: reading.sensorId,
          type: 'temperature',
          message: 'Temperatura alta',
          value: reading.temperature,
          timestamp: reading.timestamp,
        });
      }

      if (reading.humidity < 35) {
        alerts.push({
          sensorId: reading.sensorId,
          type: 'humidity',
          message: 'Humedad baja',
          value: reading.humidity,
          timestamp: reading.timestamp,
        });
      }

      if (reading.co2Level > 1200) {
        alerts.push({
          sensorId: reading.sensorId,
          type: 'co2',
          message: 'CO2 fuera de rango',
          value: reading.co2Level,
          timestamp: reading.timestamp,
        });
      }
    }

    return alerts.slice(0, 12);
  }
}
