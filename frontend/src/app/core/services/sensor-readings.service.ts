import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { environment } from '../../../environments/environment';
import { SensorReading, SensorReadingsSummary } from '../models/sensor-reading.model';

@Injectable({ providedIn: 'root' })
export class SensorReadingsService {
  constructor(private readonly http: HttpClient) {}

  getAllReadings(): Observable<SensorReading[]> {
    return this.http
      .get<SensorReading[]>(`${environment.greenhouseApiUrl}/api/v1/sensor-readings/list`)
      .pipe(
        map((readings) =>
          [...readings].sort(
            (a, b) => new Date(b.timestamp).getTime() - new Date(a.timestamp).getTime(),
          ),
        ),
      );
  }

  getReadingsBySensor(sensorId: string): Observable<SensorReading[]> {
    return this.http
      .get<SensorReading[]>(`${environment.greenhouseApiUrl}/api/v1/sensor-readings/sensor/${encodeURIComponent(sensorId)}`)
      .pipe(
        map((readings) =>
          [...readings].sort(
            (a, b) => new Date(b.timestamp).getTime() - new Date(a.timestamp).getTime(),
          ),
        ),
      );
  }

  getSummary(): Observable<SensorReadingsSummary> {
    return this.http.get<SensorReadingsSummary>(
      `${environment.greenhouseApiUrl}/api/v1/sensor-readings/summary`,
    );
  }
}
