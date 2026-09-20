import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PlanetPositionResponse } from '../models/planet-position.model';

@Injectable({ providedIn: 'root' })
export class AstrologService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/astrolog/planets';

  public getPlanets(
    day: number,
    month: number,
    year: number,
    hour: number,
    minute: number,
  ): Observable<PlanetPositionResponse[]> {
    return this.http.get<PlanetPositionResponse[]>(this.apiUrl, {
      params: { day, month, year, hour, minute },
    });
  }
}
